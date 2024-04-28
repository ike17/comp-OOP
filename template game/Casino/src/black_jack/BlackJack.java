package black_jack;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.concurrent.TimeUnit;

import handlers.MouseHandler;
import main.Chip;
import main.Controller;
import main.Frame;
import main.GameBoard;
import main.ImageLoader;
import universal.CardImage;
import universal.Deck;

public class BlackJack {
// https://www.hollywoodpnrc.com/-/media/png/east/hollywood-pnrc/pdfs/table-games-tutorials/tutorial-blackjack.pdf
	private Image board, closeGame;
	private Image clearbetButton, dealButton, splitButton, surrenderButton, hitButton, standButton, repeatbetButton,
			insuranceButton, doubledownButton, simulationOnButton, simulationOffButton;
	public Rectangle[] buttons = { new Rectangle(1250, 5, 25, 25), new Rectangle(575, 500, 128, 64), // close game, deal
			new Rectangle(150, 525, 128, 64), new Rectangle(400, 630, 128, 64), // clear, hit,
			new Rectangle(550, 630, 128, 64), new Rectangle(700, 630, 128, 64), // stand, double down
			new Rectangle(850, 630, 128, 64), new Rectangle(1000, 630, 128, 64), // surrender, split
			new Rectangle(1150, 630, 128, 64), // insurance
			new Rectangle(15, 475, 128, 64)}; // Simulation Buttons
	public GameBoard[] hitbox = new GameBoard[15];// 26
	private Chip[] chips;
	private double money = 1000;
	private double lastMoneyEarned = 0;
	boolean inGame = false;
	public Hand[] playerHand = { null, null, null }; // 3 hands
	public Deck deck;
	public Hand dealerHand;

	int firstBet = 0;
	int totalOutcome = 0;
	int currentDeckIndex = 0;
	boolean checkedWin = true;
	boolean playersTurn = true;
	boolean dealersTurn = false;
	
    boolean hasLooped = false;
    int automationIteration = 0;
    int automationTime = 200;
    
	public BlackJack() {
		board = new ImageLoader(ImageLoader.blackjackTable).getImage();
		hitButton = new ImageLoader(ImageLoader.hitButton).getImage();
		closeGame = new ImageLoader(ImageLoader.closeGame).getImage();
		dealButton = new ImageLoader(ImageLoader.dealButton).getImage();
		clearbetButton = new ImageLoader(ImageLoader.clearbetButton).getImage();
		standButton = new ImageLoader(ImageLoader.standButton).getImage();
		splitButton = new ImageLoader(ImageLoader.splitButton).getImage();
		surrenderButton = new ImageLoader(ImageLoader.surrenderButton).getImage();
		doubledownButton = new ImageLoader(ImageLoader.doubledownButton).getImage();
		standButton = new ImageLoader(ImageLoader.standButton).getImage();
		repeatbetButton = new ImageLoader(ImageLoader.repeatbetButton).getImage();
		insuranceButton = new ImageLoader(ImageLoader.insuranceButton).getImage();
		simulationOffButton = new ImageLoader(ImageLoader.simulateOffButton).getImage();
		simulationOnButton = new ImageLoader(ImageLoader.simulateOnButton).getImage();
		init();
	}

	public void init() {
//		addHitboxes();
		playerHand[0] = new Hand(false, 10);
		dealerHand = new Hand(false, 10);
		deck = new Deck(5);
		addChips();
	}

	public void tick() {
		if(!Controller.AUTOMATION) {
			for (int i = 0; i < buttons.length; i++) {
				if (buttons[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
					if (i == 0) {
						// Close Game
						Controller.switchStates(Controller.STATE.MENU);
					}
					if (i == 1 && !inGame && playerHand[currentDeckIndex] != null && playerHand[currentDeckIndex].moneyBet > 0) {
						// DEAL Bet
						inGame = true;
						dealHand();
					}
					if (!inGame && i == 2) {
						// Clear Bet
						clearBet();
					}
					if (inGame) {
						if (i == 3 && !playersTurn && inGame) {
							// Repeat Bet
	//						money -= handBet[0];
							resetGame();
							break;
						}
						if(playersTurn) {
							if (i == 3) {
								// HIT Bet
								hitBet();
							}
							if (i == 4 && playerHand[currentDeckIndex].handTotal <= 21) {
								//stand
								nextDeck();
							}
							if (i == 5) {
								// double down
								doubleDown();
							}
							if (i == 6 && playerHand[currentDeckIndex].hand[2] == null) {
								// surrender
								surrenderDeck();
							}
							if (i == 7) {
								//split deck
								checkSplit();
							}
							if (i == 8) {
								// insurance
								addInsurance();
							}
						}
					}
					MouseHandler.MOUSEDOWN = false;
				}
			}
			for (int i = 0; i < chips.length; i++) {
				if (!inGame && chips[i].bounds.contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
					if (money >= chips[i].value + firstBet) {
						firstBet += chips[i].value;
						playerHand[currentDeckIndex].moneyBet += chips[i].value;
					}
					MouseHandler.MOUSEDOWN = false;
				}
			}
		}else {
			if(!inGame) {
					if(hasLooped) {
					firstBet += chips[3].value;
					playerHand[currentDeckIndex].moneyBet += chips[3].value;
					inGame = true;
					dealHand();
				}else {
					hasLooped = true;
				}
			}else {
				if(playersTurn) {
					try {
						Thread.sleep(automationTime);
						if(playerHand[currentDeckIndex].hand != null && dealerHand.hand[0] != null) {
							System.out.println("Best Move: " + Automation.findMove(playerHand[currentDeckIndex].hand, dealerHand.hand[0].number));
							int bestMove = -1;
							bestMove = Automation.findMove(playerHand[currentDeckIndex].hand, dealerHand.hand[0].number);
							if(bestMove == 0) {
								//Hit
								hitBet();
							}
							if(bestMove == 1) {
								nextDeck();
							}
							if(bestMove == 2) {
								checkSplit();
							}
							if(bestMove == 3) {
								boolean doubleDown = doubleDown();
								if(!doubleDown) {
									hitBet();
								}
							}
							if(bestMove == 4) {
								boolean doubleDown = doubleDown();
								if(!doubleDown) {
									nextDeck();
								}
							}
							if(bestMove == 5) {
								surrenderDeck();
							}
							if(bestMove == 6) {
								boolean couldSplit = checkSplit();
								if(!couldSplit) {
									boolean doubleDown = doubleDown();
									if(!doubleDown) {
										hitBet();
									}
								}
							}
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}else {
					//Game Over
					if(!dealersTurn) {
						try {
							Thread.sleep(automationTime*2);
							resetGame();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}
				}
			}
		}
		if(dealersTurn) {
			if(dealerHand != null) {
				if(dealerHand.handTotal < 17) {
					runDealerHand();
				}else {
					dealersTurn = false;
					endRound();
				}
			}else {
				dealerHand = new Hand(deck, true);
			}
		}
	} 
	
	private void endRound() {
		System.out.println("Total Outcome: " + totalOutcome);
		for(int i = 0; i < playerHand.length; i++) {
			if(playerHand[i] != null) {
				totalOutcome += playerHand[i].returnMoney(dealerHand.handTotal, dealerHand.hand);
				System.out.println("Adding " + playerHand[i].returnMoney(dealerHand.handTotal, dealerHand.hand) + " Outcome: " + totalOutcome);
			}
		}
		money += totalOutcome;
	}

	public void hitBet() {
		if(playerHand[currentDeckIndex].setCardTotal() < 21){
			playerHand[currentDeckIndex].addCard(deck);
			if (playerHand[currentDeckIndex].setCardTotal() == 21) {
				nextDeck();
			}
			if (playerHand[currentDeckIndex].busted) {
				nextDeck();
			}
		}
	}
	public boolean checkSplit() {
		boolean hasSplit = false;
		if(playerHand[currentDeckIndex].hand[0].number == playerHand[currentDeckIndex].hand[1].number) {
			if (playerHand[currentDeckIndex].hand[2] == null) {
				if(money-getCurrentBet() >= playerHand[currentDeckIndex].moneyBet) {
					if(currentDeckIndex == 0 && (playerHand[currentDeckIndex+1] == null || playerHand[currentDeckIndex+2] == null)) {
						splitDeck();
						hasSplit = true;
					}
					if(currentDeckIndex == 1 && playerHand[currentDeckIndex+1] == null) {
						splitDeck();
						hasSplit = true;
					}
				}
			}
		}
		
		return hasSplit;
	}
	public boolean doubleDown() {
		boolean hasDD = false;
		if(money - playerHand[currentDeckIndex].handTotal >= 0) {
			playerHand[currentDeckIndex].moneyBet *= 2;
			playerHand[currentDeckIndex].addCard(deck);
			nextDeck();
			hasDD = true;
		}
		return hasDD;
	}
	public void surrenderDeck() {
		playerHand[currentDeckIndex].moneyBet /= 2;
		playerHand[currentDeckIndex].surrendered = true;
		nextDeck();
	}
	public void addInsurance() {
		if(dealerHand.hand[0].number == 1) {
			if (dealerHand != null && 
				!playerHand[currentDeckIndex].insurance) {
				if(money-getCurrentBet() >= playerHand[currentDeckIndex].moneyBet / 2) {
					playerHand[currentDeckIndex].insuranceBet += playerHand[currentDeckIndex].moneyBet/2;
					playerHand[currentDeckIndex].insurance = true;
				}
			}
		}
	}
	
	private void splitDeck() {
		if(playerHand[currentDeckIndex + 1] == null) {
			playerHand[currentDeckIndex + 1] = new Hand(deck, false);
			playerHand[currentDeckIndex + 1].hand[0] = playerHand[currentDeckIndex].hand[1];
			playerHand[currentDeckIndex].hand[1] = null;
			playerHand[currentDeckIndex].setCardTotal();
			playerHand[currentDeckIndex].addCard(deck);
			playerHand[currentDeckIndex+1].moneyBet = playerHand[currentDeckIndex].moneyBet;
		}else {
			playerHand[currentDeckIndex + 2] = new Hand(deck, false);
			playerHand[currentDeckIndex + 2].hand[0] = playerHand[currentDeckIndex].hand[1];
			playerHand[currentDeckIndex].hand[1] = null;
			playerHand[currentDeckIndex].setCardTotal();
			playerHand[currentDeckIndex].addCard(deck);
			playerHand[currentDeckIndex+2].moneyBet = playerHand[currentDeckIndex].moneyBet;
		}
	}
	public void clearBet() {
		firstBet = 0;
		for(int j = 0; j < playerHand.length; j++) {
			if(playerHand[j] != null) {
				playerHand[j].moneyBet = 0;
			}
		}
	}
	private void nextDeck() {
		if (currentDeckIndex >= 2 || playerHand[currentDeckIndex + 1] == null) {
			playersTurn = false;
			dealersTurn = true;
		} else {
			currentDeckIndex += 1;
			playerHand[currentDeckIndex].addCard(deck);
		}
	}
	private void runDealerHand() {
		dealerHand.runDealerHand(deck);
	}

	private void resetGame() {
		playerHand[0] = new Hand(deck, false);
		currentDeckIndex = 0;
		playerHand[0].moneyBet = 0;
		playerHand[1] = null;
		playerHand[2] = null;
		dealerHand = null;
		playersTurn = true;
		inGame = false;
		firstBet = 0;
		totalOutcome = 0;
		hasLooped = false;
	}

	public void render(Graphics g) {
		g.drawImage(board, 0, 0, Frame.WIDTH, Frame.HEIGHT, null);
		g.drawImage(closeGame, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
		g.setColor(Color.WHITE);
		g.setFont(Controller.SMALL);
		g.drawString("Money: $" + ((double) Math.round(money * 100) / 100), 10, 560);
		g.drawString("Current Bet: $" + getCurrentBet(), 10, 580);
		if(Controller.AUTOMATION) {
			g.drawString("Current Iteration: " + automationIteration, 10, 600);
		}
		if (lastMoneyEarned > 0) {
			g.setColor(Color.GREEN);
		}
		if (lastMoneyEarned < 0) {
			g.setColor(Color.RED);
		}
		if(Controller.AUTOMATION) {
			g.drawImage(simulationOffButton, buttons[9].x, buttons[9].y, buttons[9].width, buttons[9].height, null);
		}else {
			g.drawImage(simulationOnButton, buttons[9].x, buttons[9].y, buttons[9].width, buttons[9].height, null);
		}
		for (int i = 0; i < chips.length; i++) {
			g.drawImage(chips[i].getImage(), chips[i].bounds.x, chips[i].bounds.y, chips[i].bounds.width,
					chips[i].bounds.height, null);
		}
		if (!inGame) {
			if(playerHand != null && playerHand[currentDeckIndex] != null && playerHand[currentDeckIndex].moneyBet > 0) {
				g.drawImage(dealButton, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
				g.drawImage(clearbetButton, buttons[2].x, buttons[2].y, buttons[2].width, buttons[2].height, null);
			}else {
				g.setFont(Controller.NORMAL);
				g.setColor(Color.YELLOW);
				g.drawString("Please place a bet to start!", 50, 620);
			}
		} else {
			if (playersTurn) {
				if (playerHand[currentDeckIndex].handTotal <= 21) {
					if(!Controller.AUTOMATION) {
						g.drawImage(hitButton, buttons[3].x, buttons[3].y, buttons[3].width, buttons[3].height, null);
						g.drawImage(standButton, buttons[4].x, buttons[4].y, buttons[4].width, buttons[4].height, null);
						if (money - playerHand[currentDeckIndex].handTotal >= 0) {
							g.drawImage(doubledownButton, buttons[5].x, buttons[5].y, buttons[5].width, buttons[5].height,
									null);
						}
						g.drawImage(surrenderButton, buttons[6].x, buttons[6].y, buttons[6].width, buttons[6].height, null);
						if(playerHand[currentDeckIndex].hand[0].number == playerHand[currentDeckIndex].hand[1].number) {
							if (playerHand[currentDeckIndex].hand[2] == null) {
								if(money-getCurrentBet() >= playerHand[currentDeckIndex].moneyBet) {
									if(currentDeckIndex == 0 && (playerHand[currentDeckIndex+1] == null || playerHand[currentDeckIndex+2] == null)) {
										g.drawImage(splitButton, buttons[7].x, buttons[7].y, buttons[7].width, buttons[7].height, null);
									}
									if(currentDeckIndex == 1 && playerHand[currentDeckIndex+1] == null) {
										g.drawImage(splitButton, buttons[7].x, buttons[7].y, buttons[7].width, buttons[7].height, null);
									}
								}
							}
						}
						if (dealerHand != null && dealerHand.hand[0].number == 1 && 
								!playerHand[currentDeckIndex].insurance) {
							if(money-getCurrentBet() >= playerHand[currentDeckIndex].moneyBet / 2) {
								g.drawImage(insuranceButton, buttons[8].x, buttons[8].y, buttons[8].width, buttons[8].height,
									null);
							}
						}
					}
				}
			}
			for (int i = 0; i < playerHand.length; i++) {
				if (playerHand[i] != null) {
					if(i == currentDeckIndex) {
						playerHand[i].render(g, true, i);
					}else {
						playerHand[i].render(g, false, i);
					}
				}
			}
			dealerHand.render(g, false, 0);
			if (!playersTurn && !dealersTurn) {
				g.drawImage(repeatbetButton, buttons[3].x, buttons[3].y, buttons[3].width, buttons[3].height, null);
				g.setColor(new Color(0, 0, 0, 200));
				g.fillRoundRect(Frame.WIDTH / 2 - 150, Frame.HEIGHT / 2 - 150, 300, 150, 50, 50);
				g.setColor(Color.WHITE);
				g.setFont(Controller.bigFont);
				if (totalOutcome > 0) {
					g.drawString("You Win!", Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("You Win!") / 2,
							Frame.HEIGHT / 2 - 100);
					g.drawString("Repeat Bet?", Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Repeat Bet?") / 2,
							Frame.HEIGHT / 2 - 55);
					g.setColor(Color.GREEN);
					g.drawString("Money Earned: $" + totalOutcome,
							Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Money Earned: $" + totalOutcome) / 2,
							Frame.HEIGHT / 2 - 25);
				}
				if (totalOutcome < 0) {
					g.drawString("You Lose...", Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("You Lose...") / 2,
							Frame.HEIGHT / 2 - 100);
					g.drawString("Repeat Bet?", Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Repeat Bet?") / 2,
							Frame.HEIGHT / 2 - 55);
					g.setColor(Color.RED);
					g.drawString("Money Lost: $" + totalOutcome,
							Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Money Lost: $" + totalOutcome) / 2,
							Frame.HEIGHT / 2 - 25);
				}
				if (totalOutcome == 0) {
					g.drawString("Push! No Money Lost.",
							Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Push! No Money Lost.") / 2,
							Frame.HEIGHT / 2 - 100);
					g.drawString("Repeat Bet?", Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Repeat Bet?") / 2,
							Frame.HEIGHT / 2 - 55);
					g.drawString("Money Earned: $0",
							Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Money Earned: $0") / 2,
							Frame.HEIGHT / 2 - 25);
				}
				for (int i = 0; i < playerHand.length; i++) {
					if (playerHand[i] != null) {
						g.setColor(new Color(0, 0, 0, 160));
						g.fillRoundRect(850, 410 - (i * 135) + 85, 250, 75, 20, 20);
							if(playerHand[i].playerWins) {
								g.setColor(Color.green);
								g.drawString("Won +$" + playerHand[i].moneyBet, 900, 460 - (i * 135) + 85);
							}else if(!playerHand[i].playerWins && !playerHand[i].push && !playerHand[i].surrendered) {
								g.setColor(Color.RED);
								g.drawString("Lost -$" + playerHand[i].moneyBet, 900, 460 - (i * 135) + 85);
							}else if (playerHand[i].push){
								g.setColor(Color.GRAY);
								g.drawString("Push $0", 900, 460 - (i * 135) + 85);
							}else if(playerHand[i].surrendered) {
								g.setColor(Color.RED);
								g.drawString("Surrendered -$" + playerHand[i].moneyBet, 875, 460 - (i * 135) + 85);
							}
					}
				}
			}
			g.setFont(Controller.NORMAL);
			g.setColor(Color.WHITE);
		}
		if(Controller.AUTOMATION && !inGame) {
				try {
					Thread.sleep(automationTime*5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}

	private int getCurrentBet() {
		int total = 0;
		for(int i = 0; i < playerHand.length; i++) {
			if(playerHand[i] != null) {
				total += playerHand[i].moneyBet + playerHand[i].insuranceBet;
			}
		}
		return total;
	}
	private void dealHand() {
//		deck.refreshDeck();
		currentDeckIndex = 0;
		playerHand[0] = new Hand(deck, false);
		playerHand[0].moneyBet = firstBet;
		playerHand[0].addCard(deck);
		dealerHand = new Hand(deck, true);
		if(Controller.AUTOMATION) {
			automationIteration += 1;
		}else {
			automationIteration = 0;
		}
		if(playerHand[0].setCardTotal() == 21) {
			nextDeck();
		}
	}

	public void addChips() {
		chips = new Chip[5];
		int chipy = Frame.HEIGHT - 90;
		int chipx = 50;
		chips[0] = new Chip(chipx * 1 - 25, chipy, 1, false);
		chips[1] = new Chip(chipx * 2 - 25, chipy, 5, false);
		chips[2] = new Chip(chipx * 3 - 25, chipy, 10, false);
		chips[3] = new Chip(chipx * 4 - 25, chipy, 25, false);
		chips[4] = new Chip(chipx * 5 - 25, chipy, 100, false);
	}
}
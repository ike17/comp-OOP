package baccarat;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.dnd.MouseDragGestureRecognizer;

import black_jack.Hand;
import handlers.MouseHandler;
import main.Chip;
import main.Controller;
import main.Frame;
import main.GameBoard;
import main.ImageLoader;
import main.Marker;
import universal.Deck;

public class Baccarat {

	public Image bg, closeGame, clearBoard, deal, help, repeatbetButton;
	public Rectangle[] buttons = {
			new Rectangle(1250, 5, 25, 25), new Rectangle(550, 325, 128, 64), //close game, clear board
			new Rectangle(700, 325, 128, 64), new Rectangle(1215, 50, 48, 48),  //deal, help
			new Rectangle(585, 380, 128, 64)									//Repeat Bet
			};
	public GameBoard[] hitbox = new GameBoard[3];//26
	
	private Chip[] chips;
	private Chip[] gameChips;
	private Chip mouseChip;
	private double money = 100;
	private int tableMoney = 0;
	private double bonusWinnings = 0;
	private double totalWinnings = 0;
	
	private int gameSpeed=1000;
	
	boolean inChipBounds = false;
	boolean inGameBounds = false;
	
	public boolean inHelp = false;
	public boolean inGame = false;
	public boolean gameDone = false;
	public String helpText = "";
	public int results = 0;
	public String whoWon = "";
	
	public Deck deck;
	public Hand playerHand;
	public Hand dealerHand;
	
	public Baccarat() {
		init();
	}
	public void init() {
		bg = new ImageLoader(ImageLoader.baccaratbg).getImage();
		closeGame = new ImageLoader(ImageLoader.closeGame).getImage();
		deal = new ImageLoader(ImageLoader.dealButton).getImage();
		clearBoard = new ImageLoader(ImageLoader.clearBoard).getImage();
		help = new ImageLoader(ImageLoader.help).getImage();
		repeatbetButton = new ImageLoader(ImageLoader.repeatbetButton).getImage();
		addChips();
		addHitboxes();
		gameChips = new Chip[99];
		deck = new Deck(5);
		playerHand = new Hand(false, 3);
		dealerHand = new Hand(true, 3);
		
	}
	public void tick() {
		if(MouseHandler.MOUSEDOWN) {
			inChipBounds = false;
			inGameBounds = false;
			inHelp = false;
			helpText = "";
		}
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				if (i == 0) {
					// Close Game
					Controller.switchStates(Controller.STATE.MENU);
				}
				if(i == 1 && !inGame) {
					checkRemoveChip(true);
				}
				if(i == 2 && tableMoney > 0 && !inGame) {
					//startGame
					playerHand = new Hand(false, 3);
					dealerHand = new Hand(true, 3);
					inGame = true;
					gameDone = false;
				}
				if (i == 3) {
					// HELP
					inHelp = true;
				}
				if(i == 4 && inGame && gameDone) {
					//Repeat Bet
					inGame = false;
					gameDone = false;
					results = 0;
					if(bonusWinnings >= 0) {
						money += totalWinnings;
					}
					whoWon = "";
					totalWinnings = 0;
					bonusWinnings = 0;
					tableMoney = 0;
					gameChips = new Chip[99];
					playerHand = new Hand(false, 3);
					dealerHand = new Hand(true, 3);
				}
			}
		}
		for(int i = 0; i < chips.length; i++) {
			//Select a chip
			if(chips[i].bounds.contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				if(money >= chips[i].value) {
					mouseChip = chips[i];
					inChipBounds = true;
					MouseHandler.MOUSEDOWN = false;
				}
			}
		}
		boolean notFound = true;
		if(MouseHandler.MOUSEDOWN) {
			for(int i = 0; i < hitbox.length; i++) {
				if(hitbox[i] != null) {
				//Placing Chip
					if(hitbox[i].bounds.contains(Controller.mousePoint) 
							 && !MouseHandler.RIGHTCLICKED) {
						if(mouseChip != null && money >= mouseChip.value) {
							placeChip(((int) mouseChip.value), hitbox[i], 
									Controller.mousePoint.x-(mouseChip.bounds.width/2), 
									Controller.mousePoint.y-(mouseChip.bounds.height/2));
						}
						notFound = false;
						MouseHandler.MOUSEDOWN = false;
						inGameBounds = true;
						break;
					}
				}
			}
			if(notFound) {
				mouseChip = null;
			}
		}
		if(!inGame && !gameDone) {
			checkRemoveChip(false);
		}
		if(inHelp) {
			boolean noDescr = false;
			for(int i = 0; i < hitbox.length; i++) {
				if(hitbox[i] != null && hitbox[i].bounds.contains(Controller.mousePoint)) {					
					helpText = hitbox[i].descr;
					noDescr = true;
				}
			}
			if(!noDescr) {
				helpText = "";
			}
		}
		if(inGame && !gameDone) {
			runGame();
		}
		if(MouseHandler.MOUSEDOWN) {
		System.out.println(Controller.mousePoint.x + ", " + Controller.mousePoint.y);
		}
	}
	public void render(Graphics g) {
		g.drawImage(bg, 0, 0, Frame.WIDTH, Frame.HEIGHT+100, null);
		g.drawImage(closeGame, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
		g.setColor(Color.WHITE);
		g.setFont(Controller.SMALL);
		g.drawString("Money: $" + ((double) Math.round(money * 100) / 100), 10, 15);
		g.drawString("Money on Table: $" + ((int)tableMoney), 10, 35);
		for(int i = 0; i < gameChips.length; i++) {
			if(gameChips[i] != null) {
				g.drawImage(gameChips[i].getImage(), gameChips[i].bounds.x, gameChips[i].bounds.y, gameChips[i].bounds.width, gameChips[i].bounds.height, null);
				g.setColor(new Color(0, 0, 0, 100));
				if(!gameChips[i].canCancel) {
					g.fillRect(gameChips[i].bounds.x-19, gameChips[i].bounds.y + gameChips[i].bounds.height, 90, 20);
					g.setColor(Color.RED);
					g.setFont(Controller.SMALL);
					g.drawString("Can't Cancel", gameChips[i].bounds.x-17, gameChips[i].bounds.y + gameChips[i].bounds.height+15);
					g.setColor(Color.BLACK);
				}
			}
		}
		if(!inGame) {
			g.setFont(Controller.bigFont);
			g.drawString("Chips: ", 100, Frame.HEIGHT-90);
			for(int i = 0; i < chips.length; i++) {
				g.drawImage(chips[i].getImage(), chips[i].bounds.x, chips[i].bounds.y, chips[i].bounds.width, chips[i].bounds.height, null);
			}
			g.setColor(Color.red);
			for(int i = 0; i < hitbox.length; i++) {
				g.drawPolygon(hitbox[i].bounds);
			}
			if(mouseChip != null) {
				g.drawImage(mouseChip.getImage(), Controller.mousePoint.x - (mouseChip.bounds.width/2), 
						Controller.mousePoint.y-(mouseChip.bounds.height/2), mouseChip.bounds.width, mouseChip.bounds.height, null);
			}
			g.drawImage(clearBoard, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
			if(tableMoney > 0) {
				g.drawImage(deal, buttons[2].x, buttons[2].y, buttons[2].width, buttons[2].height, null);
			}
		}else {
			//In Game
			for(int i = 0; i < 3; i++) {
				if(playerHand.hand[i] != null) {
					g.drawImage(playerHand.hand[i].cardImage, 350 + (i*50), 100, null);
				}
				if(dealerHand.hand[i] != null) {
					g.drawImage(dealerHand.hand[i].cardImage, 750 + (i*50), 100, null);
				}
			}
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRect(365, 320, 175, 50);
			g.fillRect(745, 320, 175, 50);
			g.setColor(Color.WHITE);
			g.drawString("Player Total: " + playerHand.handTotal % 10, 400, 345);
			g.drawString("Dealer Total: " + dealerHand.handTotal % 10, 775, 345);
			if(gameDone) {
				g.setColor(new Color(0, 0, 0, 150));
				g.fillRoundRect(Frame.WIDTH/2-150, Frame.HEIGHT/2-100, 300, 200, 7, 7);
				g.setColor(Color.WHITE);
				g.setFont(Controller.bigBold);
				g.drawString("Results: " + whoWon, Frame.WIDTH/2-
						g.getFontMetrics().stringWidth("Results: " + whoWon)/2, Frame.HEIGHT/2-75);
				g.drawString("Winnings: " + bonusWinnings, Frame.WIDTH/2-
						g.getFontMetrics().stringWidth("Winnings: " + bonusWinnings)/2, Frame.HEIGHT/2-50);
				g.drawImage(repeatbetButton, buttons[4].x, buttons[4].y, buttons[4].width, buttons[4].height, null);
			}
		}
		if(inHelp) {
			g.drawImage(help, Controller.mousePoint.x-16, Controller.mousePoint.y-16, 32, 32, null);
			if(!helpText.isEmpty()) {
				g.setFont(Controller.SMALL);
				g.setColor(new Color(0, 0, 0, 150));
				int y = 0;
				int height = 0;
				int width = 0;
			    for (String line : helpText.split("\n")) {
			    	if(width < g.getFontMetrics().stringWidth(line)) {
			    		width = g.getFontMetrics().stringWidth(line);
			    	}
			    	height += g.getFontMetrics().getHeight();
			    }
				if(Controller.mousePoint.y > 450) {
					y = Controller.mousePoint.y-height;
				}else {
					y = Controller.mousePoint.y;
				}
				int x = Controller.mousePoint.x - (width/2);
				if(Controller.mousePoint.x < 200) {
					x = Controller.mousePoint.x - (width/2)+150;
				}
				g.fillRect(x, y, width+15, height+15);
				g.setColor(Color.YELLOW);
				int lineHeight = g.getFontMetrics().getHeight();
				int stringY = y;
			    for (String line : helpText.split("\n")) {
			    	height += g.getFontMetrics().getHeight();
			        g.drawString(line, x+5, stringY += lineHeight);
			    }
			}
		}else {
			g.drawImage(help, buttons[3].x, buttons[3].y, buttons[3].width, buttons[3].height, null);
		}
	}
	public void runGame() {
		boolean addedCard = false;
		if(playerHand.hand[1] == null && playerHand.hand[2] == null) {
			playerHand.addCard(deck);
			System.out.println("Adding Player Card");
			addedCard = true;
		}
		if(!addedCard && dealerHand.hand[1] == null) {
			dealerHand.addCard(deck);
			System.out.println("Adding Dealer Card");
			addedCard = true;
		}
		if(!addedCard) {
			if(playerHand.handTotal % 10 == 8 || playerHand.handTotal % 10 == 9 ||
					dealerHand.handTotal % 10 == 8 || dealerHand.handTotal % 10 == 9) {
				/*if either the player or banker is dealt a total of eight or nine, 
				both the player and banker stand.*/
				System.out.println("Game Done");
				gameDone = true;
			}
			if(!gameDone) {
				if(playerHand.handTotal % 10 <= 5 && playerHand.hand[2] == null) {
					/*If the players total is five or less, 
					 * then the player will receive another card. Otherwise, the player will stand.*/
					playerHand.addCard(deck);
					System.out.println("Adding Player Card");
					addedCard = true;
				}
				if(!addedCard) {
					if((playerHand.hand[2] != null) && dealerHand.hand[2] == null) {
						int d = dealerHand.handTotal%10;
						int p = playerHand.hand[2].number;
						boolean draw = false;
//						System.out.println("Dealer tot: " + d);
//						System.out.println("Player tot: " + p);
						if(d <=2) {
							draw = true;
						}
						if(d == 3 && (p != 8)) {
							draw = true;
						}
						if(d == 4 && (p>=2 && p<=7)) {
							draw = true;
						}
						if(d == 5 && (p>=4 && p<=7)) {
							draw = true;
						}
						if(d == 6 && (p==6 || p==7)) {
							draw = true;				
						}
						if(draw) {
							dealerHand.addCard(deck);
							System.out.println("Adding Dealer Card 2");
							addedCard = true; 
						}
					}
					if(playerHand.hand[2] == null && dealerHand.hand[2] == null){
						if(dealerHand.handTotal % 10 <= 5) {
							dealerHand.addCard(deck);
							System.out.println("Adding Dealer Card 3");
						}
					}
				}
			}
		}
		try {
			Thread.sleep(gameSpeed);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if(!addedCard) {
			gameDone = true;
//			System.out.println("GAME OVER");
			findWinner();
		}
	}
	public void findWinner() {
		results = 0;
		if(playerHand.handTotal%10 == dealerHand.handTotal%10) {
			results = 1; //tie
			whoWon = "Tie Game!";
		}else if(playerHand.handTotal%10 > dealerHand.handTotal%10) {
			results = 2; //player
			whoWon = "Player Won!";
		}else if(playerHand.handTotal%10 < dealerHand.handTotal%10) {
			results = 3; //banker
			whoWon = "Banker Won!";
		}else {
			results = 4;
			System.out.println(playerHand.handTotal + ", " + dealerHand.handTotal);
		}
		countWinnings();
	}
	
	public void countWinnings() {
		for(int i = 0; i < gameChips.length; i++) {
			if(gameChips[i] != null) {
				totalWinnings += (gameChips[i].value);
				boolean lost = true;
				if(results == 1 && hitbox[0].bounds.equals(gameChips[i].placement.bounds)) {
					//Tie
					bonusWinnings += (gameChips[i].value*8);
					lost = false;
				}
				if(results == 2 && hitbox[2].bounds.equals(gameChips[i].placement.bounds)) {
					//Player
					bonusWinnings += (gameChips[i].value);
					totalWinnings += (gameChips[i].value);
					lost = false;
				}
				if(results == 3 && hitbox[1].bounds.equals(gameChips[i].placement.bounds)) {
					//Banker
					bonusWinnings += (gameChips[i].value*0.95);
					totalWinnings += (gameChips[i].value*0.95);
					lost = false;
				}
				if(lost) {
					bonusWinnings -= (gameChips[i].value);
					totalWinnings -= (gameChips[i].value);
				}
				System.out.println("Chip Value: " + gameChips[i].value + ", Total Winnings: " + totalWinnings);
//				System.out.println("Results: " + results);
			}
		}
	}
	
	
	
	
	public void placeChip(int value, GameBoard placement, int x, int y) {
		Chip newChip = new Chip(x, y, value, true);
		for(int i = 0; i < gameChips.length; i++) {
			if(gameChips[i] == null) {
				newChip.setBoundary(placement);
				newChip.setCoords(x, y);
				gameChips[i] = newChip;
				tableMoney += newChip.value;
				money -= newChip.value;
				break;
			}
		}
	}
	private void checkRemoveChip(boolean clearAll) {
		if(!clearAll) {
			for(int i = 0; i < gameChips.length; i++) {
				if(gameChips[i] != null && gameChips[i].bounds.contains(Controller.mousePoint) && MouseHandler.RIGHTCLICKED) {
					if(gameChips[i].canCancel) {
						money += gameChips[i].value;
						tableMoney -= gameChips[i].value;
						gameChips[i] = null;
						mouseChip = null;
						MouseHandler.MOUSEDOWN = false;
						MouseHandler.RIGHTCLICKED = false;
					}
				}
			}
		}else {
			for(int i = 0; i < gameChips.length; i++) {
				if(gameChips[i] != null && gameChips[i].canCancel) {
					money += gameChips[i].value;
					tableMoney -= gameChips[i].value;
					gameChips[i] = null;
					mouseChip = null;
					MouseHandler.MOUSEDOWN = false;
					MouseHandler.RIGHTCLICKED = false;
				}
			}
		}
	}
	public void addHitboxes() {
		hitbox[0] = new GameBoard("Tie Bet", new int[] {575, 700, 700, 575}, 
				new int[] {405, 405, 470, 470});
		hitbox[1] = new GameBoard("Banker Bet", new int[] {545, 730, 730, 545}, 
			new int[] {470, 470, 550, 550});
		hitbox[2] = new GameBoard("Player Bet", new int[] {535, 735, 735, 535}, 
			new int[] {550, 550, 630, 630});
	}
	public void addChips() {
		chips = new Chip[5];
		chips[0] = new Chip(25, Frame.HEIGHT-75, 1, false);
		chips[1] = new Chip(75, Frame.HEIGHT-75, 5, false);
		chips[2] = new Chip(125, Frame.HEIGHT-75, 10, false);
		chips[3] = new Chip(175, Frame.HEIGHT-75, 25, false);
		chips[4] = new Chip(225, Frame.HEIGHT-75, 100, false);
	}
}

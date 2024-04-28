package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import handlers.MouseHandler;
import main.CheckWin.STATUS;

public class Craps {

	public Rectangle[] buttons = {new Rectangle(50, 50, 150, 50), new Rectangle(900, 185, 128, 64), new Rectangle(1050, 185, 128, 64), 
			new Rectangle(1215, 50, 48, 48), new Rectangle(1250, 5, 25, 25)};
	public GameBoard[] hitbox = new GameBoard[35];//26
	private Image board, clearBoard, roll, markerImg, help, closeGame;
	private Integer[] winningNumbers = new Integer[11];
	private double money = 100;
	private int tableMoney = 0;
	private Chip[] chips;
	private Chip[] gameChips;
	private Chip mouseChip;
	public static int[] diceRoll = new int[2];
	private Integer[][] previousRolls = new Integer[10][3];
	public int[] lastTimeRolled = new int[8];

	public static Marker marker;
	public static boolean markerOn = false;
	public boolean inHelp = false;
	public String helpText = "";
	
	private double lastMoneyEarned = 0;
	
	public Craps() {
		board = new ImageLoader(ImageLoader.CrapsTable).getImage();
		clearBoard = new ImageLoader(ImageLoader.clearBoard).getImage();
		roll = new ImageLoader(ImageLoader.roll).getImage();
		markerImg = new ImageLoader(ImageLoader.marker).getImage();
		help = new ImageLoader(ImageLoader.help).getImage();
		closeGame = new ImageLoader(ImageLoader.closeGame).getImage();
		init();
	}
	
	public void init() {
		addHitboxes();
		addChips();
		marker = new Marker(10, 80, 0, null);
		gameChips = new Chip[99];
	}
	
	boolean inChipBounds = false;
	boolean inGameBounds = false;
	public void tick() {
		if(MouseHandler.MOUSEDOWN) {
			inChipBounds = false;
			inGameBounds = false;
			inHelp = false;
			helpText = "";
		}
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				if(i == 1) {
					//Clear chips
					for(int j = 0; j < gameChips.length; j++) {
						if(gameChips[j] != null && gameChips[j].canCancel) {
							mouseChip = null;
							money += gameChips[j].value;
							tableMoney -= gameChips[j].value;
							gameChips[j] = null;
						}
					}
					recalcWinningNumbers(null, false);
				}
				if(i == 2) {
					//Roll
					roll();
				}
				if(i == 3) {
					//Help
					inHelp = true;
				}
				if(i == 4) {
					//Close Game
					Controller.switchStates(Controller.STATE.MENU);
				}
				MouseHandler.MOUSEDOWN = false;
			}
		}
		for(int i = 0; i < hitbox.length; i++) {
			if(hitbox[i] != null) {
				//Placing Chip
				if(hitbox[i].bounds.contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN && !MouseHandler.RIGHTCLICKED) {
					if(mouseChip != null) {
						boolean canPlace = true;
						if(!markerOn) {
							if(hitbox[i].name == "4" || hitbox[i].name == "5" || hitbox[i].name == "6" ||
									hitbox[i].name == "8" || hitbox[i].name == "9" || hitbox[i].name == "10" || 
									hitbox[i].name == "Come Box" || hitbox[i].name == "Don't Come Box"
									|| hitbox[i].name == "Bet Against 4" || hitbox[i].name == "Bet Against 5" ||
									hitbox[i].name == "Bet Against 6" || hitbox[i].name == "Bet Against 8" ||
									hitbox[i].name == "Bet Against 9" || hitbox[i].name == "Bet Against 10") {
								canPlace = false;
							}
						}
						if(canPlace) {
							placeChip((int) mouseChip.value, hitbox[i], Controller.mousePoint.x-(mouseChip.bounds.width/2), Controller.mousePoint.y-(mouseChip.bounds.height/2));
						}
					}
					MouseHandler.MOUSEDOWN = false;
					inGameBounds = true;
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
		for(int i = 0; i < gameChips.length; i++) {
			//Select a chip
			//Remove Chip
			if(gameChips[i] != null && gameChips[i].bounds.contains(Controller.mousePoint) && MouseHandler.RIGHTCLICKED) {
				if(gameChips[i].canCancel) {
					money += gameChips[i].value;
					tableMoney -= gameChips[i].value;
					gameChips[i] = null;
					mouseChip = null;
					MouseHandler.MOUSEDOWN = false;
					MouseHandler.RIGHTCLICKED = false;
					recalcWinningNumbers(null, false);
				}
			}
			//Grab Chip
//			if(gameChips[i] != null && gameChips[i].bounds.contains(Controller.mousePoint) && !MouseHandler.RIGHTCLICKED) {
//				money += gameChips[i].value;
//				tableMoney -= gameChips[i].value;
//				mouseChip = gameChips[i];
//				gameChips[i] = null;
//				MouseHandler.MOUSEDOWN = false;
//				MouseHandler.RIGHTCLICKED = false;
//				recalcWinningNumbers(null, false);
//			}
		}
		if((inChipBounds == false && inGameBounds == false) || (mouseChip != null && mouseChip.value > money)) {
			mouseChip = null;
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
	}
	public void render(Graphics g) {
		g.setColor(new Color(160, 160, 160));
		g.fillRect(0, 0, Frame.WIDTH, Frame.HEIGHT);
		g.drawImage(board, 0, 0, Frame.WIDTH, Frame.HEIGHT, null);
		g.setColor(Color.BLACK);
		g.fillRect(165, 0, 815, 45);
		g.setColor(Color.WHITE);
		g.setFont(Controller.SMALL);
		g.drawString("Money: $" + ((double) Math.round(money * 100) / 100), 10, 15);
		g.drawString("Money on Table: $" + ((int)tableMoney), 10, 35);
		if(lastMoneyEarned > 0) {
			g.setColor(Color.GREEN);
		}
		if(lastMoneyEarned < 0) {
			g.setColor(Color.RED);
		}
		g.drawString("Money Earned: " + ((double) Math.round(lastMoneyEarned * 100) / 100), 10, 55);
		g.drawImage(closeGame, buttons[4].x, buttons[4].y, buttons[4].width, buttons[4].height, null);
		g.setColor(Color.WHITE);
		g.drawString("Roll:", 900, 175);
		if(diceRoll[0] != 0 && diceRoll[1] != 0) {
			g.drawImage(new ImageLoader("/dice/dice" + diceRoll[0] + ".png").getImage(), 950, 150, 32, 32, null);
			g.drawImage(new ImageLoader("/dice/dice" + diceRoll[1] + ".png").getImage(), 995, 150, 32, 32, null);
		}
		g.setFont(Controller.HUGE);
		for(int i = 0; i < previousRolls.length; i++) {			
			if(previousRolls[i][0] != null) {
				g.drawString("" + previousRolls[i][0], 100+(80*(i+1)), 40);
				g.drawRect(95+(80*(i+1)), 5, 50+(previousRolls[i][0]/10*25), 40);
				g.drawImage(new ImageLoader("/dice/dice" + previousRolls[i][1] + ".png").getImage(), 125+(80*(i+1))+(previousRolls[i][0]/10*25), 8, 16, 16, null);
				g.drawImage(new ImageLoader("/dice/dice" + previousRolls[i][2] + ".png").getImage(), 125+(80*(i+1))+(previousRolls[i][0]/10*25), 25, 16, 16, null);
			}
		}
		g.setFont(Controller.bigFont);
		g.drawString("Chips: ", 1060, 60);
		for(int i = 0; i < chips.length; i++) {
			g.drawImage(chips[i].getImage(), chips[i].bounds.x, chips[i].bounds.y, chips[i].bounds.width, chips[i].bounds.height, null);
		}
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
		if(mouseChip != null) {
			g.drawImage(mouseChip.getImage(), Controller.mousePoint.x - (mouseChip.bounds.width/2), 
					Controller.mousePoint.y-(mouseChip.bounds.height/2), mouseChip.bounds.width, mouseChip.bounds.height, null);
		}
		g.setColor(Color.YELLOW);
		g.drawString("Horn Bet", 770, 290);
		g.setColor(Color.RED);
		if(!inHelp) {
			for(int i = 0; i < hitbox.length; i++) {
				if(hitbox[i] != null) {
					g.drawPolygon(hitbox[i].bounds);
					//drawMouse Hover Over Option
					if(hitbox[i].bounds.contains(Controller.mousePoint) && (mouseChip == null)) {
						int boxtotal = 0;
						g.setColor(Color.yellow);
						g.drawPolygon(hitbox[i].bounds);
						for(int j = 0; j < gameChips.length; j++) {
							if(gameChips[j] != null) {
								if(hitbox[i].bounds == gameChips[j].getBoundary()) {
									boxtotal += gameChips[j].value;
								}
							}
						}
						g.setColor(new Color(50, 50, 50, 175));
						g.fillRect(Controller.mousePoint.x-25, Controller.mousePoint.y-35, g.getFontMetrics().stringWidth("$" + boxtotal) + 25, 35);
						g.setColor(Color.yellow);
						g.drawString("$" + boxtotal, Controller.mousePoint.x-18, Controller.mousePoint.y-14);
						g.setColor(Color.RED);
					}
				}
			}
			//winning numbers:
			g.setColor(Color.black);
			g.setFont(Controller.SMALL);
			g.drawString("Winning Numbers: ", 100, 610);
			g.fillRect(50, 615, 700, 50);
			for(int i = 0; i < 11; i++) {
				g.setColor(Color.GRAY);
				g.setFont(Controller.HUGE);
				String spacing = i + "";
				g.drawString("" + (i+2), 30 + ((i+1)*50) + (20*spacing.length()), 655);
				
				if(winningNumbers[i] != null) {
					g.setColor(Color.GREEN);
					g.drawString("" + (i+2), 30 + ((i+1)*50) + (20*spacing.length()), 655);
				}
				g.setFont(Controller.bigBold);
			}
			g.setColor(Color.RED);
			g.drawString("#" + lastTimeRolled[0], 885, 335);
			g.drawString("#" + lastTimeRolled[1], 1075, 335);
			g.drawString("#" + lastTimeRolled[2], 885, 410);
			g.drawString("#" + lastTimeRolled[3], 1075, 410);
			g.drawString("#" + lastTimeRolled[4], 885, 490);
			g.drawString("#" + lastTimeRolled[5], 1002, 490);
			g.drawString("#" + lastTimeRolled[6], 1125, 490);
			g.drawString("#" + lastTimeRolled[7], 885, 570);
			
			g.drawImage(help, buttons[3].x, buttons[3].y, buttons[3].width, buttons[3].height, null);
			g.drawImage(markerImg, marker.location.x, marker.location.y, 32, 32, null);
			g.drawImage(clearBoard, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
			g.drawImage(roll, buttons[2].x, buttons[2].y, buttons[2].width, buttons[2].height, null);
		}else {
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
		}
	}
	int controlled = 0;
	public void roll() {
		Random rand = new Random();
		int total = 0;
		int comeBoxTotal = 0;
		int dontComeBoxTotal = 0;
		diceRoll[0] = rand.nextInt(6)+1;
		diceRoll[1] = rand.nextInt(6)+1;
		lastMoneyEarned = 0;
		controlled += 1;
		total = diceRoll[0] + diceRoll[1];
		for(int i = 0; i < gameChips.length; i++) {
			if(gameChips[i] != null) {
				CheckWin gameStatus = gameChips[i].placement.didWin(diceRoll, marker.isPlaced());
				if(gameStatus.status == STATUS.WON) {
//					System.out.println("----------------------");
//					System.out.println("Original Money: " + money);
					money += gameChips[i].value;
//					System.out.println("Chip Value: " + gameChips[i].value);
					money += ((double)gameChips[i].value)*gameStatus.getPayout();
//					System.out.println("Winnings: " + ((double)gameChips[i].value)*gameStatus.getPayout());
//					System.out.println("Total: " + money);
//					System.out.println("----------------------");
					tableMoney -= gameChips[i].value;
					lastMoneyEarned += ((double)gameChips[i].value)*gameStatus.getPayout();
					gameChips[i] = null;
				}else if(gameStatus.status == STATUS.LOSS) {
					//LOSS
					tableMoney -= gameChips[i].value;
					lastMoneyEarned -= gameChips[i].value;
					gameChips[i] = null;
					
				}else {
					//NEUTRAL
					if(gameChips[i] != null && gameChips[i].placement.name == "Come Box") {
						comeBoxTotal += gameChips[i].value;
						gameChips[i] = null;
					}
					if(gameChips[i] != null && gameChips[i].placement.name == "Don't Come Box") {
						dontComeBoxTotal += gameChips[i].value;
						gameChips[i] = null;
					}
				}
			}
		}
		if(total == 7) {
			marker.location.x = 10;
			marker.location.y = 80;
			marker.setGameBoard(null, 0);
			tableMoney = 0;
			gameChips = new Chip[99];
			markerOn = false;
		}else {
			//Place Marker
			if(!markerOn) {
				if(total < 7) {
					if(total != 2 && total != 3) {
						marker.location.x = 275 + ((total-4)*100);
						marker.setGameBoard(hitbox[7+total-4], total);
						marker.location.y = 175;
						markerOn = true;
					}
				}else {
					if(total != 11 && total != 12) {
						marker.location.x = 275 + ((total-5)*100);	
						marker.setGameBoard(hitbox[7+total-5], total);
						marker.location.y = 175;
						markerOn = true;
					}
				}
				for(int i = 0; i < gameChips.length; i++) {
					if(gameChips[i] != null && gameChips[i].placement.name == "Pass Line" && markerOn) {
						gameChips[i].canCancel = false;
					}
				}
				marker.value = total;
			}else {
				if(total == marker.point) {
					//Won the game, restart it
					money += tableMoney;
					tableMoney = 0;
					gameChips = new Chip[99];
					markerOn = false;
					marker.location.x = 10;
					marker.location.y = 80;
					marker.setGameBoard(null, 0);
					marker.value = 0;
				}
			}
		}
		if(total < 7) {
			addComeBox(comeBoxTotal, hitbox[7+total-4], total);
			addDontComeBox(dontComeBoxTotal, hitbox[26+total-4], total);
		}else {
			addComeBox(comeBoxTotal, hitbox[7+total-5], total);	
			addDontComeBox(dontComeBoxTotal, hitbox[26+total-5], total);
		}
		for(int i = 0; i < lastTimeRolled.length; i++) {
			lastTimeRolled[i] = lastTimeRolled[i]+1;
		}
		if(diceRoll[0] == 3 && diceRoll[1] == 3) {
			lastTimeRolled[0] = 0;
		}
		if(diceRoll[0] == 5 && diceRoll[1] == 5) {
			lastTimeRolled[1] = 0;
		}
		if(diceRoll[0] == 4 && diceRoll[1] == 4) {
			lastTimeRolled[2] = 0;
		}
		if(diceRoll[0] == 2 && diceRoll[1] == 2) {
			lastTimeRolled[3] = 0;
		}
		if((diceRoll[0] == 1 && diceRoll[1] == 2) || (diceRoll[0] == 2 && diceRoll[1] == 1)) {
			lastTimeRolled[4] = 0;
		}
		if(diceRoll[0] == 1 && diceRoll[1] == 1) {
			lastTimeRolled[5] = 0;
		}
		if(diceRoll[0] == 6 && diceRoll[1] == 6) {
			lastTimeRolled[6] = 0;
		}
		if((diceRoll[0] == 6 && diceRoll[1] == 5) || (diceRoll[0] == 5 && diceRoll[1] == 6)) {
			lastTimeRolled[7] = 0;
		}
		addRollHistory(total, diceRoll[0], diceRoll[1]);
		recalcWinningNumbers(null, false);
	}
	public void addDontComeBox(int dontComeBoxTotal, GameBoard hitbox, int total) {
		for(int i = 0; i < gameChips.length; i++) {
			if(gameChips[i] == null && dontComeBoxTotal > 0) {
				for(int j = chips.length; j > 0; j--) {
					if(chips[j-1].value <= dontComeBoxTotal) {
						gameChips[i] = new Chip((hitbox.bounds.xpoints[0]+50), (hitbox.bounds.ypoints[0] + 5), (int)chips[j-1].value, false);
						gameChips[i].setBoundary(hitbox);
						dontComeBoxTotal -= chips[j-1].value;
						break;
					}
				}
			}
		}
	}
	public void addComeBox(int comeBoxTotal, GameBoard hitbox, int total) {
		for(int i = 0; i < gameChips.length; i++) {
			if(gameChips[i] == null && comeBoxTotal > 0) {
				for(int j = chips.length; j > 0; j--) {
					if(chips[j-1].value <= comeBoxTotal) {
						gameChips[i] = new Chip((hitbox.bounds.xpoints[0]+50), (hitbox.bounds.ypoints[0] + 25), (int)chips[j-1].value, false);
						gameChips[i].setBoundary(hitbox);
						comeBoxTotal -= chips[j-1].value;
						break;
					}
				}
			}
		}
	}
	public void addRollHistory(int total, int val1, int val2) {
		for(int i = previousRolls.length-1; i > 0; i--) {
			previousRolls[i][0] = previousRolls[i-1][0];
			previousRolls[i][1] = previousRolls[i-1][1];
			previousRolls[i][2] = previousRolls[i-1][2];
		}
		previousRolls[0][0] = total;
		previousRolls[0][1] = val1;
		previousRolls[0][2] = val2;
		
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
		recalcWinningNumbers(newChip, true);
	}

	public void recalcWinningNumbers(Chip chip, boolean adding) {
		Integer[] winningNums = new Integer[11];
		if(adding) {
			if(chip.placement.name == "Pass Line") {
				chip.placement.setWinningNumbers();
			}
			winningNums = chip.placement.winnings;
			for(int i = 0; i < winningNums.length; i++) {
				if(winningNums[i] != null) {
					winningNumbers[winningNums[i]-2] = winningNums[i];
				}
			}
		}else {
			winningNumbers = new Integer[11];
			for(int i = 0; i < gameChips.length; i++) {
				if(gameChips[i] != null) {
					if(gameChips[i].placement.name == "Pass Line") {
						gameChips[i].placement.setWinningNumbers();
					}
					for(int j = 0; j < gameChips[i].placement.winnings.length; j++) {
						if(gameChips[i].placement.winnings[j] != null) {
							winningNumbers[gameChips[i].placement.winnings[j]-2] = gameChips[i].placement.winnings[j];
						}else {
							break;
						}
					}
				}
			}
		}
	}
	public void addChips() {
		chips = new Chip[5];
		chips[0] = new Chip(950, 75, 1, false);
		chips[1] = new Chip(1000, 75, 5, false);
		chips[2] = new Chip(1050, 75, 10, false);
		chips[3] = new Chip(1100, 75, 25, false);
		chips[4] = new Chip(1150, 75, 100, false);
	}
	public void addHitboxes() {
		//Pass
		hitbox[0] = new GameBoard("Pass Line", new int[] {40, 100, 102, 105, 119, 145, 761, 760, 134, 85, 55, 41, 40}, 
				new int[] {137, 92, 451, 475, 497, 514, 519, 585, 583, 557, 512, 458, 137});
		//Don't Pass
		hitbox[1] = new GameBoard("Don't Pass Line", new int[] {103, 160, 160, 103, 103}, 
				new int[] {91, 46, 335, 335, 91});
		hitbox[2] = new GameBoard("Don't Pass Line", new int[] {262, 760, 760, 262, 262}, 
				new int[] {450, 450, 515, 515, 450});
		//Big 6/8
		hitbox[3] = new GameBoard("Big 6/8", new int[] {103, 100, 110, 125, 155, 260, 260, 160, 103}, 
				new int[] {340, 460, 480, 505, 517, 517, 450, 340, 340});
		//Don't Come Box
		hitbox[4] = new GameBoard("Don't Come Box", new int[] {162, 162, 260, 260, 162}, 
				new int[] {45, 224, 224, 45, 45});
		//Come Box
		hitbox[5] = new GameBoard("Come Box", new int[] {162, 162, 760, 760, 162}, 
				new int[] {225, 338, 338, 225, 225});
		//Field
		hitbox[6] = new GameBoard("Field", new int[] {164, 260, 760, 760, 164}, 
						new int[] {340, 447, 447, 340, 340});
		
		//4
		hitbox[7] = new GameBoard("4", new int[] {261, 360, 360, 261}, 
						new int[] {105, 105, 210, 210});
		//5
		hitbox[8] = new GameBoard("5", new int[] {362, 460, 460, 361}, 
				new int[] {105, 105, 210, 210});
//		//6
		hitbox[9] = new GameBoard("6", new int[] {462, 560, 560, 461}, 
				new int[] {105, 105, 210, 210});
//		//8
		hitbox[10] = new GameBoard("8", new int[] {562, 660, 660, 561}, 
				new int[] {105, 105, 210, 210});
//		//9
		hitbox[11] = new GameBoard("9", new int[] {662, 760, 760, 661}, 
				new int[] {105, 105, 210, 210});
//		//10
		hitbox[12] = new GameBoard("10", new int[] {762, 860, 860, 761}, 
				new int[] {105, 105, 210, 210});
		//3 Hard Way
		hitbox[13] = new GameBoard("3 Hard Way", new int[] {880, 1058, 1058, 880}, 
			new int[] {315, 315, 388, 388});
		//5 Hard Way
		hitbox[14] = new GameBoard("5 Hard Way", new int[] {1060, 1240, 1240, 1060}, 
				new int[] {315, 315, 390, 390});
		//4 Hard Way
		hitbox[15] = new GameBoard("4 Hard Way", new int[] {880, 1058, 1058, 880}, 
				new int[] {392, 392, 470, 470});
		//2 Hard Way
		hitbox[16] = new GameBoard("2 Hard Way", new int[] {1060, 1240, 1240, 1060}, 
				new int[] {392, 392, 470, 470});
		//1to2
		hitbox[17] = new GameBoard("1to2 Hard Way", new int[] {880, 998, 998, 880}, 
				new int[] {471, 471, 545, 545});
		//1to1
		hitbox[18] = new GameBoard("1 Hard Way", new int[] {1000, 1118, 1118, 1000}, 
				new int[] {471, 471, 545, 545});
		//6 Hard Way
		hitbox[19] = new GameBoard("6 Hard Way", new int[] {1120, 1240, 1240, 1120}, 
				new int[] {471, 471, 545, 545});
		//6to5 Hard Way
		hitbox[20] = new GameBoard("6to5 Hard Way", new int[] {880, 1240, 1240, 880}, 
				new int[] {548, 548, 627, 627});
		//E Craps
		hitbox[21] = new GameBoard("E Craps", new int[] {790, 832, 832, 790}, 
				new int[] {335, 335, 688, 688});
		//C Craps
		hitbox[22] = new GameBoard("C Craps", new int[] {834, 872, 872, 834}, 
				new int[] {335, 335, 688, 688});
		hitbox[23] = new GameBoard("Any Seven", new int[] {879, 1235, 1235, 879}, 
				new int[] {270, 270, 310, 310});
		hitbox[24] = new GameBoard("Any Crap", new int[] {879, 1235, 1235, 879}, 
				new int[] {628, 628, 670, 670});
		hitbox[25] = new GameBoard("Horn Bet", new int[] {768, 872, 872, 768}, 
				new int[] {260, 260, 315, 315});
		hitbox[26] = new GameBoard("Bet Against 4", new int[] {262, 361, 361, 262}, 
				new int[] {45, 45, 102, 102});
		hitbox[27] = new GameBoard("Bet Against 5", new int[] {362, 461, 461, 362}, 
				new int[] {45, 45, 102, 102});
		hitbox[28] = new GameBoard("Bet Against 6", new int[] {461, 562, 562, 461}, 
				new int[] {45, 45, 102, 102});
		hitbox[29] = new GameBoard("Bet Against 8", new int[] {562, 661, 661, 562}, 
				new int[] {45, 45, 102, 102});
		hitbox[30] = new GameBoard("Bet Against 9", new int[] {661, 762, 762, 661}, 
				new int[] {45, 45, 102, 102});
		hitbox[31] = new GameBoard("Bet Against 10", new int[] {762, 861, 861, 762}, 
				new int[] {45, 45, 102, 102});
		for(int i = 0; i < hitbox.length; i++) {
			if(hitbox[i] != null) {
				hitbox[i].setWinningNumbers();
			}
		}
	}
}

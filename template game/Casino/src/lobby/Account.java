package lobby;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import handlers.MouseHandler;
import main.Controller;
import main.Frame;
import main.ImageLoader;

public class Account {

	private Image closeGame;
	private Rectangle[] buttons = {new Rectangle(1250, 5, 25, 25)};
	private AccountDetails[] accountDetails = new AccountDetails[5];
	private String[] gametype = {"Craps", "Blackjack", "Slots"};
	public Account() {
		closeGame = new ImageLoader(ImageLoader.closeGame).getImage();
		accountDetails[0] = new AccountDetails("Player 1");
		accountDetails[1] = new AccountDetails("Player 2");
	}
	public void tick() {
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				if(i == 0) {
					Controller.switchStates(Controller.STATE.MENU);
				}
				MouseHandler.MOUSEDOWN = false;
			}
		}
	}
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, Frame.WIDTH, Frame.HEIGHT);
		g.drawImage(closeGame, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
		//PROFILE
		g.setColor(Color.BLACK);
		g.drawLine(375, 0, 375, Frame.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(Controller.bigBold);
		g.drawString("Select Profile:", 100, 40);
		//GAMETYPE
		g.drawString("Select Game Type:", Frame.WIDTH/2-g.getFontMetrics().stringWidth("Select Game Type:")/2+50, 75);
		for(int i = 0; i < gametype.length; i++) {
			g.drawString(gametype[i], (150*(i+3)), 125);
			g.drawRoundRect((150*(i+3)-30), 95, 125, 50, 15, 15);
		}
		//STATISTICS
		g.drawString("Statistics: ", Frame.WIDTH/2-g.getFontMetrics().stringWidth("Statistics: ")/2+50, 200);
		g.drawString("Hands Won: 50", 400, 250);
		g.drawString("Hands Lost: 32", 400, 275);
		g.drawString("Total Money Earned: $7,902", 400, 300);
		g.drawString("Total Money Lost: $5,466", 400, 325);
		//ACHIEVEMENTS
		g.drawString("Achievements: ", 400, 575);
		g.setColor(Color.BLACK);
		g.drawLine(375, 550, Frame.WIDTH, 550);
		g.setColor(Color.WHITE);
		g.setFont(Controller.NORMAL);
		for(int i = 0; i < accountDetails.length; i++) {
			if(accountDetails[i] != null) {
				g.drawString(accountDetails[i].playerName, 50, 100*(i+1));
				g.drawRoundRect(25, 100*(i+1)-40, 300, 80, 15, 15);
				g.drawString("Money: $" + accountDetails[i].money, 200, 100*(i+1)-15);
			}
		}
		
	}
}

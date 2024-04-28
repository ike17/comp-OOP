package card_counter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Timer;

import handlers.MouseHandler;
import main.Controller;
import main.Frame;
import main.ImageLoader;
import universal.Cards;
import universal.Cards.cardtype;
import universal.Deck;

public class CardCounter {

	private Rectangle[] buttons = {
			new Rectangle(1250, 5, 25, 25), new Rectangle(400, 300, 100, 50),
			new Rectangle(50, 600, 100, 50), new Rectangle(150, 600, 100, 50)
			};
	private int counter = 0;
	private int speed = 1750;
	private Deck deck;
	private Cards card;
	private Image closeGame, start, speedInc, speedDec;
	
	private Timer timer;
	
	boolean setTimer = false;
	
	
	private boolean inGame = false;
	
	public CardCounter() {
		init();
		deck = new Deck(1);
	}
	
	public void init() {
		closeGame = new ImageLoader(ImageLoader.closeGame).getImage();
		start = new ImageLoader(ImageLoader.dealButton).getImage();
		speedInc = new ImageLoader(ImageLoader.hitButton).getImage();
		speedDec = new ImageLoader(ImageLoader.standButton).getImage();
//		deck = new Deck(5);
	}
	
	public void tick() {
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				if(i == 0) {
					//Exit game
					if(timer != null) {
						timer.stop();
						timer = null;
					}
					Controller.switchStates(Controller.STATE.MENU);
				}
				if(i == 1) {
					//Start
					inGame = true;
					setTimer = true;
				}
				if(i == 2) {
					//Speed Increment
					if(speed < 5000) {
						setTimer = true;
						speed += 250;
					}
				}
				if(i == 3) {
					//Speed Decrement
					if(speed > 1250) {
						setTimer = true;
						speed -= 250;
					}
				}
				MouseHandler.MOUSEDOWN = false;
			}
		}
		if(inGame) {
			if(setTimer) {
				if(timer != null) {
					timer.stop();
				}
				timer = null;
				timer = new Timer(speed, (ActionListener) new AbstractAction() {
					   @Override
					   public void actionPerformed(ActionEvent ae) {
					       //action that you want performed 
					   	addCard();
					   	timer = null;
					   }
				});
				timer.start();
				setTimer = false;
			}
		}
	}
	
	public void addCard() {
		card = deck.pullCard();
		//2-6 += 1, 7-9 = 0, 10-Ace -= 1;
		//https://www.blackjackapprenticeship.com/how-to-count-cards/
		if(card.number >= 2 && card.number <= 6) {
			counter += 1;
		}else if(card.number >= 10 || card.number == 1) {
			counter -= 1;
		}else {
			//do nothing == 0
		}
	}
	
	public void render(Graphics g) {
		if(!refresh) {
		g.setColor(new Color(50, 150, 50));
		g.fillRect(0, 0, Frame.WIDTH, Frame.HEIGHT);
		g.drawImage(closeGame, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
		if(!inGame) {
			g.drawImage(start, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
		}else {
			if(card != null) {
				g.drawImage(card.cardImage, Frame.WIDTH/2-card.width/2, 150, null);
				g.setColor(Color.black);
				g.setFont(Controller.NORMAL);
				g.drawString("Card Value: " + counter, 
						Frame.WIDTH/2-g.getFontMetrics().stringWidth("Card Value: " + counter)/2, 
						150 + card.height+25);
			}
		}
		g.drawImage(speedInc, buttons[2].x, buttons[2].y, buttons[2].width, buttons[2].height, null);
		g.drawImage(speedDec, buttons[3].x, buttons[3].y, buttons[3].width, buttons[3].height, null);
		g.drawString("Card Speed: " + speed, buttons[2].x, buttons[2].y+buttons[2].height+25);
		showcards(g);
		}
	}
	public boolean refresh = false;
	public void showcards(Graphics g) {
		int temp = 0;
		int temp2 = 0;
		for(int i = 0; i < 52; i++) {
			if(i == 13) {
				temp += 150;
				temp2 = 0;
			}
			if(i == 26) {
				temp += 150;
				temp2 = 0;
			}
			if(i == 39) {
				temp += 150;
				temp2 = 0;
			}
			if(deck != null) {
				g.drawImage(deck.pullCard().cardImage, temp2, temp, null);
				temp2+= 80;

			}
//			System.out.println(decktest[i].toString());
		}
		refresh = true;
		temp = 0;
	}
}
package lobby;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import handlers.MouseHandler;
import main.Controller;
import main.Frame;
import main.ImageLoader;
import main.Controller.STATE;

public class Menu {

	public MenuItem[] buttons;
	
	private int worldX = 0;
	private int worldY = 0;
	
	private Image casinoFloor;
	
	public Menu() {
		init();
	}
	
	public void init() {
		casinoFloor = new ImageLoader(ImageLoader.casinoFloor).getImage();
		buttons = new MenuItem[5];
		buttons[0] = new MenuItem(75, 50, 275, 150, Controller.STATE.CRAPS);
		buttons[1] = new MenuItem(550, 225, 300, 200, Controller.STATE.BLACKJACK);
		buttons[2] = new MenuItem(1125, 10, 200, 225, Controller.STATE.ACCOUNT);
		buttons[3] = new MenuItem(1300, 365, 375, 300, Controller.STATE.COUNTER);
		buttons[4] = new MenuItem(1035, 900, 500, 300, Controller.STATE.BACCARAT);
	}
	
	public void tick() {
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i] != null) {
				buttons[i].tick(worldX, worldY);
			}
		}
		if(Controller.mousePoint.x > Frame.WIDTH-50 && worldX-Frame.WIDTH > -6400/2) {
			worldX -= 10;
		}
		if(Controller.mousePoint.x < 50 && worldX < 0) {
			worldX += 10;
		}
		if(Controller.mousePoint.y > Frame.HEIGHT-50 && worldY-Frame.HEIGHT > -3600/2) {
			worldY -= 10;
		}
		if(Controller.mousePoint.y < 50 && worldY < 0) {
			worldY += 10;
		}
		if(MouseHandler.MOUSEDOWN) {
			System.out.println("WorldX: " + (Controller.mousePoint.x-worldX) + ", WorldY: " + (Controller.mousePoint.y+worldY));
			MouseHandler.MOUSEDOWN = false;
		}
	}
	public void render(Graphics g) {
		g.drawImage(casinoFloor, 0+worldX, 0+worldY, 6400/2, 3600/2, null);
		g.setColor(new Color(160, 160, 160));
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i] != null) {
				buttons[i].render(g);
			}
		}
		g.setColor(Color.WHITE);
	}
}

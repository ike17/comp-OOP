package lobby;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import handlers.MouseHandler;
import main.Controller;

public class MenuItem {

	public int initialX;
	public int initialY;
	
	public int currentX = 0;
	public int currentY = 0;
	
	public Rectangle bounds;
	
	private Controller.STATE state;
	
	public MenuItem(int initialX, int initialY, int width, int height, Controller.STATE state) {
		this.initialX = initialX;
		this.initialY = initialY;
		this.state = state;
		bounds = new Rectangle(initialX, initialY, width, height);
	}
	public void tick(int x, int y) {
		currentX = initialX + x;
		currentY = initialY + y;
		bounds.x = currentX;
		bounds.y = currentY;
		if(bounds.contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
			Controller.switchStates(state);
			MouseHandler.MOUSEDOWN = false;
		}
	}
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		if(bounds.contains(Controller.mousePoint)) {
			g.setColor(new Color(255, 255, 255, 100));
			g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		}
	}
}

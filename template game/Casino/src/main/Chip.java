package main;

import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;

public class Chip {

	public int x;
	public int y;
	public double value;
	public Image img;
	public Rectangle bounds;
	public GameBoard placement;
	public boolean canCancel = true;
	
	public Chip(int x, int y, int value, boolean canCancel) {
		this.x = x;
		this.y = y;
		this.value = value;
		this.canCancel = canCancel;
		bounds = new Rectangle(x, y, 48, 48);
	}
	public void setBoundary(GameBoard bounds) {
		this.placement = bounds;
	}
	public Polygon getBoundary() {
		return placement.bounds;
	}

	public Image getImage() {
		String chip = "/chips/Chip" + ((int)value) + ".png";
		this.img = new ImageLoader(chip).getImage();
		return img;
	}
	public void setCoords(int x2, int y2) {
		x = x2;
		y = y2;
	}
}

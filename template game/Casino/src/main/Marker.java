package main;

import java.awt.Point;

public class Marker extends Chip{

	public Point location = new Point(0, 0);
	public int point = 0; // what it currently is on
	private GameBoard spot;
	
	public Marker(int x, int y, int value, GameBoard spot) {
		super(x, y, value, false);
		location.x = x;
		location.y = y;
		this.spot = spot;
	}

	public void setGameBoard(GameBoard spot, int point) {
		this.spot = spot;
		this.point = point;
	}
	public GameBoard getGameBoard() {
		return spot;
	}

	public boolean isPlaced() {
		if(spot == null) {
			return false;
		}else {
			return true; 
		}
	}
}

 package universal;

import java.awt.Image;

import main.Controller;
import main.ImageLoader;

public class Cards {

	public enum cardtype{
		SPADE,
		HEART,
		CLUB,
		DIAMOND
	}
	
	public cardtype type;
	public int number;
	public int xoffset;
	public int yoffset;
	public int width = 120;
	public int height = 162;
	public Image cardImage;
	
	public Cards(cardtype type, int number, int cardno) {
		this.type = type;
		this.number = cardno;
		
		cardImage = Controller.ci.getCardImage(number, type);
		if(cardno > 10) {
			this.number = 10;
		}
	}
	public String toString() {
		return "Current Card: " + type + ": " + number;
	}
}

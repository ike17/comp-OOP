package universal;

import java.awt.Image;
import java.awt.image.BufferedImage;

import main.ImageLoader;
public class CardImage {
	public enum cardtype{
		SPADE,
		HEART,
		CLUB,
		DIAMOND
	}

	public cardtype type;
	public int xoffset = 0;
	public int yoffset = 0;
	public int width = 120;
	public int height = 160;
	public int number;
	//Loader class to help speed up the load process of cards
	public Image[] cardImages = null;
	public BufferedImage wholeImage = null;
	public CardImage() {
		cardImages = new Image[53];
//		wholeImage = new ImageLoader("/cards/cards.png").getImage();
		wholeImage = new ImageLoader("/cards/Cardset2.png").getImage();
		setCardImages();
	}
	public void setCardImages() {
		int temp = 0;
//		for(int i = 0; i < 4; i++) {
//			xoffset = 0;
//			for(int j = 0; j < 13; j++) {
//				cardImages[temp+j] = wholeImage.getSubimage(xoffset, yoffset, width, height);
//				xoffset = 121*(j+1);
//				if(j < 12) {
//					xoffset += (20*(j+1));
//				}
//			}
//			yoffset += 167;
//			temp += 13;
//		}
		for(int i = 0; i < 52; i++) {
			cardImages[i] = wholeImage.getSubimage(i*(width), 0, width, height);
		}
		//1819 (141*j+1)
	}
	public Image getCardImage(int number, universal.Cards.cardtype type) {
		int og = number;
//		if(type == universal.Cards.cardtype.HEART) {
//			number += 13;
//		}
//		if(type == universal.Cards.cardtype.DIAMOND) {
//			number += 26;
//		}
//		if(type == universal.Cards.cardtype.CLUB) {
//			number += 39;
//		}
//		System.out.println("Current Number " + number + " is " + type + " of " + og);
		return cardImages[number];
	}
	
}

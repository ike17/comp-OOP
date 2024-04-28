package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	//How to add resource folder to the project(where images will be stored)
	//https://techdora.com/how-to-add-eclipse-resource-folder-and-files/
	
	private BufferedImage img;
	
	public static String LOGO = "/b";
	
	//Universal
	public static String closeGame = "/close.png";
	
	//Casino Floor
	public static String casinoFloor = "/casino_floor/CasinoFloor.png";
	
	//blackjack 
	public static String blackjackTable = "/BlackJack/Blackjack_Table.png";
	public static String dealButton = "/BlackJack/dealButton.png";
	public static String hitButton = "/BlackJack/hitButton.png";
	public static String clearbetButton = "/BlackJack/clearbetButton.png";
	public static String standButton = "/BlackJack/standButton.png";
	public static String surrenderButton = "/BlackJack/surrenderButton.png";
	public static String doubledownButton = "/BlackJack/doubledownButton.png";
	public static String splitButton = "/BlackJack/splitButton.png";
	public static String repeatbetButton = "/BlackJack/repeatbetButton.png";
	public static String insuranceButton = "/BlackJack/insuranceButton.png";
	public static String simulateOnButton = "/BlackJack/simulateOn.png";
	public static String simulateOffButton = "/BlackJack/simulateOff.png";
	// Craps
	public static String CrapsTable = "/CrapsTable.png";
	public static String clearBoard = "/ClearBoard.png";
	public static String roll = "/Roll.png";
	public static String marker = "/Marker.png";
	public static String help = "/Help.png";
	
	//Baccarat
	public static String baccaratbg = "/baccarat/baccarat.png";
	
	public ImageLoader(String path) {
		try {
			img = ImageIO.read(ImageLoader.class.getResourceAsStream(path));//Loads image as resource.

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public BufferedImage getImage() {
		return img;
	}
//	public BufferedImage getSubImage(int section) {
//		return img.getSubimage(0, section*25, 50, 25);
		//If we want a whole spritemap and split in sections. 
//	}
	
}

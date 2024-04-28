package handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class KeyHandler implements KeyListener{

	public static boolean LEFT = false;
	public static boolean RIGHT = false;
	public static boolean UP = false;
	public static boolean DOWN = false;
	
	public static String RECENTCHAR = "";
	public static boolean BACKSPACE = false;
	
	public KeyHandler() {
		
	}
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 87 || e.getKeyCode() == 38) {
			UP = true;
		}
		if(e.getKeyCode() == 65 || e.getKeyCode() == 37) {
			LEFT = true;
		}
		if(e.getKeyCode() == 68 || e.getKeyCode() == 39) {
			RIGHT = true;
		}
		if(e.getKeyCode() == 83 || e.getKeyCode() == 40) {
			DOWN = true;
		}
		
		if(e.getKeyCode() >= 46 && e.getKeyCode() <= 90 || e.getKeyCode() == 32) {//32 = space
			RECENTCHAR = Character.toString(e.getKeyChar());
		}
		if(e.getKeyCode() == 8) {
			BACKSPACE = true;
		}
		System.out.println("The Key pressed is: " + e.getKeyCode());
		//Prints the keycode of the character pressed. We can use this to check if the user presses a certain character
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 87 || e.getKeyCode() == 38) {
			UP = false;
		}
		if(e.getKeyCode() == 65 || e.getKeyCode() == 37) {
			LEFT = false;
		}
		if(e.getKeyCode() == 68 || e.getKeyCode() == 39) {
			RIGHT = false;
		}
		if(e.getKeyCode() == 83 || e.getKeyCode() == 40) {
			DOWN = false;
		}
		//Set the variable to released when the button is let go. 
	}

}

package handlers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import main.Controller;

public class MouseHandler implements MouseMotionListener, MouseListener, MouseWheelListener{

	public static boolean MOUSEDOWN = false;
	public static boolean RIGHTCLICKED = false;
	public static boolean hasPressed = false;
	
	public MouseHandler() {
		Controller.mousePoint.x = 300;
		Controller.mousePoint.y = 300;
	}
	
	public void mouseClicked(MouseEvent e) {	
//		System.out.println("Mouse X: " + e.getX() + ", Y: " + e.getY() + "");
		//Get x & Y of where the mouse was pressed
	}
	public void mousePressed(MouseEvent e) {
		if(!hasPressed) {
			MOUSEDOWN = true;
			hasPressed = true;
			//Set the global that the mouse is down if any class needs to know.
		}
		if(e.getButton() == e.BUTTON3) {
			RIGHTCLICKED = true;
		}
	}
	public void mouseReleased(MouseEvent e) {
		MOUSEDOWN = false;
		RIGHTCLICKED = false;
		hasPressed = false;
		//Mouse has been released
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
		Controller.mousePoint.x = 300;
		Controller.mousePoint.y = 300;
	}
	public void mouseDragged(MouseEvent e) {
		MOUSEDOWN = false;
		//Cancel the mousedown 
	}
	public void mouseMoved(MouseEvent e) {
		Controller.mousePoint = e.getPoint();
	}
	public void mouseWheelMoved(MouseWheelEvent e) {
	       int notches = e.getWheelRotation();
	       if (notches < 0 && Controller.scrollAmt > 0-Controller.scrollLmt) {
//	    	    System.out.println("Mouse Up");
	    	   Controller.scrollAmt -= 30;
	       }
	    	 if(notches > 0 && Controller.scrollAmt < 0) {
//	    	    System.out.println("Mouse DOWN");
	    		   Controller.scrollAmt += 30;
	       }
	    	 System.out.println(Controller.scrollAmt);
	}
}

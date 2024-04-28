package main;

import javax.swing.JFrame;

public class Frame{

	private static JFrame frame;
	
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	
	private static int actualwidth;
	private static int actualheight;
	
	public static String TITLE = "Casino Game";
	
	public static void main(String[] args) {
		actualwidth = WIDTH + 15;
		actualheight = HEIGHT + 15;
		frame = new JFrame(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Controller(frame));
		frame.pack();	
		frame.setSize(actualwidth, actualheight);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		//Simple items to help us set up the actual runnable window.
	}
}

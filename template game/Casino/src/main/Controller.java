package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import baccarat.Baccarat;
import black_jack.BlackJack;
import card_counter.CardCounter;
import handlers.MouseHandler;
import lobby.Account;
import lobby.Menu;
import universal.CardImage;

public class Controller extends JPanel implements Runnable {
	
	public enum STATE{	//ENUM that explains easier what gamestate we are using
		MENU,
		CRAPS,
		BLACKJACK,
		ACCOUNT,
		COUNTER,
		BACCARAT
	}

	private Thread thread;
	private Graphics2D g;
	private BufferedImage image;
	
	private static STATE state = STATE.MENU;// default the gamestate to menu
	
	public static Point mousePoint = new Point(0, 0); //Keep track of where the mouse pointer is.
	public static Font bigFont = new Font("TimesRoman", Font.PLAIN, 25);  //Different fonts if we so choose.
	public static Font bigBold = new Font("TimesRoman", Font.BOLD, 18);  
	public static Font HUGE = new Font("TimesRoman", Font.BOLD, 45);
	public static Font NORMAL = new Font("TimesRoman", Font.PLAIN, 18);
	public static Font SMALL = new Font("TimesRoman", Font.PLAIN, 17);
	
	private boolean running = true; //for thread
	private static final long serialVersionUID = 1L;
	private long lastTime;
	@SuppressWarnings("unused")
	private double fps;
	private String version = "1.0";
	
	private static Menu menu;
	private static Craps craps;
	private static BlackJack bj;
	private static Account account;
	private static CardCounter counter;
	private static Baccarat baccarat;
	
	public static CardImage ci;
	
	public static boolean AUTOMATION = false;
	
	//For FPS
	
	public static int scrollAmt = 0;
	public static int scrollLmt = 0;
		
	public static JFrame frame;
	
	public Controller(JFrame frame) {
		super();
		setPreferredSize(new Dimension(Frame.WIDTH, Frame.HEIGHT));
		setFocusable(true);
		requestFocus(true);
		addNotify();
		this.frame = frame;
		//Stuff to set up the frame still
	}
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		//Create a new thread to run the game
	}
	private void init() {
		image = new BufferedImage(Frame.WIDTH, Frame.HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics(); //Create/Initialize a new image.
//		this.addKeyListener(new KeyHandler());
		this.addMouseListener(new MouseHandler());
		this.addMouseMotionListener(new MouseHandler());
		this.addMouseWheelListener(new MouseHandler());
		//Create Mouse and Key Listeners which will be recording user input.
		menu = new Menu();
		Controller.switchStates(Controller.STATE.MENU);
		ci = new CardImage();
	}

	public void run() { // Our running function. 
		init();
		while (running) {
			lastTime = System.nanoTime();
			display();
			try {
				Thread.sleep(10);
				//Pause the game every 10ms.
			} catch (InterruptedException e) {

			}
			fps = 1000000000.0 / (System.nanoTime() - lastTime); //Frames per second based on how fast machine is.
			lastTime = System.nanoTime();
		}
		
	}

	private void display() { //This is what will make the game run/calculate(tick function) & display graphics(render function)
		switch(state) {
		case MENU:
			menu.tick();
			menu.render(g);
			break;
		case CRAPS:
			craps.tick();
			craps.render(g);
			break;
		case BLACKJACK:
			bj.tick();
			bj.render(g);
			if(bj.buttons[9].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				AUTOMATION = !AUTOMATION;
				MouseHandler.MOUSEDOWN = false;
				System.out.println("WAS IN Status: " + AUTOMATION);
				bj.render(g);
			}
			break;
		case BACCARAT:
			baccarat.tick();
			baccarat.render(g);
			break;
		case ACCOUNT:
			account.tick();
			account.render(g);
			break;
		case COUNTER:
			counter.tick();
			counter.render(g);
			break;
		default:
			break;
	}
		g.setColor(Color.white);
		g.drawString("Version " + version, Frame.WIDTH/2-g.getFontMetrics().stringWidth("Version: " + version)/2, Frame.HEIGHT-25);
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		//Draw the graphics that are displayed for whatever gamestate is currently active.
	}
	
	public static void switchStates(STATE state) { // Helps us switch gamestates for example to a win screen after we win.
		scrollAmt = 0;
		scrollLmt = 0;
		Controller.state = state;
		if(state == Controller.STATE.CRAPS) {
			craps = new Craps();
		}
		if(state == Controller.STATE.BLACKJACK) {
			bj = new BlackJack();
		}
		if(state == Controller.STATE.ACCOUNT) {
			account = new Account();
		}
		if(state == Controller.STATE.COUNTER) {
			counter = new CardCounter();
		}
		if(state == Controller.STATE.BACCARAT) {
			baccarat = new Baccarat();
		}
		//Once we switch, restart the file from the beginning and initiallize it again.
	}
}

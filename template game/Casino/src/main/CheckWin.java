package main;

public class CheckWin {

	public static enum STATUS {
		WON,
		LOSS,
		NEUTRAL
	}
	public STATUS status;
	public double payout = 0;
	
	public double getPayout() {
		return (payout + 0.0);
	}
	public boolean didWin() {
		return false;
	}
}

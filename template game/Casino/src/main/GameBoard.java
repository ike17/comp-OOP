package main;

import java.awt.Polygon;
import java.util.Arrays;

import main.CheckWin.STATUS;

public class GameBoard extends Polygon{

	private static final long serialVersionUID = 1L;
	public String name = "";
	public String descr = "";
	public Polygon bounds;
	public double payout = 0;
	private CheckWin results;
	public Integer[] winnings = new Integer[11];
		
	public GameBoard(String name, int[] x, int[] y) {
		this.name = name;
		this.bounds = new Polygon(x, y, x.length);
		setWinningNumbers();
		results = new CheckWin();
	}
	public CheckWin didWin(int[] diceRoll, boolean markerOn) {
		int input = diceRoll[0] + diceRoll[1];
		results.payout = -1;
		if(name == "Field") {
			if(input == 3 || input == 4 || input == 9 || input == 10 || input == 11) {
					results.payout = (double)1.0;
					results.status = STATUS.WON;
			}else if(input == 2 || input == 12) {
				results.payout = (double)2.0;
				results.status = STATUS.WON;
			}else {
				results.status = STATUS.LOSS;
			}
		}
		if(name == "Pass Line") {
			if(Craps.markerOn) {
				if(input != 7) {
					if(input == Craps.marker.value) {
						results.payout = (double)1.0;
						results.status = STATUS.WON;
					}
				}else {
					results.status = STATUS.LOSS;
				}
			}else {
				if(input == 7 || input == 11) {
					results.payout = (double)1.0;
					results.status = STATUS.WON;
				}else if(input == 2 || input == 3 || input == 12) {
					results.status = STATUS.LOSS;
				}else {
					//chip is now in play
					results.status = STATUS.NEUTRAL;
				}
			}
		}
		if(name == "Don't Pass Line") {
			if(Craps.markerOn) {
				if(input == 7) {
					results.payout = (double)1.0;
					results.status = STATUS.WON;
				}else if(input == Craps.marker.value){
					results.status = STATUS.LOSS;
				}else {
					results.status = STATUS.NEUTRAL;
				}
			}else {
				if(input == 7 || input == 11) {
					results.status = STATUS.LOSS;
				}else if(input == 2 || input == 3) {
					results.payout = (double)1.0;
					results.status = STATUS.WON;
				}else {
					//chip is now in play
					results.status = STATUS.NEUTRAL;
				}
			}
		}
		if(name == "Big 6/8") {
			if(input == 6 || input == 8) {
				results.payout = (double)1.0;
				results.status = STATUS.WON;
			}else if(input == 7) {
				results.status = STATUS.LOSS;
			}else {
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "4") {
			if(Craps.markerOn) {
				if(input == 4) {
					results.payout = ((double)9/5);//bet 100 -> win 180
					results.status = STATUS.WON;
				}else if(input == 7){
					results.status = STATUS.LOSS;
				}else {
					results.status = STATUS.NEUTRAL;
				}
			}else {
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "5") {
			if(Craps.markerOn) {
				if(input == 5) {
					results.payout = ((double)7/5);
					results.status = STATUS.WON;
				}else if(input == 7){
					results.status = STATUS.LOSS;
				}else {
					results.status = STATUS.NEUTRAL;
				}
			}else {
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "6") {
			if(Craps.markerOn) {
				if(input == 6) {
					results.payout = ((double)7/6);
					results.status = STATUS.WON;
				}else if(input == 7){
					results.status = STATUS.LOSS;
				}else {
					results.status = STATUS.NEUTRAL;
				}
			}else {
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "8") {
			if(Craps.markerOn) {
				if(input == 8) {
					results.payout = ((double)7/6);
					results.status = STATUS.WON;
				}else if(input == 7){
					results.status = STATUS.LOSS;
				}else {
					results.status = STATUS.NEUTRAL;
				}
			}else {
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "9") {
			if(Craps.markerOn) {
				if(input == 9) {
					results.payout = ((double)7/5);
					results.status = STATUS.WON;
				}else if(input == 7){
					results.status = STATUS.LOSS;
				}else {
					results.status = STATUS.NEUTRAL;
				}
			}else {
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "10") {
			if(Craps.markerOn) {
				if(input == 10) {
					results.payout = ((double)9/5);
					results.status = STATUS.WON;
				}else if(input == 7){
					results.status = STATUS.LOSS;
				}else {
					results.status = STATUS.NEUTRAL;
				}
			}else {
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "3 Hard Way") {
			if(diceRoll[0] == 3 && diceRoll[1] == 3) {
				results.payout = ((double)10.0);
				results.status = STATUS.WON;
			}else if(input == 7 || (diceRoll[0] == 3 && diceRoll[1] != 3) || (diceRoll[1] == 3 && diceRoll[0] != 3)){
				results.status = STATUS.LOSS;
			}else {
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "5 Hard Way") {
			if(diceRoll[0] == 5 && diceRoll[1] == 5) {
				results.payout = ((double)8.0);
				results.status = STATUS.WON;
			}else if(input == 7 || (diceRoll[0] == 5 && diceRoll[1] != 5) || (diceRoll[1] == 5 && diceRoll[0] != 5)){
				results.status = STATUS.LOSS;
			}else {
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "4 Hard Way") {
			if(diceRoll[0] == 4 && diceRoll[1] == 4) {
				results.payout = ((double)10.0);
				results.status = STATUS.WON;
			}else if(input == 7 || (diceRoll[0] == 4 && diceRoll[1] != 4) || (diceRoll[1] == 4 && diceRoll[0] != 4)){
				results.status = STATUS.LOSS;
			}else {
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "2 Hard Way") {
			if(diceRoll[0] == 2 && diceRoll[1] == 2) {
				results.payout = ((double)8.0);
				results.status = STATUS.WON;
			}else if(input == 7 || (diceRoll[0] == 2 && diceRoll[1] != 2) || (diceRoll[1] == 2 && diceRoll[0] != 2)){
				results.status = STATUS.LOSS;
			}else {
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "1to2 Hard Way") {
			if(input == 3) {
				results.payout = ((double)16.0);
				results.status = STATUS.WON;
			}else if(input == 7 || 
					((diceRoll[0] == 1 && diceRoll[1] != 2) || (diceRoll[1] == 2 && diceRoll[0] != 1)) ||
					((diceRoll[0] == 2 && diceRoll[1] != 1) || (diceRoll[1] == 1 && diceRoll[0] != 2))){
				results.status = STATUS.LOSS;
			}else {
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "1 Hard Way") {
			if(input == 2) {
				results.payout = ((double)31.0);
				results.status = STATUS.WON;
			}else if(input == 7 || (diceRoll[0] == 1 && diceRoll[1] != 1) || (diceRoll[1] == 1 && diceRoll[0] != 1)){
				results.status = STATUS.LOSS;
			}else {
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "6 Hard Way") {
			if(input == 12) {
				results.payout = ((double)31.0);
				results.status = STATUS.WON;
			}else if(input == 7 || (diceRoll[0] == 6 && diceRoll[1] != 6) || (diceRoll[1] == 6 && diceRoll[0] != 6)){
				results.status = STATUS.LOSS;
			}else {
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "6to5 Hard Way") {
			if(input == 12) {
				results.payout = ((double)16.0);
				results.status = STATUS.WON;
			}else if(input == 7 || 
					((diceRoll[0] == 6 && diceRoll[1] != 5) || (diceRoll[1] == 6 && diceRoll[0] != 5)) ||
					((diceRoll[0] == 5 && diceRoll[1] != 6) || (diceRoll[1] == 5 && diceRoll[0] != 6))){
				results.status = STATUS.LOSS;
			}else {
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "C Craps") {
			if(input == 2 || input == 3 || input == 12) {
				results.payout = ((double)7.0);
				results.status = STATUS.WON;
			}else{
				results.status = STATUS.LOSS;
			}
		}
		if(name == "Any Seven") {
			if(input == 7) {
				results.payout = ((double)4.0);
				results.status = STATUS.WON;
			}else{
				results.status = STATUS.LOSS;
			}
		}
		if(name == "Any Crap") {
			if(input == 2 || input == 3 || input == 12) {
				results.payout = ((double)7.0);
				results.status = STATUS.WON;
			}else{
				results.status = STATUS.LOSS;
			}
		}
		if(name == "E Crap") {
			if(input == 11) {
				results.payout = ((double)15.0);
				results.status = STATUS.WON;
			}else{
				results.status = STATUS.LOSS;
			}
		}
		if(name == "Horn Bet") {
			if(input == 2 || input == 12) {
				results.payout = ((double)30.0);
				results.status = STATUS.WON;
			}else if(input == 3 || input == 11) {
				results.payout = ((double)15.0);
				results.status = STATUS.WON;
			}else{
				results.status = STATUS.LOSS;
			}
		}
		if(name == "Come Box") {
			if(input == 7 || input == 11) {
				results.payout = ((double)1.0);
				results.status = STATUS.WON;
			}else if(input == 2 || input == 3 || input == 12) {
				results.status = STATUS.LOSS;
			}else{
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "Don't Come Box") {
			if(input == 2 || input == 3) {
				results.payout = ((double)1.0);
				results.status = STATUS.WON;
			}else if(input == 7 || input == 11) {
				results.status = STATUS.LOSS;
			}else{
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "Bet Against 4") {
			if(input == 4) {
				results.status = STATUS.LOSS;
			}else if(input == 7) {
				results.payout = ((double)1.0);
				results.status = STATUS.WON;
			}else{
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "Bet Against 5") {
			if(input == 5) {
				results.status = STATUS.LOSS;
			}else if(input == 7) {
				results.payout = ((double)1.0);
				results.status = STATUS.WON;
			}else{
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "Bet Against 6") {
			if(input == 6) {
				results.status = STATUS.LOSS;
			}else if(input == 7) {
				results.payout = ((double)1.0);
				results.status = STATUS.WON;
			}else{
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "Bet Against 8") {
			if(input == 8) {
				results.status = STATUS.LOSS;
			}else if(input == 7) {
				results.payout = ((double)1.0);
				results.status = STATUS.WON;
			}else{
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "Bet Against 9") {
			if(input == 9) {
				results.status = STATUS.LOSS;
			}else if(input == 7) {
				results.payout = ((double)1.0);
				results.status = STATUS.WON;
			}else{
				results.status = STATUS.NEUTRAL;
			}
		}
		if(name == "Bet Against 10") {
			if(input == 10) {
				results.status = STATUS.LOSS;
			}else if(input == 7) {
				results.payout = ((double)1.0);
				results.status = STATUS.WON;
			}else{
				results.status = STATUS.NEUTRAL;
			}
		}
		return results;
	}
	public void setWinningNumbers() {
		if(name == "Field") {
			descr = "“Field bets” are a one roll bet that one of seven numbers\n"
					+ "come in (2, 3, 4, 9, 10, 11, 12). Bets are paid 1 to 1 unless a 2\n"
					+ "or 12 are rolled. These bets are paid 2 to 1. If any number\n"
					+ "other than these comes up, the bet is lost.\n";
			descr += "Winning Numbers: " + Arrays.toString(winnings);
			winnings = new Integer[] {2, 3, 4, 9, 10, 11, 12};
		}
		if(name == "Pass Line") {
			descr = "This is an even money bet. Players are betting with the\n"
					+ "shooter. On the “COME OUT ROLL” (the first roll of dice\n"
					+ "with respect to a pass and don’t pass bet) the player will\n"
					+ "win if the toss is a 7 or 11 and lose if the toss is 2, 3 or\n"
					+ "12. Any other number is the point, and in order for the\n"
					+ "pass line to win, the shooter must throw the point again\n"
					+ "(same number) before throwing a 7. If a player bets on\n"
					+ "the pass, they cannot remove or reduce their bet after\n"
					+ "a point has been established.";
			if(Craps.markerOn) {
				winnings = new Integer[] {(int)Craps.marker.value};
			}else {
				winnings = new Integer[] {7, 11};
			}
		}
		if(name == "Don't Pass Line") {
			descr = "This is the opposite of a pass line bet. The don’t pass line\n"
					+ "will lose on the come out roll if a 7 or 11 is thrown; and\n"
					+ "win if a 2 or 3 is rolled. A come out roll of 12 is a push\n"
					+ "on the don’t pass line. After the point is established, the\n"
					+ "don’t pass line bet loses if the point is made, and wins\n"
					+ "if a 7 is thrown prior to the shooter making their point.\n"
					+ "Don’t pass bets can be reduced or removed at any time\n"
					+ "the player chooses, but cannot be increased until a new\n"
					+ "come out roll.";
			if(Craps.markerOn) {
				winnings = new Integer[] {7};
			}else {
				winnings = new Integer[] {2, 3};
			}
		}
		if(name == "Big 6/8") {
			descr = "The user will win if there is a 6 or an 8 rolled and will\n"
					+ "loose if there is a 7 rolled.\n"
					+ "Winning Numbers: " + Arrays.toString(winnings);
			winnings = new Integer[] {6, 8};
		}
		if(name == "4") {
			descr = "“PLACE BETS” may be made any time, but do not win\n"
					+ "or lose on the come out roll. Players can bet individual\n"
					+ "numbers 4, 5, 6, 8, 9 or 10. If the place bet is rolled before\n"
					+ "a 7, the player wins. If the 7 is rolled before the place\n"
					+ "bet, the player loses. The odds on the place bet vary: 4\n"
					+ "& 10 pay 9 to 5, 5 & 9 pay 7 to 5, and 6 & 8 pay 7 to 6.\n"
					+ "Players can remove, increase or reduce the bet any time\n"
					+ "after the dice have been rolled.\n"
					+ "Winning Numbers: " + Arrays.toString(winnings);
			winnings = new Integer[] {4};
		}
		if(name == "5") {
			descr = "“PLACE BETS” may be made any time, but do not win\n"
					+ "or lose on the come out roll. Players can bet individual\n"
					+ "numbers 4, 5, 6, 8, 9 or 10. If the place bet is rolled before\n"
					+ "a 7, the player wins. If the 7 is rolled before the place\n"
					+ "bet, the player loses. The odds on the place bet vary: 4\n"
					+ "& 10 pay 9 to 5, 5 & 9 pay 7 to 5, and 6 & 8 pay 7 to 6.\n"
					+ "Players can remove, increase or reduce the bet any time\n"
					+ "after the dice have been rolled.\n"
					+ "Winning Numbers: " + Arrays.toString(winnings);
			winnings = new Integer[] {5};
		}
		if(name == "6") {
			descr = "“PLACE BETS” may be made any time, but do not win\n"
					+ "or lose on the come out roll. Players can bet individual\n"
					+ "numbers 4, 5, 6, 8, 9 or 10. If the place bet is rolled before\n"
					+ "a 7, the player wins. If the 7 is rolled before the place\n"
					+ "bet, the player loses. The odds on the place bet vary: 4\n"
					+ "& 10 pay 9 to 5, 5 & 9 pay 7 to 5, and 6 & 8 pay 7 to 6.\n"
					+ "Players can remove, increase or reduce the bet any time\n"
					+ "after the dice have been rolled.\n"
					+ "Winning Numbers: " + Arrays.toString(winnings);
			winnings = new Integer[] {6};
		}
		if(name == "8") {
			descr = "“PLACE BETS” may be made any time, but do not win\n"
					+ "or lose on the come out roll. Players can bet individual\n"
					+ "numbers 4, 5, 6, 8, 9 or 10. If the place bet is rolled before\n"
					+ "a 7, the player wins. If the 7 is rolled before the place\n"
					+ "bet, the player loses. The odds on the place bet vary: 4\n"
					+ "& 10 pay 9 to 5, 5 & 9 pay 7 to 5, and 6 & 8 pay 7 to 6.\n"
					+ "Players can remove, increase or reduce the bet any time\n"
					+ "after the dice have been rolled.\n"
					+ "Winning Numbers: " + Arrays.toString(winnings);
			winnings = new Integer[] {8};
		}
		if(name == "9") {
			descr = "“PLACE BETS” may be made any time, but do not win\n"
					+ "or lose on the come out roll. Players can bet individual\n"
					+ "numbers 4, 5, 6, 8, 9 or 10. If the place bet is rolled before\n"
					+ "a 7, the player wins. If the 7 is rolled before the place\n"
					+ "bet, the player loses. The odds on the place bet vary: 4\n"
					+ "& 10 pay 9 to 5, 5 & 9 pay 7 to 5, and 6 & 8 pay 7 to 6.\n"
					+ "Players can remove, increase or reduce the bet any time\n"
					+ "after the dice have been rolled.\n"
					+ "Winning Numbers: " + Arrays.toString(winnings);
			winnings = new Integer[] {9};
		}
		if(name == "10") {
			descr = "“PLACE BETS” may be made any time, but do not win\n"
					+ "or lose on the come out roll. Players can bet individual\n"
					+ "numbers 4, 5, 6, 8, 9 or 10. If the place bet is rolled before\n"
					+ "a 7, the player wins. If the 7 is rolled before the place\n"
					+ "bet, the player loses. The odds on the place bet vary: 4\n"
					+ "& 10 pay 9 to 5, 5 & 9 pay 7 to 5, and 6 & 8 pay 7 to 6.\n"
					+ "Players can remove, increase or reduce the bet any time\n"
					+ "after the dice have been rolled.\n"
					+ "Winning Numbers: " + Arrays.toString(winnings);
			winnings = new Integer[] {10};
		}
		if(name == "3 Hard Way") {
			descr = "This bet applies to the 4, 6, 8 and 10. To win a hardway\n"
					+ "bet, the 4, 6, 8 or 10 must be rolled in pairs. The four\n"
					+ "wins if two 2’s are rolled, the 6 wins if two 3’s are rolled,\n"
					+ "the 8 wins if two 4’s are rolled, and the 10 wins if two 5’s\n"
					+ "are rolled. If one of these numbers comes in easy, the\n"
					+ "bet loses. The bets also lose if a 7 is rolled. The hard 6\n"
					+ "& 8 pay 9 to 1 and the hard 4 & 10 pay 7 to 1. Hardway\n"
					+ "bets can be placed at any time\n"
					+ "Winning Numbers: Roll a 3 on both dice";
			winnings = new Integer[] {6};
		}
		if(name == "5 Hard Way") {
			descr = "This bet applies to the 4, 6, 8 and 10. To win a hardway\n"
					+ "bet, the 4, 6, 8 or 10 must be rolled in pairs. The four\n"
					+ "wins if two 2’s are rolled, the 6 wins if two 3’s are rolled,\n"
					+ "the 8 wins if two 4’s are rolled, and the 10 wins if two 5’s\n"
					+ "are rolled. If one of these numbers comes in easy, the\n"
					+ "bet loses. The bets also lose if a 7 is rolled. The hard 6\n"
					+ "& 8 pay 9 to 1 and the hard 4 & 10 pay 7 to 1. Hardway\n"
					+ "bets can be placed at any time\n"
					+ "Winning Numbers: Roll a 5 on both dice";
			winnings = new Integer[] {10};
		}
		if(name == "4 Hard Way") {
			descr = "This bet applies to the 4, 6, 8 and 10. To win a hardway\n"
					+ "bet, the 4, 6, 8 or 10 must be rolled in pairs. The four\n"
					+ "wins if two 2’s are rolled, the 6 wins if two 3’s are rolled,\n"
					+ "the 8 wins if two 4’s are rolled, and the 10 wins if two 5’s\n"
					+ "are rolled. If one of these numbers comes in easy, the\n"
					+ "bet loses. The bets also lose if a 7 is rolled. The hard 6\n"
					+ "& 8 pay 9 to 1 and the hard 4 & 10 pay 7 to 1. Hardway\n"
					+ "bets can be placed at any time\n"
					+ "Winning Numbers: Roll a 4 on both dice";
			winnings = new Integer[] {8};
		}
		if(name == "2 Hard Way") {
			descr = "This bet applies to the 4, 6, 8 and 10. To win a hardway\n"
					+ "bet, the 4, 6, 8 or 10 must be rolled in pairs. The four\n"
					+ "wins if two 2’s are rolled, the 6 wins if two 3’s are rolled,\n"
					+ "the 8 wins if two 4’s are rolled, and the 10 wins if two 5’s\n"
					+ "are rolled. If one of these numbers comes in easy, the\n"
					+ "bet loses. The bets also lose if a 7 is rolled. The hard 6\n"
					+ "& 8 pay 9 to 1 and the hard 4 & 10 pay 7 to 1. Hardway\n"
					+ "bets can be placed at any time\n"
					+ "Winning Numbers: Roll a 2 on both dice";
			winnings = new Integer[] {4};
		}
		if(name == "1to2 Hard Way") {
			descr = "This bet applies to the 4, 6, 8 and 10. To win a hardway\n"
					+ "bet, the 4, 6, 8 or 10 must be rolled in pairs. The four\n"
					+ "wins if two 2’s are rolled, the 6 wins if two 3’s are rolled,\n"
					+ "the 8 wins if two 4’s are rolled, and the 10 wins if two 5’s\n"
					+ "are rolled. If one of these numbers comes in easy, the\n"
					+ "bet loses. The bets also lose if a 7 is rolled. The hard 6\n"
					+ "& 8 pay 9 to 1 and the hard 4 & 10 pay 7 to 1. Hardway\n"
					+ "bets can be placed at any time\n"
					+ "Winning Numbers: Roll a 1 and a 2 on the dice";
			winnings = new Integer[] {3};
		}
		if(name == "1 Hard Way") {
			descr = "This bet applies to the 4, 6, 8 and 10. To win a hardway\n"
					+ "bet, the 4, 6, 8 or 10 must be rolled in pairs. The four\n"
					+ "wins if two 2’s are rolled, the 6 wins if two 3’s are rolled,\n"
					+ "the 8 wins if two 4’s are rolled, and the 10 wins if two 5’s\n"
					+ "are rolled. If one of these numbers comes in easy, the\n"
					+ "bet loses. The bets also lose if a 7 is rolled. The hard 6\n"
					+ "& 8 pay 9 to 1 and the hard 4 & 10 pay 7 to 1. Hardway\n"
					+ "bets can be placed at any time\n"
					+ "Winning Numbers: Roll a 1 on both dice";
			winnings = new Integer[] {2};
		}
		if(name == "6 Hard Way") {
			descr = "This bet applies to the 4, 6, 8 and 10. To win a hardway\n"
					+ "bet, the 4, 6, 8 or 10 must be rolled in pairs. The four\n"
					+ "wins if two 2’s are rolled, the 6 wins if two 3’s are rolled,\n"
					+ "the 8 wins if two 4’s are rolled, and the 10 wins if two 5’s\n"
					+ "are rolled. If one of these numbers comes in easy, the\n"
					+ "bet loses. The bets also lose if a 7 is rolled. The hard 6\n"
					+ "& 8 pay 9 to 1 and the hard 4 & 10 pay 7 to 1. Hardway\n"
					+ "bets can be placed at any time\n"
					+ "Winning Numbers: Roll a 6 on both dice";
			winnings = new Integer[] {12};
		}
		if(name == "6to5 Hard Way") {
			descr = "This bet applies to the 4, 6, 8 and 10. To win a hardway\n"
					+ "bet, the 4, 6, 8 or 10 must be rolled in pairs. The four\n"
					+ "wins if two 2’s are rolled, the 6 wins if two 3’s are rolled,\n"
					+ "the 8 wins if two 4’s are rolled, and the 10 wins if two 5’s\n"
					+ "are rolled. If one of these numbers comes in easy, the\n"
					+ "bet loses. The bets also lose if a 7 is rolled. The hard 6\n"
					+ "& 8 pay 9 to 1 and the hard 4 & 10 pay 7 to 1. Hardway\n"
					+ "bets can be placed at any time\n"
					+ "Winning Numbers: Roll a 6 and a 5 on the dice";
			winnings = new Integer[] {11};
		}
		if(name == "C Craps") {
			descr = "If a one roll bet of 2, 3 or 12 is rolled, the bet pays 7 to 1.\n"
					+ "If any other number is rolled, the bet loses.\n"
					+ "Winning Numbers: " + Arrays.toString(winnings);
			winnings = new Integer[] {2, 3, 12};
		}
		if(name == "E Crap") {
			descr = "If a one roll bet of 11 is rolled, the bet pays 15 to 1.\n"
					+ "If any other number is rolled, the bet loses.\n"
					+ "Winning Numbers: " + Arrays.toString(winnings);
			winnings = new Integer[] {11};
		}
		if(name == "Any Seven") {
			descr = "A one roll bet that pays 4 to 1. If any number other than\n"
					+ "7 is rolled, the bet loses.\n"
					+ "Winning Numbers: " + Arrays.toString(winnings);
			winnings = new Integer[] {7};
		}
		if(name == "Any Crap") {
			descr = "If a one roll bet of 2, 3 or 12 is rolled, the bet pays 7 to 1.\n"
					+ "If any other number is rolled, the bet loses.\n"
					+ "Winning Numbers: " + Arrays.toString(winnings);
			winnings = new Integer[] {2, 3, 12};
		}
		if(name == "Horn Bet") {
			descr = "A one roll combination of 2, 3, 11 and 12. Bets are placed\n"
					+ "and paid as if each number were four separate bets.\n"
					+ "Payoffs are made according to each individual number,\n"
					+ "with ¾ of the bet being subtracted from the payoff\n"
					+ "for the numbers that didn’t come in. The bet is lost if\n"
					+ "anything other than 2, 3, 11 or 12 is rolled.\n"
					+ "Winning Numbers: " + Arrays.toString(winnings);
			winnings = new Integer[] {2, 3, 11, 12};
		}
		if(name == "Come Box") {
			descr = "Players can place a “COME BET” at any time after the\n"
					+ "come out roll. Come bets are similar to creating a new\n"
					+ "game within the game. If the roll after the come out is\n"
					+ "a 7 or 11 the bet wins; and if the roll is 2, 3 or 12 the bet\n"
					+ "loses. Any other number will become a personal come\n"
					+ "point and win 1 to 1 if that number rolls before a 7. If the\n"
					+ "7 is thrown before the come point, the bet loses. A come\n"
					+ "bet may not be removed or reduced after the point is\n"
					+ "thrown, however players can place as many come bets\n"
					+ "as they wish.\n"
					+ "Winning Numbers: " + Arrays.toString(winnings);
			winnings = new Integer[] {7, 11};
		}
		if(name == "Don't Come Box") {
			descr = "The “DON’T COME BET” is reverse of the come bet.\n"
					+ "Players lose on a 7 or 11, and win if the roll is 2 or 3. If the\n"
					+ "12 rolls, the don’t come bet is a push. Any other number\n"
					+ "becomes the don’t come point. This bet will win if a 7 is\n"
					+ "rolled before the don’t come point, and lose if the point\n"
					+ "is rolled before a 7. A don’t come bet may be removed or\n"
					+ "reduced at any time, but cannot be increased or replaced.\n"
					+ "Winning Numbers: " + Arrays.toString(winnings);
			if(Craps.markerOn) {
				winnings = new Integer[] {7};
			}else {
				winnings = new Integer[] {2, 3};
			}
		}
		if(name == "Bet Against 4" || name == "Bet Against 5" || name == "Bet Against 6" ||
				name == "Bet Against 8" || name == "Bet Against 9" || name == "Bet Against 10") {
			descr = "Betting against getting this number. Win if a 7 appears first\n"
					+ "Winning Numbers: " + Arrays.toString(winnings);
			winnings = new Integer[] {7};
		}
		
		// END OF CRAPS -------------------------------------------
		// Baccarat -------------------------------------------
		if(name == "Tie Bet") {
			descr = "If you bet on a tie and win, you will receive a payout of 8:1.\n"
					+ "The house, however, has a 14.36% edge over the player.";
		}
		if(name == "Banker Bet") {
			descr = "If you bet on the Banker’s hand wining and win, you will receive\n"
					+ "a payout of 1:1. You will, however, have to pay the\n"
					+ "house a 5% commission on your winnings from the Banker’s hands.\n"
					+ "The house has a 1.06% edge over the player.";
		}
		if(name == "Player Bet") {
			descr = "If you bet on your hand winning and win, you will receive a\n"
					+ "payout of 1:1. The house has a 1.24% edge over the player.";
		}
		// END OF Baccarat -------------------------------------------
	}
}

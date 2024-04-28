package black_jack;

import universal.Cards;

public class Automation {

	public int dealerCardNumber = 0;
	public Hand playerHand; // 3 hands
	
	/*
	 * Hit = 0
	 * Stand = 1
	 * Split = 2
	 * Double Down = 3
	 * Double Down/Stand if cant = 4
	 * Surrender/Hit if can't surrender = 5
	 * Split/Double Down if cant split = 6
	*/
	public static int[][] pairStrategy = {
			{1, 2, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0},
			{1, 3, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0},
			{1, 4, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0},
			{1, 5, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0},
			{1, 6, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0},
			{1, 7, 0, 1, 4, 4, 4, 4, 1, 1, 0, 0},
			{1, 8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{2, 2, 0, 5, 5, 2, 2, 2, 2, 0, 0, 0},
			{3, 3, 0, 5, 5, 2, 2, 2, 2, 0, 0, 0},
			{4, 4, 0, 0, 0, 0, 5, 5, 0, 0, 0, 0},
			{5, 5, 0, 3, 3, 3, 3, 3, 3, 3, 3, 0},
			{6, 6, 0, 5, 2, 2, 2, 2, 0, 0, 0, 0},
			{7, 7, 0, 2, 2, 2, 2, 2, 2, 0, 0, 0},
			{8, 8, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
			{9, 9, 1, 2, 2, 2, 2, 2, 1, 2, 2, 1},
			{10, 10, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
	};
	//ace == position 0
	public static int[][] totalStrategy = {
			{3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{9, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0},
			{10, 0, 3, 3, 3, 3, 3, 3, 3, 3, 0},
			{11, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3},
			{12, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
			{13, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0},
			{14, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0},
			{15, 0, 1, 1, 1, 1, 1, 0, 0, 0, 6},
			{16, 6, 1, 1, 1, 1, 1, 0, 0, 6, 6},
			{17, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{18, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{19, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{21, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
	};
	
	public Automation(Hand playerHand) {
		this.playerHand = playerHand;
	}
	
	public static int findMove(Cards[] hand, int dealerNumber) {
		boolean found = false;
		int move = -1;
		if(hand[2] == null) {
			//Run first move
			for(int i = 0; i < pairStrategy.length; i++) {
				if(hand[0].number == pairStrategy[i][0] && hand[1].number == pairStrategy[i][1]) {
					found = true;
					move = pairStrategy[i][dealerNumber+1];
					break;
				}
				if(hand[1].number == pairStrategy[i][0] && hand[0].number == pairStrategy[i][1]) {
					found = true;
					move = pairStrategy[i][dealerNumber+1];
					break;
				}
			}
		}
		if(hand[2] != null || !found){
			// run total strategy
			int cardTotal = 0;
			for(int i = 0; i < hand.length; i++) {
				if(hand[i] != null) {
					cardTotal += hand[i].number;
				}
			}
			for(int i = 0; i < totalStrategy.length; i++) {
				if(cardTotal == totalStrategy[i][0]) {
					move = totalStrategy[i][dealerNumber];
				}
			}
		}
		if(move == -1) {
			System.out.println("Error - Couldn't Find Move");
		}
		return move;
	}
}

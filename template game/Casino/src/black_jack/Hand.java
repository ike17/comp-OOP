package black_jack;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.TimeUnit;

import main.Controller;
import main.Frame;
import universal.Cards;
import universal.Cards.cardtype;
import universal.Deck;

public class Hand {
	
	boolean busted = false;
	boolean isDealer = false;
	boolean playerWins = true;
	boolean push = false;
	boolean playersTurn = true;
	boolean surrendered = false;
	boolean insurance = false;
	
	public int moneyBet = 0;
	public int handTotal = 0;
	public int insuranceBet = 0;
	public Cards[] hand;
	
	public Hand(Deck deck, boolean isDealer) {
		this.isDealer = isDealer;
		hand = new Cards[10];
		addCard(deck);
	}
	public Hand(boolean isDealer, int maxCards) {
		this.isDealer = isDealer;
		hand = new Cards[maxCards];
	}
	public void addCard(Deck deck) {
		for (int i = 0; i < hand.length; i++) {
			if (hand[i] == null) {
				hand[i] = deck.pullCard();
				break;
			}
		}
		setCardTotal();
	}
	public void runDealerHand(Deck deck) {
		try {
			Thread.sleep(1500);
			addCard(deck);
			setCardTotal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
}
	public void render(Graphics g, boolean isCurrent, int index) {
		if(!isDealer) {
			for(int i = 0; i < hand.length; i++) {
				if (hand[i] != null) {
					g.drawImage(hand[i].cardImage,
							(Frame.WIDTH / 2) - (hand[i].width / 2 - (i * 25)), 460-(index*140),
							(int) (hand[i].width * .85), (int) (hand[i].height * .85), null);
				}	
			}
			if (hand[0] != null) {
				if (isCurrent) {
					g.setColor(new Color(0, 0, 0, 175));
				} else {
					g.setColor(new Color(0, 0, 0, 100));
				}
				g.fillRoundRect(Frame.WIDTH / 2 - 275, 460 - (index * 140) + 25, 200, 100, 15, 15);
				g.setColor(Color.WHITE);
				g.setFont(Controller.bigBold);
				if (isCurrent) {
					g.drawString("Current Hand -->",
							Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Current Hand -->") / 2 - 175,
							520 - (index * 140));
				}
				g.drawString("Hand: " + handTotal,
						Frame.WIDTH / 2 - g.getFontMetrics().stringWidth(handTotal+"") / 2 - 200,
						460 - (index * 140) + 85);
				g.drawString("Bet: $" + (moneyBet+insuranceBet),
						Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Bet: $" + (moneyBet+insuranceBet)) / 2 - 175,
						460 - (index * 140) + 110);
				g.setFont(Controller.NORMAL);
			}
		}else {
			for(int i = 0; i < hand.length; i++) {
				if (hand[i] != null) {
					g.drawImage(hand[i].cardImage,
							(Frame.WIDTH / 2) - (hand[i].width / 2 - (i * 25)), 25,
							(int) (hand[i].width * .85), (int) (hand[i].height * .85), null);
				}	
			}
			g.setColor(new Color(0, 0, 0, 175));
			g.fillRoundRect(Frame.WIDTH / 2 - 275, 50, 200, 50, 15, 15);
			g.setColor(Color.WHITE);
			g.setFont(Controller.bigBold);
			g.drawString("Hand: " + handTotal,
					Frame.WIDTH / 2 - g.getFontMetrics().stringWidth(handTotal+"") / 2 - 200,
					80);
		}
	}
	public int setCardTotal() {
		int points = 0;
		int ace = 0;
		int totalPoints = 0;
		for (int i = 0; i < hand.length; i++) {
			if (hand[i] != null) {
				points += hand[i].number;
				if (hand[i].number == 1) {
					ace = 10;
				}
			}
		}
		if (!isDealer) {
			totalPoints = points;
			if (points + ace == 21 || points > 21) {
				totalPoints = points + ace;
			}
			if (points + ace <= 21) {
				totalPoints = points + ace;
			}
		} else {
			if (points + ace <= 21) {
				totalPoints = points + ace;
			} else {
				totalPoints = points;
			}
		}
		handTotal = totalPoints;
		if(totalPoints > 21) {
			busted = true;
		}
		return totalPoints;
	}
	public int returnMoney(int dealerPoints, Cards[] dealerDeck) {
		int money = 0;
		setCardTotal();
		if(!surrendered) {
			if (dealerPoints > 21) {
				if (handTotal <= 21) { //won
					playerWins = true;
					money += moneyBet;
				}
			}
			if (handTotal < 21) {
				if (dealerPoints < handTotal) { //won
					playerWins = true;
					money += moneyBet;
				}
				if (handTotal == dealerPoints) { //push
					push = true;
					playerWins = false;
					money = 0;
				}
				if (dealerPoints > handTotal && dealerPoints <= 21) { //lost
					playerWins = false;
					money -= moneyBet;
				}
			}
			if (handTotal == 21) { //won
				if (dealerPoints != 21) {
					money = moneyBet+(moneyBet/2);
					playerWins = true;
				} else { //push
					push = true;
					playerWins = false;
					money = 0;
				}
			}
			if (handTotal > 21) {
				if (dealerPoints > 21) {// push
					push = true;
					playerWins = false;
					money = 0;
				}else { //lost
					money -= moneyBet;
					playerWins = false;
				}
			}
		}else {
			playerWins = false;
			push = false;
			money -= moneyBet;
		}
		if (insurance && dealerDeck[1].number == 10) {
			// Win your money back
			money = 0;
			playerWins = false;
			push = true;
		}
		if (insurance && dealerDeck[1].number != 10) {
			money -= insuranceBet;
		}
		return money;
	}
}

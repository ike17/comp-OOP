package universal;

import java.util.ArrayList;
import java.util.Collections;

import universal.Cards.cardtype;

public class Deck {


	private ArrayList<Cards> newCards = new ArrayList<Cards>();
	public int cardsLeft = 0;
	public int decks = 5;
	
	public Deck(int deckCount) {
		decks = deckCount;
		refreshDeck();
	}
	public void refreshDeck() {
		newCards.removeAll(newCards);
		for(int j = 0; j < decks; j++) {
			for(int i = 0; i < 52; i++) {
				if(i < 13) {
					newCards.add(new Cards(Cards.cardtype.SPADE, i, i+1));
				}
				if(i>=13 && i < 26) {
					newCards.add(new Cards(Cards.cardtype.HEART, i, i+1-13));
				}
				if(i>=26 && i < 39) {
					newCards.add(new Cards(Cards.cardtype.DIAMOND, i, i+1-26));
				}
				if(i>=39) {
					newCards.add(new Cards(Cards.cardtype.CLUB, i, i+1-39));
				}
			}
		}
		Collections.shuffle(newCards);
		cardsLeft = decks*52;
	}

	public Cards pullCard() {
		Cards temp;
		if(cardsLeft > 0) {
		temp = newCards.get(0);
		cardsLeft--;
		System.out.println("Pulling Card: " + temp.number + " OF " + temp.type);
		newCards.remove(0);
		}else {
			refreshDeck();
			temp = pullCard();
		}
		return temp;
	}
}

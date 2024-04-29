import java.util.Collections;

public class Shoe extends CardCollection {

    // Make sure there are either 6 or 8 Decks when the game begins
    public Shoe(int numDecks) {
        if (numDecks != 6 && numDecks != 8) {
            throw new CardException("Invalid number of decks");
        }
        for (int i = 0; i < numDecks; i++) {
            for (Card.Suit suit : Card.Suit.values()) {
                for (Card.Rank rank : Card.Rank.values()) {
                    cards.add(new BaccaratCard(rank, suit));
                }
            }
        }
    }

    // Shuffle the deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Deal card
    public BaccaratCard deal() {
        if (cards.isEmpty()) {
            throw new CardException("Cannot deal from an empty shoe.");
        }
        return (BaccaratCard) cards.remove(0);
    }
}

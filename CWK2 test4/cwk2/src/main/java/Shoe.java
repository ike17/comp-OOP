import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shoe {
    private final List<BaccaratCard> cards = new ArrayList<>();

    public Shoe(int numDecks) {
        if (numDecks != 6 && numDecks != 8) {
            throw new CardException("Invalid number of decks: " + numDecks);
        }
        for (int i = 0; i < numDecks; i++) {
            for (Card.Suit suit : Card.Suit.values()) {
                for (Card.Rank rank : Card.Rank.values()) {
                    cards.add(new BaccaratCard(rank, suit));
                }
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public BaccaratCard deal() {
        if (cards.isEmpty()) {
            throw new CardException("Cannot deal from an empty shoe.");
        }
        return cards.remove(0);
    }

    public int size() {
        return cards.size();
    }
}

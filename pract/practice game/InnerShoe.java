import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class InnerShoe {
    private LinkedList<BaccaratCard> cards = new LinkedList<>();

    public InnerShoe(int numberOfDecks) throws CardException {
        if (numberOfDecks != 6 && numberOfDecks != 8) {
            throw new CardException("Invalid number of decks. Only 6 or 8 are allowed.");
        }
        for (int i = 0; i < numberOfDecks; i++) {
            for (Card.Suit suit : Card.Suit.values()) {
                for (Card.Rank rank : Card.Rank.values()) {
                    cards.add(new BaccaratCard(rank, suit));
                }
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public BaccaratCard deal() throws CardException {
        if (cards.isEmpty()) {
            throw new CardException("Cannot deal from an empty shoe.");
        }
        return cards.pop();
    }

    public int size() {
        return cards.size();
    }
}

import java.util.Collections;

public class Shoe extends CardCollection {

    /**
     * Constructs a Shoe with a specified number of decks of Baccarat cards.
     *
     * @param numDecks The number of decks to include in the shoe.
     */
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

    /**
     * Shuffles the cards in the shoe.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Deals a card from the shoe.
     *
     * @return The BaccaratCard at the top of the shoe.
     * @throws CardException if the shoe is empty.
     */
    public BaccaratCard deal() {
        if (cards.isEmpty()) {
            throw new CardException("Cannot deal from an empty shoe.");
        }
        return (BaccaratCard) cards.remove(0);
    }
}

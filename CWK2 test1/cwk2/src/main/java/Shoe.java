/**
 * A class representing a shoe of cards for Baccarat consisting of multiple decks.
 */
public class Shoe {
    private final List<BaccaratCard> cards;

    /**
     * Creates a Shoe object containing the specified number of decks.
     *
     * @param numberOfDecks The number of decks to be included in the shoe.
     * @throws CardException if the number of decks is not 6 or 8.
     */
    public Shoe(int numberOfDecks) throws CardException {
        if (numberOfDecks != 6 && numberOfDecks != 8) {
            throw new CardException("Invalid number of decks: " + numberOfDecks);
        }

        cards = new ArrayList<>(numberOfDecks * 52);

        for (int i = 0; i < numberOfDecks; i++) {
            addDeck();
        }
    }

    /**
     * Adds a full deck of 52 cards to the shoe, ordered by suit then by rank.
     */
    private void addDeck() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new BaccaratCard(rank, suit));
            }
        }
    }

    /**
     * Returns the total number of cards in the shoe.
     *
     * @return the number of cards in the shoe
     */
    public int size() {
        return cards.size();
    }

    /**
     * Shuffles the cards in the shoe randomly.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Deals the top card from the shoe.
     *
     * @return the dealt BaccaratCard
     * @throws CardException if the shoe is empty
     */
    public BaccaratCard deal() throws CardException {
        if (cards.isEmpty()) {
            throw new CardException("Cannot deal from an empty shoe");
        }
        return cards.remove(0);
    }
}
public class BaccaratCard extends Card {
    
    /**
     * Constructs a BaccaratCard with a specified rank and suit using the superclass constructor.
     *
     * @param rank The rank of the card.
     * @param suit The suit of the card.
     */
    public BaccaratCard(Rank rank, Suit suit) {
        super(rank, suit);
    }

    /**
     * Returns the value of the card according to Baccarat rules.
     *
     * @return the Baccarat value of the card
     */
    public int value() {
        int rankOrdinal = getRank().ordinal();
        if (rankOrdinal < 9) {
            return rankOrdinal + 1;
        }
        return 0;
    }

    /**
     * Returns a string representation of this BaccaratCard.
     * The string consists of the card's rank and suit.
     * Example: "A♠", "10♥", "K♦".
     *
     * @return A string representation of this card.
     */
    @Override
    public String toString() {
        return String.format("%c%s", getRank().getSymbol(), getSuit().getSymbol());
    }
}

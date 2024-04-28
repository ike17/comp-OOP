/**
 * Represents a playing card in the game of Baccarat.
 */
public class BaccaratCard {
    private final Card.Rank rank;
    private final Card.Suit suit;

    /**
     * Creates a BaccaratCard object with the specified rank and suit.
     *
     * @param rank the rank of the card
     * @param suit the suit of the card
     */
    public BaccaratCard(Card.Rank rank, Card.Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Returns the rank of this card.
     *
     * @return the rank of the card
     */
    public Card.Rank getRank() {
        return rank;
    }

    /**
     * Returns the suit of this card.
     *
     * @return the suit of the card
     */
    public Card.Suit getSuit() {
        return suit;
    }

    /**
     * Returns a string representation of this card in the format "RS", where R is
     * the rank symbol and S is the Unicode symbol for the suit.
     *
     * @return a string representation of this card
     */
    @Override
    public String toString() {
        return rank.getSymbol() + getSuitSymbol(suit);
    }

    /**
     * Returns the Unicode symbol corresponding to the given suit.
     *
     * @param suit the suit for which to return the symbol
     * @return the Unicode symbol for the given suit
     */
    private static char getSuitSymbol(Card.Suit suit) {
        switch (suit) {
            case CLUBS:
                return '\u2663';
            case DIAMONDS:
                return '\u2666';
            case HEARTS:
                return '\u2665';
            case SPADES:
                return '\u2660';
            default:
                throw new IllegalArgumentException("Invalid suit: " + suit);
        }
    }

    /**
     * Compares this card with the specified object for equality. Two cards are
     * considered equal if they have the same rank and suit.
     *
     * @param obj the object to be compared for equality with this card
     * @return true if the specified object is equal to this card
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BaccaratCard other = (BaccaratCard) obj;
        return rank == other.rank && suit == other.suit;
    }

    /**
     * Compares this card with the specified card for order. Cards are ordered
     * first by suit, and then by rank within each suit.
     *
     * @param other the card to be compared
     * @return a negative integer, zero, or a positive integer as this card is
     *         less than, equal to, or greater than the specified card
     */
    public int compareTo(BaccaratCard other) {
        int suitComparison = suit.compareTo(other.suit);
        if (suitComparison != 0) {
            return suitComparison;
        }
        return rank.compareTo(other.rank);
    }

    /**
     * Returns the value of this card in the game of Baccarat.
     *
     * @return the value of this card
     */
    public int value() {
        switch (rank) {
            case ACE:
                return 1;
            case TWO:
            case THREE:
            case FOUR:
            case FIVE:
            case SIX:
            case SEVEN:
            case EIGHT:
            case NINE:
                return rank.ordinal() + 1;
            case TEN:
            case JACK:
            case QUEEN:
            case KING:
                return 0;
            default:
                throw new IllegalStateException("Unknown rank: " + rank);
        }
    }
}

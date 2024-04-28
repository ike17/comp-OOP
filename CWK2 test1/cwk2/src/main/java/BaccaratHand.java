/**
 * A class to represent a hand of playing cards specifically for Baccarat.
 */
public class BaccaratHand {
    private final List<BaccaratCard> cards;

    /**
     * Creates an empty BaccaratHand.
     */
    public BaccaratHand() {
        cards = new ArrayList<>();
    }

    /**
     * Adds a BaccaratCard to the hand.
     *
     * @param card BaccaratCard to be added
     */
    public void add(BaccaratCard card) {
        cards.add(card);
    }

    /**
     * Returns the number of cards in the hand.
     *
     * @return Number of cards
     */
    public int size() {
        return cards.size();
    }

    /**
     * Returns a string representation of the hand.
     *
     * @return String containing two-character representations of each card, separated by a space
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (BaccaratCard card : cards) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(card.toString());
        }
        return sb.toString();
    }

    /**
     * Calculates the points value of the hand in the game of Baccarat.
     *
     * @return the value of the hand
     */
    public int value() {
        int sum = 0;
        for (BaccaratCard card : cards) {
            sum += card.value();
        }
        return sum % 10;
    }

    /**
     * Determines if the hand is a natural in Baccarat.
     *
     * @return true if the hand has exactly two cards and a value of 8 or 9, false otherwise
     */
    public boolean isNatural() {
        return (cards.size() == 2) && (value() == 8 || value() == 9);
    }
}
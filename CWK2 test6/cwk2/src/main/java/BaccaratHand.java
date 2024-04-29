import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a hand of cards used in the game of Baccarat.
 * Extends CardCollection to handle a collection of BaccaratCard specifically.
 */
public class BaccaratHand extends CardCollection {

    /**
     * Adds a BaccaratCard to the hand.
     *
     * @param card The BaccaratCard to add
     */
    public void add(BaccaratCard card) {
        super.add(card);
    }

    /**
     * Calculates the Baccarat value of the hand, mod 10.
     *
     * @return the total Baccarat value of the hand
     */
    @Override
    public int value() {
        return cards.stream()
                    .mapToInt(card -> ((BaccaratCard) card).value())
                    .sum() % 10;
    }

    /**
     * Determines if the hand is a "natural," scoring 8 or 9 with just two cards.
     *
     * @return true if the hand is a natural, false otherwise
     */
    public boolean isNatural() {
        return cards.size() == 2 && (value() == 8 || value() == 9);
    }

    /**
     * Returns a string representation of the hand, formatted as two-character card strings.
     *
     * @return a string representation of the hand
     */
    @Override
    public String toString() {
        return cards.stream()
                    .map(card -> ((BaccaratCard) card).toString())
                    .collect(Collectors.joining(" "));
    }

    /**
     * Provides access to the list of BaccaratCard objects in the hand.
     *
     * @return a list of BaccaratCard objects
     */
    public List<BaccaratCard> getCards() {
        return cards.stream()
                    .map(card -> (BaccaratCard) card)
                    .collect(Collectors.toList());
    }
}

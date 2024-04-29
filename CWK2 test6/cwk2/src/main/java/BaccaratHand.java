import java.util.List;
import java.util.stream.Collectors;


public class BaccaratHand extends CardCollection {

    // Adds a card from BaccaratCard to the Hand
    public void add(BaccaratCard card) {
        super.add(card);
    }

    // Calculates the Baccarat value of the hand
    @Override
    public int value() {
        return cards.stream()
                    .mapToInt(card -> ((BaccaratCard) card).value())
                    .sum() % 10;
    }

    // Determines if the hand is a Natural or not
    public boolean isNatural() {
        return cards.size() == 2 && (value() == 8 || value() == 9);
    }

    
    @Override
    public String toString() {
        return cards.stream()
                    .map(card -> ((BaccaratCard) card).toString())
                    .collect(Collectors.joining(" "));
    }

    
    public List<BaccaratCard> getCards() {
        return cards.stream()
                    .map(card -> (BaccaratCard) card)
                    .collect(Collectors.toList());
    }
}

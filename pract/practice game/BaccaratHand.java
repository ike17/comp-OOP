import java.util.ArrayList;
import java.util.List;

public class BaccaratHand {
    private List<BaccaratCard> hand = new ArrayList<>();

    public void add(BaccaratCard card) {
        hand.add(card);
    }

    public int size() {
        return hand.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        hand.forEach(card -> sb.append(card.toString()).append(" "));
        return sb.toString().trim();
    }

    public int value() {
        return hand.stream().mapToInt(BaccaratCard::value).sum() % 10;
    }

    public boolean isNatural() {
        return (hand.size() == 2 && (value() == 8 || value() == 9));
    }
}

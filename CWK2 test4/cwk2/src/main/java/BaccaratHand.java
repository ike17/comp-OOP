import java.util.ArrayList;
import java.util.List;

public class BaccaratHand {
    private final List<BaccaratCard> cards = new ArrayList<>();

    public void add(BaccaratCard card) {
        cards.add(card);
    }

    public int size() {
        return cards.size();
    }

    public List<BaccaratCard> getCards() {
        return cards;
    }

    public int value() {
        return cards.stream().mapToInt(BaccaratCard::value).sum() % 10;
    }

    public boolean isNatural() {
        return size() == 2 && (value() == 8 || value() == 9);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (BaccaratCard card : cards) {
            sb.append(card.toString()).append(" ");
        }
        return sb.toString().trim();
    }
}

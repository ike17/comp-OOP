import java.util.Objects;

public class BaccaratCard extends Card {
    public BaccaratCard(Card.Rank rank, Card.Suit suit) {
        super(rank, suit);
    }

    @Override
    public String toString() {
        return String.format("%s%s", getRank().getSymbol(), getSuit().getSymbol());
    }

    public int value() {
        switch (getRank()) {
            case ACE:
                return 1;
            case TWO:
                return 2;
            case THREE:
                return 3;
            case FOUR:
                return 4;
            case FIVE:
                return 5;
            case SIX:
                return 6;
            case SEVEN:
                return 7;
            case EIGHT:
                return 8;
            case NINE:
                return 9;
            default:
                return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BaccaratCard card = (BaccaratCard) obj;
        return this.getRank() == card.getRank() && this.getSuit() == card.getSuit();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRank(), getSuit());
    }

    @Override
    public int compareTo(Card other) {
        if (this.getSuit().ordinal() != other.getSuit().ordinal()) {
            return this.getSuit().ordinal() - other.getSuit().ordinal();
        } else {
            return this.getRank().ordinal() - other.getRank().ordinal();
        }
    }
}

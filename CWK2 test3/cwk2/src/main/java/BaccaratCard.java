public class BaccaratCard extends Card {
    public BaccaratCard(Rank rank, Suit suit) {
        super(rank, suit);
    }

    @Override
    public int value() {
        if (getRank().ordinal() < Rank.TEN.ordinal()) {
            return getRank().ordinal() + 1;
        }
        return 0; // TEN, JACK, QUEEN, KING all count as 0 in Baccarat
    }

    @Override
    public String toString() {
        // Assuming we want to use the first letter for non-numeric cards
        String rankStr = getRank().ordinal() < 9 ? String.valueOf(getRank().ordinal() + 1) : getRank().name().substring(0, 1);
        String suitStr = "";
        switch (getSuit()) {
            case CLUBS:
                suitStr = "\\u2663";
                break;
            case DIAMONDS:
                suitStr = "\\u2666";
                break;
            case HEARTS:
                suitStr = "\\u2665";
                break;
            case SPADES:
                suitStr = "\\u2660";
                break;
        }
        return rankStr + suitStr;
    }
}
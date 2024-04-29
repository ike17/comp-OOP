public class BaccaratCard extends Card {
    
    
    public BaccaratCard(Rank rank, Suit suit) {
        super(rank, suit);
    }

    // Returns the value of the card according to Baccarat rules
    public int value() {
        int rankOrdinal = getRank().ordinal();
        if (rankOrdinal < 9) {
            return rankOrdinal + 1;
        }
        return 0;
    }

    // Returns a string consisting of the card's rank and suit
    @Override
    public String toString() {
        return String.format("%c%s", getRank().getSymbol(), getSuit().getSymbol());
    }
}

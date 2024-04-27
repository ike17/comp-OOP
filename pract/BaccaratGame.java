import java.util.Random;

public class BaccaratGame {
    private static final int CARDS_IN_DECK = 52;
    private static final int MAX_CARD_VALUE = 9;
    private static final int[] CARD_VALUES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, 0, 0}; // values for Ace to King
    private static final String[] CARD_SUITS = {"Clubs", "Diamonds", "Hearts", "Spades"};

    private int[] deck;
    private int cardsRemaining;
    private Random random;

    public BaccaratGame() {
        deck = new int[CARDS_IN_DECK];
        cardsRemaining = CARDS_IN_DECK;
        random = new Random();
        resetDeck();
    }

    private void resetDeck() {
        for (int i = 0; i < CARDS_IN_DECK; i++) {
            deck[i] = i;
        }
        shuffleDeck();
    }

    private void shuffleDeck() {
        for (int i = 0; i < CARDS_IN_DECK; i++) {
            int j = random.nextInt(cardsRemaining);
            int temp = deck[i];
            deck[i] = deck[cardsRemaining - 1];
            deck[cardsRemaining - 1] = temp;
            cardsRemaining--;
        }
        cardsRemaining = CARDS_IN_DECK;
    }

    private int getCardValue(int cardIndex) {
        return CARD_VALUES[cardIndex % 13];
    }

    private String getCardSuit(int cardIndex) {
        return CARD_SUITS[cardIndex / 13];
    }

    private int calculateHandValue(int[] hand) {
        int sum = 0;
        for (int card : hand) {
            sum += getCardValue(card);
        }
        return sum % 10 == 0 ? 0 : sum % 10;
    }

    private int dealCard() {
        if (cardsRemaining == 0) {
            resetDeck();
        }
        cardsRemaining--;
        return deck[cardsRemaining];
    }

    private void playGame() {
        cardsRemaining = CARDS_IN_DECK;
        shuffleDeck();
        int[] playerHand = new int[3];
        int[] bankerHand = new int[3];

        playerHand[0] = dealCard();
        bankerHand[0] = dealCard();
        playerHand[1] = dealCard();
        bankerHand[1] = dealCard();

        int playerHandValue = calculateHandValue(playerHand);
        int bankerHandValue = calculateHandValue(bankerHand);

        System.out.println("Player's hand:");
        System.out.println(getCardValue(playerHand[0]) + " of " + getCardSuit(playerHand[0]));
        System.out.println(getCardValue(playerHand[1]) + " of " + getCardSuit(playerHand[1]));

        System.out.println("\nBanker's hand:");
        System.out.println(getCardValue(bankerHand[0]) + " of " + getCardSuit(bankerHand[0]));
        System.out.println(getCardValue(bankerHand[1]) + " of " + getCardSuit(bankerHand[1]));

        if (playerHandValue > 7 || bankerHandValue > 7) {
            System.out.println("No more cards can be drawn.");
        } else {
            if (playerHandValue <= 5) {
                playerHand[2] = dealCard();
                playerHandValue = calculateHandValue(playerHand);
                System.out.println("\nPlayer draws a third card: " + getCardValue(playerHand[2]) + " of " + getCardSuit(playerHand[2]));
            }

            if (bankerHandValue <= 5) {
                bankerHand[2] = dealCard();
                bankerHandValue = calculateHandValue(bankerHand);
                System.out.println("\nBanker draws a third card: " + getCardValue(bankerHand[2]) + " of " + getCardSuit(bankerHand[2]));
            }
        }

        System.out.println("\nPlayer's hand value: " + playerHandValue);
        System.out.println("Banker's hand value: " + bankerHandValue);

        if (playerHandValue > bankerHandValue) {
            System.out.println("Player wins!");
        } else if (playerHandValue < bankerHandValue) {
            System.out.println("Banker wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    public static void main(String[] args) {
        BaccaratGame game = new BaccaratGame();
        game.playGame();
    }
}
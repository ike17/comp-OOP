import java.util.Scanner;

/**
 * Main program to simulate the Baccarat game, including the rules for drawing a third card.
 */
public class Baccarat {
    public static void main(String[] args) {
        Shoe shoe = new Shoe(6);
        shoe.shuffle();

        BaccaratHand playerHand = new BaccaratHand();
        BaccaratHand bankerHand = new BaccaratHand();

        // Initial dealing for player and banker
        playerHand.add(shoe.deal());
        bankerHand.add(shoe.deal());
        playerHand.add(shoe.deal());
        bankerHand.add(shoe.deal());

        System.out.println("Initial Hands:");
        System.out.println("Player Hand: " + playerHand + " - Value: " + playerHand.value());
        System.out.println("Banker Hand: " + bankerHand + " - Value: " + bankerHand.value());

        // Check for natural win first
        if (playerHand.isNatural() || bankerHand.isNatural()) {
            System.out.println("Natural win detected!");
            if (playerHand.isNatural()) {
                System.out.println("Player has a Natural.");
            }
            if (bankerHand.isNatural()) {
                System.out.println("Banker has a Natural.");
            }
        } else {
            // Determine the need for the third card for the player
            if (playerHand.value() <= 5) {
                System.out.println("Player draws a third card.");
                playerHand.add(shoe.deal());
                System.out.println("Player Hand: " + playerHand + " - Value: " + playerHand.value());
            }

            // Rules for the banker drawing a third card
            BaccaratCard playerThirdCard = playerHand.size() > 2 ? (BaccaratCard) playerHand.getCards().get(2) : null;
            if (shouldBankerDraw(bankerHand.value(), playerThirdCard)) {
                System.out.println("Banker draws a third card.");
                bankerHand.add(shoe.deal());
                System.out.println("Banker Hand: " + bankerHand + " - Value: " + bankerHand.value());
            }
        }
    }

    /**
     * Determines whether the banker should draw a third card based on the Baccarat rules.
     *
     * @param bankerValue the current value of the banker's hand
     * @param playerThirdCard the third card of the player, if any
     * @return true if the banker should draw a third card, false otherwise
     */
    private static boolean shouldBankerDraw(int bankerValue, BaccaratCard playerThirdCard) {
        if (playerThirdCard == null) {
            return bankerValue <= 5;
        }

        int playerThirdValue = playerThirdCard.value();
        switch (bankerValue) {
            case 0:
            case 1:
            case 2:
                return true;
            case 3:
                return playerThirdValue != 8;
            case 4:
                return playerThirdValue >= 2 && playerThirdValue <= 7;
            case 5:
                return playerThirdValue >= 4 && playerThirdValue <= 7;
            case 6:
                return playerThirdValue == 6 || playerThirdValue == 7;
            default:
                return false;
        }
    }
}

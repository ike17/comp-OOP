public class Baccarat {
    public static void main(String[] args) {
        try {
            Shoe shoe = new Shoe(6);  // Initialize the shoe with 6 decks
            shoe.shuffle();  // Shuffle the shoe

            BaccaratHand playerHand = new BaccaratHand();
            BaccaratHand bankerHand = new BaccaratHand();

            // Deal two cards alternately to player and banker
            playerHand.add(shoe.deal());
            bankerHand.add(shoe.deal());
            playerHand.add(shoe.deal());
            bankerHand.add(shoe.deal());

            // Display each hand and its value
            System.out.println("Player's hand: " + playerHand + " - Value: " + playerHand.value());
            System.out.println("Banker's hand: " + bankerHand + " - Value: " + bankerHand.value());

            // Check for naturals
            if (playerHand.isNatural()) {
                System.out.println("Player has a natural!");
            }
            if (bankerHand.isNatural()) {
                System.out.println("Banker has a natural!");
            }

            // Output the winner or if it's a tie based on the rules of Baccarat
            if (playerHand.value() > bankerHand.value()) {
                System.out.println("Player wins!");
            } else if (playerHand.value() < bankerHand.value()) {
                System.out.println("Banker wins!");
            } else {
                System.out.println("It's a tie!");
            }
        } catch (CardException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

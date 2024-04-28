import java.util.Scanner;

public class Baccarat {
    private int playerWins = 0;
    private int bankerWins = 0;
    private int ties = 0;
    private int roundsPlayed = 0;
    private final Shoe shoe;

    public Baccarat() {
        shoe = new Shoe(6); // Initialize shoe with 6 decks
    }

    public void playGame(boolean interactive) {
        Scanner scanner = interactive ? new Scanner(System.in) : null;

        do {
            playRound();
            roundsPlayed++;

            if (interactive) {
                System.out.print("Continue playing? (y/n): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    break;
                }
            }
        } while (shoe.size() >= 6);

        if (scanner != null) {
            scanner.close();
        }

        printGameStatistics();
    }

    private void playRound() {
        BaccaratHand playerHand = new BaccaratHand();
        BaccaratHand bankerHand = new BaccaratHand();

        playerHand.add(shoe.deal());
        bankerHand.add(shoe.deal());
        playerHand.add(shoe.deal());
        bankerHand.add(shoe.deal());

        System.out.println("Player: " + playerHand);
        System.out.println("Banker: " + bankerHand);

        if (playerHand.isNatural() || bankerHand.isNatural()) {
            determineOutcome(playerHand, bankerHand);
        } else {
            // Draw third card if necessary
            if (playerHand.value() <= 5) {
                playerHand.add(shoe.deal());
                System.out.println("Player draws: " + playerHand.getCards().get(2));
            }

            BaccaratCard thirdCard = playerHand.size() == 3 ? playerHand.getCards().get(2) : null;
            if (bankerShouldDrawCard(bankerHand, thirdCard)) {
                bankerHand.add(shoe.deal());
                System.out.println("Banker draws: " + bankerHand.getCards().get(2));
            }

            determineOutcome(playerHand, bankerHand);
        }
    }

    private boolean bankerShouldDrawCard(BaccaratHand bankerHand, BaccaratCard playerThirdCard) {
        // Implement Banker's rule for third card here...
        // This is simplified and should be replaced with actual Baccarat rules for drawing
        return bankerHand.value() <= 5;
    }

    private void determineOutcome(BaccaratHand playerHand, BaccaratHand bankerHand) {
        int playerValue = playerHand.value();
        int bankerValue = bankerHand.value();

        if (playerValue == bankerValue) {
            ties++;
            System.out.println("Tie.");
        } else if (playerValue > bankerValue) {
            playerWins++;
            System.out.println("Player wins.");
        } else {
            bankerWins++;
            System.out.println("Banker wins.");
        }
    }

    private void printGameStatistics() {
        System.out.println("\nGame Over");
        System.out.println("Rounds Played: " + roundsPlayed);
        System.out.println("Player Wins: " + playerWins);
        System.out.println("Banker Wins: " + bankerWins);
        System.out.println("Ties: " + ties);
    }

    public static void main(String[] args) {
        Baccarat game = new Baccarat();
        boolean interactive = args.length > 0 && (args[0].equals("-i") || args[0].equals("--interactive"));
        game.playGame(interactive);
    }
}

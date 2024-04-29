import java.util.Scanner;

/**
 * Main program to simulate the Baccarat game, including the rules for drawing a third card.
 */
public class Baccarat {
    private int playerWins = 0;
    private int bankerWins = 0;
    private int ties = 0;
    private int roundsPlayed = 1;
    private Shoe shoe;

    public Baccarat() {
        shoe = new Shoe(6);
        shoe.shuffle();
    }

    public void playGame(boolean interactive) {
        Scanner scanner = interactive ? new Scanner(System.in) : null;

        do {
            BaccaratHand playerHand = new BaccaratHand();
            BaccaratHand bankerHand = new BaccaratHand();
            playRound(playerHand, bankerHand);
            determineOutcome(playerHand, bankerHand);

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

    private void playRound(BaccaratHand playerHand, BaccaratHand bankerHand) {
        // Initial dealing for player and banker
        playerHand.add((BaccaratCard) shoe.deal());
        bankerHand.add((BaccaratCard) shoe.deal());
        playerHand.add((BaccaratCard) shoe.deal());
        bankerHand.add((BaccaratCard) shoe.deal());
        
        System.out.println("\nRound: " + roundsPlayed);
        System.out.println("Player Hand: " + playerHand + " = " + playerHand.value());
        System.out.println("Banker Hand: " + bankerHand + " = " + bankerHand.value());

        // Check for natural win first
        if (playerHand.isNatural() || bankerHand.isNatural()) {
            if (playerHand.isNatural()) {
                System.out.println("Player has a Natural");
            }
            if (bankerHand.isNatural()) {
                System.out.println("Banker has a Natural");
            }
        } else {
            // Determine the need for the third card for the player
            if (playerHand.value() <= 5) {
                System.out.println("Dealing third card to player...");
                playerHand.add((BaccaratCard) shoe.deal());
                System.out.println("Player Hand: " + playerHand + " = " + playerHand.value());
            }

            // Banker's third card rules
            BaccaratCard playerThirdCard = playerHand.size() > 2 ? (
                BaccaratCard) playerHand.getCards().get(2) : null;
            if (shouldBankerDraw(bankerHand.value(), playerThirdCard)) {
                System.out.println("Dealing third card to banker...");
                bankerHand.add((BaccaratCard) shoe.deal());
                System.out.println("Banker Hand: " + bankerHand + " = " + bankerHand.value());
            }
        }
    }

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

    private void determineOutcome(BaccaratHand playerHand, BaccaratHand bankerHand) {
        int playerValue = playerHand.value();
        int bankerValue = bankerHand.value();
        roundsPlayed++;

        if (playerValue == bankerValue) {
            ties++;
            System.out.println("Tie");
        } else if (playerValue > bankerValue) {
            playerWins++;
            System.out.println("Player Wins!");
        } else {
            bankerWins++;
            System.out.println("Banker Wins!");
        }
    }

    private void printGameStatistics() {
        int statsRounds = roundsPlayed - 1;
        System.out.println("\nGame Over");
        System.out.println(statsRounds + " Rounds Played");
        System.out.println(playerWins + " Player Wins");
        System.out.println(bankerWins + " Banker Wins");
        System.out.println(ties + " Ties");
    }

    public static void main(String[] args) {
        Baccarat game = new Baccarat();
        boolean interactive = args.length > 0 && (args[0].equals("-i") 
        || args[0].equals("--interactive"));
        game.playGame(interactive);
    }
}

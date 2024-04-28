import java.util.Scanner;

/**
 * A class to simulate a complete game of Baccarat.
 */
public class Baccarat {
    private static final int MIN_CARDS_FOR_NEW_ROUND = 6;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean interactiveMode = isInteractiveMode(args);
        int playerWins = 0;
        int bankerWins = 0;
        int ties = 0;
        int roundsPlayed = 0;

        try {
            Shoe shoe = new Shoe(6);
            shoe.shuffle();

            while (shoe.size() >= MIN_CARDS_FOR_NEW_ROUND) {
                if (interactiveMode && !playInteractiveRound(shoe, ++roundsPlayed, playerWins, bankerWins, ties)) {
                    break;
                } else if (!interactiveMode) {
                    playNonInteractiveRound(shoe, ++roundsPlayed, playerWins, bankerWins, ties);
                }
            }

            printStatistics(roundsPlayed, playerWins, bankerWins, ties);
        } catch (CardException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static boolean isInteractiveMode(String[] args) {
        return args.length > 0 && (args[0].equals("-i") || args[0].equals("--interactive"));
    }

    private static boolean playInteractiveRound(Shoe shoe, int roundNumber, int playerWins, int bankerWins, int ties) throws CardException {
        BaccaratHand playerHand = new BaccaratHand();
        BaccaratHand bankerHand = new BaccaratHand();

        System.out.println("\nRound " + roundNumber);
        playRound(shoe, playerHand, bankerHand);

        int handResult = determineWinner(playerHand, bankerHand);
        updateStatistics(handResult, playerWins, bankerWins, ties);

        System.out.print("Another round? (y/n): ");
        return scanner.nextLine().trim().toLowerCase().startsWith("y");
    }

    private static void playNonInteractiveRound(Shoe shoe, int roundNumber, int playerWins, int bankerWins, int ties) throws CardException {
        BaccaratHand playerHand = new BaccaratHand();
        BaccaratHand bankerHand = new BaccaratHand();

        playRound(shoe, playerHand, bankerHand);

        int handResult = determineWinner(playerHand, bankerHand);
        updateStatistics(handResult, playerWins, bankerWins, ties);
    }

    private static void playRound(Shoe shoe, BaccaratHand playerHand, BaccaratHand bankerHand) throws CardException {
        playerHand.add(shoe.deal());
        bankerHand.add(shoe.deal());
        playerHand.add(shoe.deal());
        bankerHand.add(shoe.deal());

        System.out.println("Player: " + playerHand + " = " + playerHand.value());
        System.out.println("Banker: " + bankerHand + " = " + bankerHand.value());

        if (playerHand.isNatural()) {
            System.out.println("Player has a Natural");
        } else if (bankerHand.isNatural()) {
            System.out.println("Banker has a Natural");
        } else {
            dealThirdCardIfNeeded(shoe, playerHand, bankerHand);
        }
    }

    private static void dealThirdCardIfNeeded(Shoe shoe, BaccaratHand playerHand, BaccaratHand bankerHand) throws CardException {
        if (playerHand.value() <= 5) {
            System.out.println("Dealing third card to player...");
            playerHand.add(shoe.deal());
            System.out.println("Player: " + playerHand + " = " + playerHand.value());
        }

        if (bankerHand.value() <= 5) {
            System.out.println("Dealing third card to banker...");
            bankerHand.add(shoe.deal());
            System.out.println("Banker: " + bankerHand + " = " + bankerHand.value());
        }
    }

    private static int determineWinner(BaccaratHand playerHand, BaccaratHand bankerHand) {
        int playerValue = playerHand.value();
        int bankerValue = bankerHand.value();

        if (playerValue > bankerValue) {
            return 1; // Player win
        } else if (playerValue < bankerValue) {
            return -1; // Banker win
        } else {
            return 0; // Tie
        }
    }

    private static void updateStatistics(int handResult, int playerWins, int bankerWins, int ties) {
        switch (handResult) {
            case -1:
                bankerWins++;
                System.out.println("Banker win!");
                break;
            case 0:
                ties++;
                System.out.println("Tie");
                break;
            case 1:
                playerWins++;
                System.out.println("Player win!");
                break;
        }
    }

    private static void printStatistics(int roundsPlayed, int playerWins, int bankerWins, int ties) {
        System.out.println("\n" + roundsPlayed + " rounds played");
        System.out.println(playerWins + " player wins");
        System.out.println(bankerWins + " banker wins");
        System.out.println(ties + " ties");
    }
}
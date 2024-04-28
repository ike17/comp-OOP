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
            if (interactive && !continueGame(scanner)) {
                break;
            }
        } while (shoe.size() >= 6);
        if (scanner != null) {
            scanner.close();
        }
        printGameStatistics();
    }

    private boolean continueGame(Scanner scanner) {
        System.out.print("Continue playing? (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.startsWith("y");
    }

    private void playRound() {
        BaccaratHand playerHand = new BaccaratHand();
        BaccaratHand bankerHand = new BaccaratHand();

        dealInitialCards(playerHand, bankerHand);
        displayHands(playerHand, bankerHand);

        if (playerHand.isNatural() || bankerHand.isNatural()) {
            determineOutcome(playerHand, bankerHand);
        } else {
            drawThirdCard(playerHand, bankerHand);
            determineOutcome(playerHand, bankerHand);
        }
    }

    private void dealInitialCards(BaccaratHand playerHand, BaccaratHand bankerHand) {
        playerHand.add(shoe.deal());
        bankerHand.add(shoe.deal());
        playerHand.add(shoe.deal());
        bankerHand.add(shoe.deal());
    }

    private void displayHands(BaccaratHand playerHand, BaccaratHand bankerHand) {
        System.out.println("Player: " + playerHand);
        System.out.println("Banker: " + bankerHand);
    }

    private void drawThirdCard(BaccaratHand playerHand, BaccaratHand bankerHand) {
        boolean playerDraws = shouldDrawThirdCard(playerHand);
        boolean bankerDraws = false;

        if (playerDraws) {
            playerHand.add(shoe.deal());
            System.out.println("Player draws: " + playerHand.getCards().get(2));
        }

        BaccaratCard playerThirdCard = playerDraws ? playerHand.getCards().get(2) : null;
        bankerDraws = shouldBankerDrawThirdCard(bankerHand, playerThirdCard);

        if (bankerDraws) {
            bankerHand.add(shoe.deal());
            System.out.println("Banker draws: " + bankerHand.getCards().get(2));
        }
    }

    private boolean shouldDrawThirdCard(BaccaratHand hand) {
        int handValue = hand.value();
        return handValue < 6 || (handValue == 6 && hand.getCards().get(0).value() == 6 && hand.getCards().get(1).value() == 6);
    }

    private boolean shouldBankerDrawThirdCard(BaccaratHand bankerHand, BaccaratCard playerThirdCard) {
        int bankerValue = bankerHand.value();

        if (bankerValue <= 2) {
            return true;
        } else if (bankerValue == 3) {
            return playerThirdCard != null && (playerThirdCard.value() == 8 || playerThirdCard.value() == 9);
        } else if (bankerValue == 4) {
            return playerThirdCard != null && playerThirdCard.value() >= 2 && playerThirdCard.value() <= 7;
        } else if (bankerValue == 5) {
            return playerThirdCard != null && playerThirdCard.value() >= 4 && playerThirdCard.value() <= 7;
        } else if (bankerValue == 6) {
            return playerThirdCard != null && (playerThirdCard.value() == 6 || playerThirdCard.value() == 7);
        } else {
            return false;
        }
    }

    private void determineOutcome(BaccaratHand playerHand, BaccaratHand bankerHand) {
        int playerValue = playerHand.value();
        int bankerValue = bankerHand.value();

        if (playerValue == bankerValue) {
            ties++;
            System.out.println("Tie.");
        } else if (playerValue > bankerValue || (playerValue == 9 && bankerValue == 8)) {
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
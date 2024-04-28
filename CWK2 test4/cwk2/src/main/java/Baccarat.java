import java.util.Scanner;

public class Baccarat {
    private int playerWins = 0;
    private int bankerWins = 0;
    private int ties = 0;
    private int roundsPlayed = 0;
    private final Shoe shoe;

    public Baccarat() {
        // Initialize shoe with 6 decks
        shoe = new Shoe(6); 
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
      int bankerTotal = bankerHand.value();
  
      // If player did not draw a third card (player's hand has only two cards)
      if (playerThirdCard == null) {
          return bankerTotal <= 5;
      }
  
      // If the player did draw a third card, apply complex rules based on the banker's total and player's third card value
      int playerThirdCardValue = playerThirdCard.value();  // Assuming value method returns Baccarat values correctly
  
      switch (bankerTotal) {
          case 0:
          case 1:
          case 2:
              return true;
          // Banker draws unless player's third card is an 8
          case 3:
              return playerThirdCardValue != 8;  
          // Banker draws if player's third card is 2-7
          case 4:
              return playerThirdCardValue >= 2 && playerThirdCardValue <= 7;  
          // Banker draws if player's third card is 4-7
          case 5:
              return playerThirdCardValue >= 4 && playerThirdCardValue <= 7;  
          // Banker draws if player's third card is 6 or 7
          case 6:
              return playerThirdCardValue == 6 || playerThirdCardValue == 7;  
          default:
              return false;
      }
  }
  
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
        boolean interactive = args.length > 0 && (args[0].equals("-i") 
        || args[0].equals("--interactive"));
        game.playGame(interactive);
    }
}

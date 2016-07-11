public class Game {
 
  public static void main(String[] args) {
    int guessFlag;
    int guess;
    int score;
    int numTries;
    String playAgainString;
    GuessingJar jar = new GuessingJar();
    Prompter prompter = new Prompter();

    // Prompt the user for game setup data
    prompter.promptForJarContents();

    // Play the game at least once
    do {

      // Load the jar
      System.out.printf("Loading the jar with no more than %d %s... ",
          prompter.getMaxQuantity(), prompter.getItem());
      jar.loadJar(prompter.getItem(), prompter.getMaxQuantity());
      System.out.printf("done%n");

      // Initialize the guesses array for storing guesses
      int[] guesses = new int[prompter.getMaxQuantity()];
      for (int index : guesses) {
        guesses[index] = 0;
      }

      // Prompt the user for a guess to play the game
      numTries = 0;
      do {
        //Play the game
        guess = prompter.promptForGuess();
        guessFlag = jar.guessQuantity(guess);
        if (-1 == guessFlag) {
          System.out.println("Your guess was too low");
        }
        if (1 == guessFlag) {
          System.out.println("Your guess was too high");
        }
        if (1 == guesses[guess-1]) {
          System.out.printf("You've already guessed %d.  Try again...%n", guess);
          continue;
        }
        guesses[guess-1] = 1;
        numTries += 1;
      } while (0 != guessFlag);

      // Tally the score
      score = prompter.getMaxQuantity() * (prompter.getMaxQuantity() - numTries);

      // Inform the user of the results and add him to the high score board if he made it.
      System.out.printf("You guessed correctly in %d tries!  There were %d %s in the jar!  You scored %d points!%n",
          numTries, guess, prompter.getItem(), score);
      UpdateHighScoreBoard(score);

      // See if the player wants to play again
      playAgainString = prompter.promptForPlayAgain();

    } while (0 != playAgainString.compareToIgnoreCase("no"));

  }



  // PRIVATE METHODS //

  private static void UpdateHighScoreBoard(int score){
    ScoreBoard scoreBoard = new ScoreBoard();
    scoreBoard.ReconcilePlayerWithScoreBoard(score);
    scoreBoard.WriteHighScoreBoard();
  }
  
}
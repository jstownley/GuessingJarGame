import java.io.*;

public class Prompter {
  public String mItem;
  public int mMaxQuantity;

  public Prompter() {
  }



  // PUBLIC METHODS //

  public String getItem() {
    return mItem;
  }

  public int getMaxQuantity() {
    return mMaxQuantity;
  }

  public void promptForJarContents() {
    mItem = GetValidString("Enter an item with which to fill the jar","lettersOnly");
    mMaxQuantity = GetValidInt("Enter a maximum quantity of items with which to fill the jar",0);
  }

  public int promptForGuess() {
    return GetValidInt(String.format("Guess how many %s in the jar ", mItem),0,mMaxQuantity);
  }

  public String promptForPlayerName() {
    return GetValidString("Enter your name","lettersAndNumbers");
  }

  public String promptForPlayAgain() {
    return GetValidString("Wanna play again? Enter \"yes\" or \"no\"","lettersOnly");
  }



  // PRIVATE METHODS //

  private String GetValidString(String promptText, String useCase) {
    String outString = "";
    boolean validItem = false;

    // Check out the useCase input for proper usage
    if ( (0 != useCase.compareToIgnoreCase("numbersOnly")) &&
         (0 != useCase.compareToIgnoreCase("lettersOnly")) &&
        (0 != useCase.compareToIgnoreCase("lettersAndNumbers")) ) {
      System.out.println("Invalid definition of useCase.  Input useCase must be either " +
          "\"numbersOnly\", \"lettersOnly\", or \"lettersAndNumbers\"");
      System.exit(0);
    }

    // Prompt the user for a valid string and don't stop until we get one.
    do {

      // Get the string from the user
      BufferedReader buffReader = new BufferedReader(new InputStreamReader(System.in));
      System.out.printf("%s : ", promptText);
      try {
        outString = buffReader.readLine();
      } catch (IOException e) {
        e.printStackTrace();
        continue;
      }

      // Check out the input string
      if (outString.isEmpty()) {
        System.out.println("Invalid input. Input cannot be empty");
        continue;
      }
      if (outString.trim().isEmpty()) {
        System.out.println("Invalid input.  Input cannot contain only whitespace chars");
        continue;
      }
      if ((0 == useCase.compareToIgnoreCase("lettersOnly")) && !CheckStringForLettersOnly(outString)) {
        System.out.printf("Invalid input: %s. Input must contain letters only%n", outString);
        continue;
      }
      if ((0 == useCase.compareToIgnoreCase("numbersOnly")) && !CheckStringForNumbersOnly(outString)) {
        System.out.printf("Invalid input: %s. Input must contain numbers only%n", outString);
        continue;
      }
      /* Note:  If the input string contains letters and numbers and the useCase is "lettersAndNumbers",
                do nothing else.  The check for empty strings and all whitespace chars will suffice.
       */

      // If we've made it this far, we're good to go
      validItem = true;

    } while (!validItem);

    return outString;
  }

  private int GetValidInt(String promptText, int minBound) {
    String outIntAsString = "";
    boolean validItem = false;

    // Prompt the user for a valid int and don't stop until we get one.
    do {

      // Get the int from the user
      outIntAsString = GetValidString(promptText,"numbersOnly");

      // Check out the input integer for valid minBound and decimal points
      if (!CheckStringForIntNumbersOnly(outIntAsString)){
        System.out.printf("Invalid input: %s.  Input number must be an integer%n", outIntAsString);
        continue;
      }
      /* If the input number passes the previous test, we know it's an int
         and we can run the following test without worry.
       */
      if (minBound >= Integer.parseInt(outIntAsString)) {
        System.out.printf("Invalid input: %s.  Input number must be greater than %d%n", outIntAsString, minBound);
        continue;
      }

      // If we've made it this far, we're good to go
      validItem = true;

    } while (!validItem);

    return Integer.parseInt(outIntAsString);

  }

  private int GetValidInt(String promptText, int minBound, int maxBound) {
    int outInt;
    boolean validItem = false;

    // Prompt the user for a valid int and don't stop until we get one.
    do {

      // Get the int from the user with a validated minBound
      outInt = GetValidInt(promptText,minBound);

      // Check out the input integer for valid maxBound
      if (maxBound < outInt) {
        System.out.printf("Invalid input: %d.  Input number must be less than or equal to %d%n", outInt, maxBound);
        continue;
      }

      // If we've made it this far, we're good to go
      validItem = true;

    } while (!validItem);

    return outInt;

  }

  private boolean CheckStringForNumbersOnly(String str) {
    return str.matches("[0-9\\-\\.]+");
  }

  private boolean CheckStringForIntNumbersOnly(String str) {
    return str.matches("[0-9\\-]+");
  }

  private boolean CheckStringForLettersOnly(String str) {
    return str.matches("[a-zA-Z ]+");
  }

}
import java.util.Random;

public class GuessingJar {

  public String mContents;
  public int mMaxQuantity;
  private int mQuantity;

  public GuessingJar() {
  }

  public void loadJar(String contents, int maxQuantity) {
    Random randNumGen = new Random();
    mContents = contents;
    mMaxQuantity = maxQuantity;
    mQuantity = randNumGen.nextInt(maxQuantity) + 1;
  }

  public int guessQuantity(int guess) {
    /* Return a flag based on whether the supplied
       guess is greater than, less than, or equal
       to the randomly set quantity */
    if (guess > mQuantity) {
      return 1;
    }
    if (guess < mQuantity) {
      return -1;
    }
    return 0;
  }

}
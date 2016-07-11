import java.io.*;

public class ScoreBoard{
  public int[] mScores;
  public String[] mNames;
  public String mFileName;

  public ScoreBoard() {
    String line;
    String[] elements;
    int thisRank;
    String thisName;

    // Initialize the member variables to blank scoreboard values
    mFileName = "guessing_jar_score_board.log";
    mScores   = new int[10];
    mNames    = new String[10];

    for (int ii=0; ii<mNames.length; ii++) {
      mNames[ii]  = "----------";
      mScores[ii] = 0;
    }

    // Read the score board log file one line at a time
    try {
      // FileReader reads text files in the default encoding.
      // Always wrap FileReader in BufferedReader.
      BufferedReader bufferedReader = new BufferedReader(new FileReader(mFileName));

      while((line = bufferedReader.readLine()) != null) {
        elements = line.split("\\s");

          /* Elements now contains the tokens of the line with spaces representing
             null in the array.  Skip ahead until the first element is a number.
           */
        if (elements[0].matches("[a-zA-Z]+|\\*+")) {
          continue;
        }

        // Decode the rank, which is the first element
        thisRank = Integer.parseInt(elements[0]) - 1;

        // Decode the score, which will be stored in the last element
        mScores[thisRank] = Integer.parseInt(elements[elements.length-1]);

        // Everything in between the rank and the score is either part of the name or an empty space
        thisName = "";
        for (int ii=1; ii<elements.length-1; ii++) {
          if (0 == elements[ii].compareTo("")) {
            thisName += " ";
          } else {
            thisName += elements[ii];
          }
        }
        mNames[thisRank] = thisName.trim();

      }

      bufferedReader.close();
    }
    catch(FileNotFoundException ex) {
      System.out.println("Unable to open file '" + mFileName + "'.  Initializing score board...");
    }
    catch(IOException ex) {
      ex.printStackTrace();
    }
  }

  public int CheckForHighScore(int score){
    int newRank = -1;

    // Loop through the scores and see if the new one is higher than any of the existing ones
    for (int ii=0; ii<mScores.length; ii++) {
      if (score >= mScores[ii]){
        newRank = ii;
        break;
      }
    }

    return newRank;
  }

  public void ReconcilePlayerWithScoreBoard(int score) {
    int newRank, count;
    int[] newScores = new int[10];
    String[] newNames = new String[10];

    // First, check for a high score
    newRank = CheckForHighScore(score);

    // If we have a new high score, add it to its proper place on the scoreboard
    count = 0;
    if (-1 != newRank) {
      // Begin by getting the player name
      System.out.println("Congratulations!  New high score!");

      for (int ii=0; ii<newScores.length; ii++) {
        // If ii is the new rank, use the input score
        if (ii == newRank) {
          newNames[ii] = CreateScoreBoardNameString(new Prompter().promptForPlayerName());
          newScores[ii] = score;
        } else {
          newNames[ii] = mNames[count];
          newScores[ii] = mScores[count];
          count += 1;
        }
      }

      // Now save off the new data as the official data
      mNames  = newNames;
      mScores = newScores;
    }
  }

  public void WriteHighScoreBoard() {
    String thisRank;
    String thisLine;
    int thisScore;

    try {
      // Open the log file for reading.
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(mFileName));

      // Write the header information to file and to screen
      thisLine = "Guessing Jar Game High Score Log";
      WriteLine(bufferedWriter, thisLine);
      thisLine = "*****";
      WriteLine(bufferedWriter, thisLine);
      thisLine = "Rank Name       Score";
      WriteLine(bufferedWriter, thisLine);

      // Write the scoreboard information
      for (int ii=0; ii<mNames.length; ii++) {

        // Determine the rank string with the appropriate number of whitespaces
        if (10 > ii+1) {
          thisRank = String.format("%d   ",ii+1);
        } else {
          thisRank = String.format("%d  ",ii+1);
        }

        // Write the line
        thisLine = String.format("%s %s %d",thisRank,CreateScoreBoardNameString(mNames[ii]),mScores[ii]);
        WriteLine(bufferedWriter,thisLine);
      }

      bufferedWriter.close();
    }
    catch(IOException ex) {
      ex.printStackTrace();
    }
  }



  // PRIVATE METHODS //

  private void WriteLine(BufferedWriter bufferedWriter, String line) throws IOException {
    bufferedWriter.write(line);
    bufferedWriter.newLine();
    System.out.println(line);
  }

  private String CreateScoreBoardNameString(String name) {
    String outName = "";
    for (int ii=0; ii<10; ii++) {
      if (name.length() > ii) {
        outName += name.charAt(ii);
      } else {
        outName += " ";
      }
    }
    return outName;
  }

}
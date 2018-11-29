
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BoardGame {
  // Constants
  private static final Integer ARRAY_SIZE = 15;
  private static final Integer OVER = 0;
  private static final Integer TO = 1;
  private static final Integer DEFAULT_START_PEG = 0;

  private static final char PRESENT_CHAR = 'x';
  private static final char MISSING_CHAR = '.';

  private static final Integer[][][] Moves = {
    {{1, 3}, {2, 5}},
    {{3, 6}, {4, 8}},
    {{4, 7}, {5, 9}},
    {{1, 0}, {6, 10}, {7, 12}, {4, 5}},
    {{7, 11}, {8, 13}},
    {{2, 0}, {4, 3}, {8, 12}, {9, 14}},
    {{3, 1}, {7, 8}},
    {{4, 2}, {8, 9}},
    {{4, 1}, {7, 6}},
    {{8, 7}},
    {{11, 12}},
    {{7, 4}, {12, 13}},
    {{7, 3}, {8, 5}, {11, 10}, {13, 14}},
    {{8, 4}, {12, 11}},
    {{9, 5}, {13, 12}}
  };

  private boolean[] board  = new boolean[ARRAY_SIZE];
  private List<String> solution = new ArrayList<String>();
  private boolean solved = false;

  // Default constructor
  public BoardGame() {
    for (Integer i = 0; i < ARRAY_SIZE; i++)
    {
      board[i] = true;
    }
  }

  // Return the character this peg should be in string
  private char getDrawChar(Integer peg) {
    return (pegIsPresent(peg)) ? PRESENT_CHAR : MISSING_CHAR;
  }

  private String drawBoard() {
    String boardString;

    char[] toDraw = new char[ARRAY_SIZE];

    for (int i = 0; i < ARRAY_SIZE; i++) {
      toDraw[i] = getDrawChar(i);
    }

    boardString  = "     " + toDraw[0] + "\n";
    boardString += "    "  + toDraw[1]  + " " + toDraw[2] + "\n";
    boardString += "   "   + toDraw[3]  + " " + toDraw[4]  + " " + toDraw[5] + "\n";
    boardString += "  "    + toDraw[6]  + " " + toDraw[7]  + " " + toDraw[8]  + " " + toDraw[9] + "\n";
    boardString += " "     + toDraw[10] + " " + toDraw[11] + " " + toDraw[12] + " " + toDraw[13] + " " +toDraw[14] + "\n";

    return boardString;
  }

  private boolean isInvalidPegIndex(Integer peg) {
    return (peg < 0 || peg >= ARRAY_SIZE);
  }

  private boolean pegIsPresent(Integer peg) {
    if (isInvalidPegIndex(peg)) {
      return false;
    }

    return board[peg];
  }

  private boolean pegIsAbsent(Integer peg) {
    return !pegIsPresent(peg);
  }

  private Integer currentNumberOfPegs() {
    Integer numPegs = 0;

    for (Integer i = 0; i < ARRAY_SIZE; i++)
    {
      if (pegIsPresent(i)) {
        numPegs++;
      }
    }

    return numPegs;
  }

  private void setPeg(Integer peg, boolean set) {
    if (isInvalidPegIndex(peg)) {
      return;
    }
    
    board[peg] = set;
  }

  private void removePeg(Integer peg) {
    setPeg(peg, false);
  }

  private void addPeg(Integer peg) {
    setPeg(peg, true);
  }

  private void reset() {
    solution.clear();

    for (int i = 0; i < ARRAY_SIZE; i++) {
      addPeg(i);
    }
  }

  // Initialize board with starting peg
  private void initializeBoard(Integer startPeg) {
    reset();
    solved = false;
    removePeg(startPeg);

    solution.add(drawBoard());
  }

  private void initializeBoard() {
    initializeBoard(DEFAULT_START_PEG);
  }

  // Get solution to a string
  private String solutionToString() {
    StringBuilder sb = new StringBuilder();
    
    for (String step : solution) {
      sb.append(step + "\n");
    }

    return sb.toString();
  }

  // Play game
  public String play(Integer peg) {
    if (isInvalidPegIndex(peg)) {
      printLine("Invalid peg (" + peg + ") provided. Defaulting to " + DEFAULT_START_PEG);
      peg = DEFAULT_START_PEG;
    }

    printLine("Playing for " + peg);

    // Initialize and solve board
    initializeBoard(peg);
    solve();

    // Return solution if solved, and empty string if not
    return solutionToString();
  }

  // Overload play function to make a default case
  public String play() {
    printLine("No starting peg provided. Defaulting to " + DEFAULT_START_PEG + "...");
    
    return play(DEFAULT_START_PEG);
  }

  private boolean puzzleIsSolved() {
    return (currentNumberOfPegs() == 1);
  }

  private boolean isInvalidMove(Integer over, Integer to) {
    return (pegIsAbsent(over) || pegIsPresent(to));
  }

  private boolean doMove(Integer from, Integer over, Integer to) {
    if (isInvalidMove(over, to)) {
      return false;
    }

    // Remove the peg that's jumping, and the one jumping over
    removePeg(from);;
    removePeg(over);

    // Peg is now where it landed
    addPeg(to);
  
    return true;
  }

  // Solve the puzzle recursively
  private void solve() {
    // Solved!
    if (puzzleIsSolved()) {
      solved = true;
      return;
    }

    // For each peg...
    for (int peg = 0; peg < ARRAY_SIZE; peg++)
    {
      // Skip already removed pegs
      if (pegIsAbsent(peg)) {
        continue;
      }

      // For each move of this peg...
      for (Integer[] move : Moves[peg]) {
        int from = peg;        // Move origin
        int over = move[OVER]; // Peg moving over
        int to   = move[TO];   // Peg moving to

        // Save the board so we can go back if necessary
        boolean[] savedBoard = board.clone();

        // Skip if the move isn't made
        if (doMove(from, over, to) == false) {
          continue;
        }

        // Add the current board string representation to the solution
        String currentBoard = drawBoard();
        solution.add(currentBoard);

        // Recursively solve the puzzle
        solve();
        if (puzzleIsSolved()) {
          solved = true;
          return;
        }

        // Didn't solve... revert board
        board = savedBoard;

        // Remove string representation from solution
        solution.remove(solution.size() - 1);
      }
    }
  }

  // Print wrapper because I'm lazy
  public static void print(String toPrint) {
    System.out.println(toPrint);
  }

  // See previous comment
  public static void printLine(String toPrint) {
    System.out.println(toPrint);
  }

  public static void main(String[] args) {
    BoardGame boardGame = new BoardGame();

    for (int peg = 0; peg < 5; peg++) {
      print(boardGame.play(peg));
    }
  }
}


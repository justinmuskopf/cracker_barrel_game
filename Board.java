import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Board {
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

  private Integer board[] = new Integer[ARRAY_SIZE];
  private boolean pegs[]  = new boolean[ARRAY_SIZE];
  private List<Integer[]> solution = new ArrayList<Integer[]>();

  public Board() {
    for (Integer i = 0; i < ARRAY_SIZE; i++)
    {
      board[i] = i + 1;
      pegs[i] = true;
    }
  }

  private boolean isInvalidPegIndex(Integer peg) {
    return (peg < 0 || peg >= ARRAY_SIZE);
  }

  private boolean pegIsPresent(Integer peg) {
    if (isInvalidPegIndex(peg)) {
      return false;
    }

    return pegs[peg];
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
    
    pegs[peg] = set;
  }

  private void removePeg(Integer peg) {
    setPeg(peg, false);
  }

  private void addPeg(Integer peg) {
    setPeg(peg, true);
  }

  private void init(Integer startPeg) {
    removePeg(startPeg);
    solve();
  }
  private void init() {
    init(DEFAULT_START_PEG);
  }


  private void solve() {
    if (currentNumberOfPegs() == 1) {
      return;
    }

    for (int peg = 0; peg < ARRAY_SIZE; peg++)
    {
      if (pegIsAbsent(peg)) {
        continue;
      }

      for (Integer[] move : Moves[peg]) {
        int from = peg;
        int over = move[OVER];
        int to   = move[TO];

        if (pegIsAbsent(over) || pegIsPresent(to)) {
          continue;
        }

        boolean savedBoard[] = pegs.clone();


      }

    }

  }

  public static void print(String toPrint) {
    System.out.println(toPrint);
  }
  public void print() {
    System.out.println(Arrays.toString(board));
  }

  public static void printLine(String toPrint) {
    System.out.println(toPrint);
  }

  public static void main(String[] args) {
    print("Hello!");

    Board board = new Board();

    board.init();
  }

}

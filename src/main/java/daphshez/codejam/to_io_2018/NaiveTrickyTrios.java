package daphshez.codejam.to_io_2018;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.apache.commons.math3.util.CombinatoricsUtils.binomialCoefficient;
import static org.apache.commons.math3.util.CombinatoricsUtils.factorial;

/**
 * A naive solution for Tricky Trios (https://code.google.com/codejam/contest/8384486/dashboard#s=p3&a=3)
 * Generates all possible boards, and runs optimal strategy on each board. Equivalent boards not used (i.e. 000111 and 111000).
 * Run time is ~0.5 second on my machine for n=5.
 */
public class NaiveTrickyTrios
{
    private int n;
    private int[] board;
    private int boardsGenerated;
    private long totalSolutions;

    public NaiveTrickyTrios(int n)
    {
        this.n = n;
        board = new int[n * 3];
        Arrays.fill(board, -1);
    }

    public double run()
    {
        recursion(0);
        return ((double) totalSolutions) / boardsGenerated;
    }

    public void recursion(int currentSet)
    {
        if (currentSet == n) {
            boardsGenerated += 1;
            totalSolutions += optimalSolver(board);
        } else {
            // find the first empty spot on the board
            int i = currentSet;
            while (board[i] != -1)
                i++;


            for (int j = i + 1; j < n * 3 - 1; j++) {
                if (board[j] != -1) continue;
                for (int k = j + 1; k < n * 3; k++) {
                    if (board[k] != -1) continue;

                    board[i] = board[j] = board[k] = currentSet;

                    recursion(currentSet + 1);

                    board[i] = board[j] = board[k] = -1;
                }
            }

        }

    }

    public int optimalSolver(int[] board)
    {
        int n = board.length / 3;
        int[] known = new int[n];
        int next = 0;
        int removed = 0;

        int r = 0;
        while (removed < n) {
            r += 1;

            if (known[board[next]] == 2) {  // (1) Cards are A??, and we know the locations of the other two A's
                removed += 1;
                next += 1;
            } else if (board[next] == board[next + 1]) {  // Cards are AA?, and...
                if (known[board[next]] == 1) {  // (2) we know the location of the other A
                    removed += 1;
                    next += 2;
                } else if (board[next] == board[next + 2]) {// (3) cards are AAA
                    removed += 1;
                    next += 3;
                } else {    // (4) cards are AAB and we still don't know where the 3rd A is
                    int B = board[next + 2];
                    known[board[next]] += 2;
                    known[B] += 1;
                    next += 3;

                    // it's possible that we now know where all the three B's are
                    if (known[B] == 3) {
                        removed += 1;
                        r += 1;
                    }
                }
            } else { // cards are AB, and we don't know the locations of the two other A's
                int B = board[next + 1];

                known[board[next]] += 1;
                known[B] += 1;
                next += 2;

                // it's possible that we now know where all the three B's are
                if (known[B] == 3) {
                    removed += 1;
                    r += 1;
                }


            }
        }

        return r;
    }

    private static long expectedNumberOfBoards(int n)
    {
        return IntStream.rangeClosed(1, n).mapToLong(i -> binomialCoefficient(i * 3, 3))
                .reduce(1, (x, y) -> x * y) / factorial(n);
    }


    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException
    {
        IntStream.of(1, 2, 3, 4, 5)
                .forEach(i -> System.out.println(i + " -> " + (new NaiveTrickyTrios(i).run())));

    }


}
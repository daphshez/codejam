package daphshez.codejam.to_io_2018;

import daphshez.codejam.common.CodeJamRunner;
import daphshez.codejam.common.CodeJamSolution;
import daphshez.codejam.common.FixedLengthCaseReader;
import daphshez.codejam.common.PrecisionFileComparer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 Think about the optimal strategy as a state machine.

 The state is described by (n1, n2, n3) where n_i is the number of sets of which i cards are known. S_0 is (0, 0, 0),
 and the game ends when the state is (0, 0, n). The transition table is given below.

 The key insight is that each of the transitions out of a state has a probability that depends only on (n1, n2, n3) -
 and not on the next input.


 Transitions:
 (1) cards are A? and known[A] == 2 -->  (n1, n2 - 1, n3 +  1)    (one round)   A 2->3
 (2) cards are AA? && known[A] == 1  -->  (n1 - 1, n2, n3 + 1)    (one round)   A 1->3
 (3) cards are AAA  -->  (n1, n2, n3 + 1)                         (one round)   A 0->3
 --- cards are AAB and known[A] == 0 and
    (4) known[B] == 0   --> (n1 + 1, n2 + 1, n3)               (one round)   A 0->2  B 0->1
    (5) known[B] == 1   --> (n1 - 1, n2 + 2, n3)               (one round)           B 1->2
    (6) known[B] == 2   --> (n1, n2, n3 + 1)                   (two rounds)          B 2->3
 --- cards are AB and
  --- known[A] == 0 and
        (7) known[B] == 0  --> (n1 + 2, n2, n3)              (one round)    A 0->1  B 0->1
        (8) known[B] == 1 -->  (n1, n2 + 1, n3)              (one round)            B 1->2
        (9) known[B] == 2 -->  (n1 + 1, n2 -1, n3 + 1)       (two rounds)           B 2->3
  --- known[A] == 1 and
        (10) known[B] == 0  --> (n1, n2 + 1, n3)              (one round)    A 1->2  B 0->1
        (11) known[B] == 1 -->  (n1-2, n2 + 2, n3)            (one round)    A 1->2  B 1->2
        (12) known[B] == 2 -->  (n1-1, n2, n3 + 1)            (two rounds)   A 1->2  B 2->3


 */

public class FastTrickyTrios
{
    private final int n;
    private final State endState;
    private final Map<State, Double> cache = new HashMap<>();


    private class State
    {
        final int n1, n2, n3;
        final int n0;           // n - (n1 + n2 +  n3)
        final double l, l1, l2; //  l = leftUnknown, l1 = l-1, l2 = l-2 ; kept as doubles to avoid integer division

        State(int n1, int n2, int n3)
        {
            this.n1 = n1;
            this.n2 = n2;
            this.n3 = n3;
            n0 = n - (n1 + n2 + n3);
            l = n * 3 - (n1 + 2 * n2 + 3 * n3);
            l1 = l - 1;
            l2 = l - 2;

            if (n0 < 0)
                throw new IllegalArgumentException("Something went wrong");
        }

        State transition(int delta1, int delta2, int delta3)
        {
            return new State(n1 + delta1, n2 + delta2, n3 + delta3);
        }


        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return n1 == state.n1 &&
                    n2 == state.n2 &&
                    n3 == state.n3;
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(n1, n2, n3);
        }

        public double prob(int caseNumber)
        {
            switch (caseNumber) {
                case 1:
                    return n2 / l;
                case 2:
                    return 2 * n1 / (l * l1);
                case 3:
                    return 3 * n0 * 2 / (l * l1 * l2);
                case 4:
                    return (3 * n0 * 2 * 3 * (n0 - 1)) / (l * l1 * l2);
                case 5:
                    return (3 * n0 * 2 * 2 * n1) / (l * l1 * l2);
                case 6:
                    return (3 * n0 * 2 * n2) / (l * l1 * l2);
                case 7:
                    return 3 * n0 * 3 * (n0 - 1) / (l * l1);
                case 8:
                    return 3 * n0 * 2 * n1 / (l * l1);
                case 9:
                    return 3 * n0 * n2 / (l * l1);
                case 10:
                    return 2 * n1 * 3 * n0 / (l * l1);
                case 11:
                    return 2 * n1 * 2 * (n1 - 1) / (l * l1);
                case 12:
                    return 2 * n1 * n2 / (l * l1);
                default:
                    throw new IllegalArgumentException();
            }

        }

        public State transition(int caseNumber)
        {
            switch (caseNumber) {
                case 1:
                    return transition(0, -1, 1);
                case 2:
                    return transition(-1, 0, 1);
                case 3:
                    return transition(0, 0, 1);
                case 4:
                    return transition(1, 1, 0);
                case 5:
                    return transition(-1, 2, 0);
                case 6:
                    return transition(0, 0, 1);
                case 7:
                    return transition(2, 0, 0);
                case 8:
                    return transition(0, 1, 0);
                case 9:
                    return transition(1, -1, 1);
                case 10:
                    return transition(0, 1, 0);
                case 11:
                    return transition(-2, 2, 0);
                case 12:
                    return transition(-1, 0, 1);
                default:
                    throw new IllegalArgumentException();


            }

        }



        // a sanity checking method
        // the sum of probability should be 1 in every state except the end state
        double sumProbabilities()
        {
            return IntStream.rangeClosed(1, 12).mapToDouble(this::prob).sum();
        }

        @Override
        public String toString()
        {
            return "(" + n1 + "," + n2 + "," + n3 + ")";
        }
    }


    FastTrickyTrios(int n)
    {
        this.n = n;
        endState = new State(0, 0, n);
    }

    double solve(State state)
    {
        if (state.equals(endState))
            return 0;

        if (cache.containsKey(state))
            return cache.get(state);


        double res = IntStream.rangeClosed(1, 12)
                .mapToDouble(c -> {
                    if (state.prob(c) > 0) {
                        int r = 1;
                        if (c == 6 || c == 9 || c == 12)
                            r = 2;
                        return state.prob(c) * (r + solve(state.transition(c)));
                    }
                    else {
                        return 0;
                    }
                }).sum();

        cache.put(state, res);

        return res;
    }


    public double solve()
    {
        System.out.println(n);
        return solve(new State(0, 0, 0));
    }

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException
    {
        CodeJamRunner.run(new FixedLengthCaseReader(1),
                codeJamCase -> {
                    FastTrickyTrios solver = new FastTrickyTrios((int) codeJamCase.intAt(0));
                    return new CodeJamSolution(codeJamCase.getCounter(), Double.toString(solver.solve()));
                },
                "to_io_2018",
                "D",
                args[0],
                new PrecisionFileComparer());


    }

}

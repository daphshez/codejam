package daphshez.codejam.to_io_2015;

import daphshez.codejam.common.CodeJamCase;
import daphshez.codejam.common.CodeJamRunner;
import daphshez.codejam.common.CodeJamSolution;
import daphshez.codejam.common.FixedLengthCaseReader;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Function;

// recursive solution for problem NativeTrickyTrios https://code.google.com/codejam/contest/9214486/dashboard#s=p3
// too slow for the large input
// could probably be optimised by dropping the visited list and using reducing the board size correctly

public class RecursiveGooglander implements Function<CodeJamCase, CodeJamSolution>
{
    @Override
    public CodeJamSolution apply(CodeJamCase codeJamCase)
    {

        long r = codeJamCase.intAt(0);
        long c = codeJamCase.intAt(1);

        System.out.println("Solving for " + r + "," + c + " " + (new Date()));

        int maxDepth = (int) (r * c);

        ArrayList<Pair<Integer, Integer>> visited = new ArrayList<>(maxDepth);

        visited.add(new ImmutablePair<>(0, 0));

        return new CodeJamSolution(codeJamCase.getCounter(),
                search(0, 0, r, c, 0, visited));


    }

    private int search(long minR, long minC,
                       long maxR,
                       long maxC,
                       int direction,
                       ArrayList<Pair<Integer, Integer>> visited)
    {

        Pair<Integer, Integer> current = visited.get(visited.size() - 1);

        int fromHere = 0;

        fromHere += executeNextMove(minR, minC, maxR, maxC, direction, visited, next(current, direction));



        fromHere += executeNextMove((direction == 270) ? minR + 1 : minR,
                (direction == 0) ? minC + 1: minC,
                (direction == 90) ? maxR - 1 : maxR,
                (direction == 180) ? maxC - 1 : maxC,
                (direction + 90) % 360,
                visited,
                next(current, (direction + 90) % 360));

        return Math.max(1, fromHere);
    }

    private int executeNextMove(long minR, long minC,
                                long maxR,
                                long maxC,
                                int direction,
                                ArrayList<Pair<Integer, Integer>> visited,
                                Pair<Integer, Integer> nextMove)
    {
        int fromHere = 0;
        if (onBoard(minR, minC, maxR, maxC, nextMove) && notVisited(visited, nextMove)) {
            visited.add(nextMove);
            fromHere = search(minR, minC, maxR, maxC, direction, visited);
            visited.remove(visited.size() - 1);
        }
        return fromHere;
    }

    private boolean notVisited(ArrayList<Pair<Integer, Integer>> visited, Pair<Integer, Integer> move2)
    {
        return !visited.contains(move2);
    }

    private boolean onBoard(long minR, long minC,
            long maxR, long maxC, Pair<Integer, Integer> move1)
    {
        return move1.getLeft() >= minR && move1.getLeft() < maxR &&
                move1.getRight() >= minC && move1.getRight() < maxC;
    }


    private Pair<Integer, Integer> next(Pair<Integer, Integer> current, int direction)
    {
        if (direction == 0)
            return new ImmutablePair<>(current.getLeft() + 1, current.getRight());
        if (direction == 90)
            return new ImmutablePair<>(current.getLeft(), current.getRight() + 1);
        if (direction == 180)
            return new ImmutablePair<>(current.getLeft() - 1, current.getRight());
        if (direction == 270)
            return new ImmutablePair<>(current.getLeft(), current.getRight() - 1);

        throw new IllegalArgumentException();
    }

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException
    {
        Class<Function<CodeJamCase, CodeJamSolution>> clazz = (Class<Function<CodeJamCase, CodeJamSolution>>) MethodHandles.lookup().lookupClass();

        CodeJamRunner.run(new FixedLengthCaseReader(),
                clazz.newInstance(),
                "to_io_2015",
                "NativeTrickyTrios",
                args[0]);


    }
}

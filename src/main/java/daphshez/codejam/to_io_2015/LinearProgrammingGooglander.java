package daphshez.codejam.to_io_2015;

import daphshez.codejam.common.CodeJamCase;
import daphshez.codejam.common.CodeJamRunner;
import daphshez.codejam.common.CodeJamSolution;
import daphshez.codejam.common.FixedLengthCaseReader;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// Linear programming solution from problem RecursiveGooglander
// Fast enough to solve large input

// if r == 1 or c == 1, RecursiveGooglander(r, c) = 1
// else, RecursiveGooglander(r, c) = sigma (i = 1..r) RecursiveGooglander(i, c - 1)

public class LinearProgrammingGooglander implements Function<CodeJamCase, CodeJamSolution>
{
    private final Map<Pair<Integer, Integer>, Long> cache = new HashMap<>();

    @Override
    public CodeJamSolution apply(CodeJamCase codeJamCase)
    {
        int r = (int)codeJamCase.intAt(0);
        int c = (int)codeJamCase.intAt(1);

        System.out.println("Solving for " + r + "," + c + " " + (new Date()));

        return new CodeJamSolution(codeJamCase.getCounter(),
                solveForBoardSize(c, r));


    }

    public long solveForBoardSize(int width, int height)
    {
        if (width == 1 || height == 1)
            return 1;

        Pair<Integer, Integer> key = new ImmutablePair<>(Math.min(width, height), Math.max(width, height));

        if (cache.containsKey(key))
            return cache.get(key);

        long res = 0;
        for (int i = 1; i <= height; i++)
            res += solveForBoardSize(width - 1, i);

        cache.put(key, res);
        return res;
    }

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException
    {
        Class<Function<CodeJamCase, CodeJamSolution>> clazz = (Class<Function<CodeJamCase, CodeJamSolution>>) MethodHandles.lookup().lookupClass();

        CodeJamRunner.run(new FixedLengthCaseReader(),
                clazz.newInstance(),
                "to_io_2015",
                "RecursiveGooglander",
                args[0]);

        System.out.println(clazz.getSimpleName());
    }
}
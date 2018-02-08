package daphshez.codejam.to_io_2015;

// https://code.google.com/codejam/contest/9214486/dashboard#s=p1

import daphshez.codejam.common.CodeJamCase;
import daphshez.codejam.common.CodeJamRunner;
import daphshez.codejam.common.CodeJamSolution;
import daphshez.codejam.common.FixedLengthCaseReader;
import org.apache.commons.math3.util.CombinatoricsUtils;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.lang.Math.max;

public class DrearyDesign implements Function<CodeJamCase, CodeJamSolution>
{
    private Map<Integer, Long> cache = new HashMap<>();


    @Override
    public CodeJamSolution apply(CodeJamCase codeJamCase)
    {
        long k = codeJamCase.intAt(0);
        int v = (int)codeJamCase.intAt(1);

        System.out.println(k + " " + v);

        long aaa = k + 1;

        long aab = max(k - v, 0) * v;
        for (int a = 1; a <= v; a++)
            aab += a;

        long abc = 0;
        if (v >= 2) {
            abc = max(k - v, 0) * CombinatoricsUtils.binomialCoefficient(v, 2);
            for (int a = 2; a <= v; a++)
                abc += CombinatoricsUtils.binomialCoefficient(a, 2);
        }

        long res = aaa + 6 * aab + 6 * abc;

        return new CodeJamSolution(codeJamCase.getCounter(),res);
    }

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException
    {
        Class<Function<CodeJamCase, CodeJamSolution>> clazz = (Class<Function<CodeJamCase, CodeJamSolution>>) MethodHandles.lookup().lookupClass();

        CodeJamRunner.run(new FixedLengthCaseReader(),
                clazz.newInstance(),
                "to_io_2015",
                "B",
                args[0]);

    }
}

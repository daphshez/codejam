package daphshez.codejam.to_io_2018;

import daphshez.codejam.common.CodeJamCase;
import daphshez.codejam.common.CodeJamRunner;
import daphshez.codejam.common.CodeJamSolution;
import daphshez.codejam.common.FixedLengthCaseReader;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class C implements Function<CodeJamCase, CodeJamSolution>
{
    @Override
    public CodeJamSolution apply(CodeJamCase codeJamCase)
    {
        String[] names = codeJamCase.lines().get(1).split(" ");
        String res = testMe(names[0], names[1], names[2]) + " " +
                testMe(names[1], names[0], names[2]) + " " +
                testMe(names[2], names[0], names[1]);
        return new CodeJamSolution(codeJamCase.getCounter(), res);
    }


    public static String testMe(String me,
                                String o1,
                                String o2)
    {
        // pairs of letters where p1 < p2
        Set<Pair<Character, Character>> constraints = new HashSet<>();

        boolean order1determined = false;
        boolean order2determined = false;


        for (int i = 0; i < me.length(); i++) {
            if (!order1determined && o1.charAt(i) != me.charAt(i)) {
                // we must have o1.charAt(i) < me.charAt(i); check that it's not the reverse
                Pair<Character, Character> badConstraint = new ImmutablePair<>(me.charAt(i), o1.charAt(i));
                if (constraints.contains(badConstraint))
                    return "NO";
                constraints.add(new ImmutablePair<>(o1.charAt(i), me.charAt(i)));
                order1determined = true;
            }

            if (!order2determined && o2.charAt(i) != me.charAt(i)) {
                Pair<Character, Character> badConstraint = new ImmutablePair<>(o2.charAt(i), me.charAt(i));
                if (constraints.contains(badConstraint))
                    return "NO";
                constraints.add(new ImmutablePair<>(me.charAt(i), o2.charAt(i)));
                order2determined = true;
            }

            if (order1determined && order2determined)
                break;

        }

        return "YES";
    }





    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException
    {
        Class<Function<CodeJamCase, CodeJamSolution>> clazz = (Class<Function<CodeJamCase, CodeJamSolution>>) MethodHandles.lookup().lookupClass();

        CodeJamRunner.run(new FixedLengthCaseReader(2),
                clazz.newInstance(),
                "to_io_2018",
                clazz.getSimpleName(),
                args[0]);
    }

}
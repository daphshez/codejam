package daphshez.codejam.to_io_2015;

import daphshez.codejam.common.CodeJamCase;
import daphshez.codejam.common.CodeJamRunner;
import daphshez.codejam.common.CodeJamSolution;
import daphshez.codejam.common.FixedLengthCaseReader;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.function.Function;

// I/O Error solution
// https://code.google.com/codejam/contest/9214486/dashboard#s=p0


public class A implements Function<CodeJamCase, CodeJamSolution>
{
    @Override
    public CodeJamSolution apply(CodeJamCase codeJamCase)
    {
        String input = codeJamCase.line(1);

        char[] res = new char[input.length() / 8];

        for (int i = 0; i < input.length(); i++) {
            int bit = (input.charAt(i) == 'I') ? 1 : 0;

            res[i / 8] = (char) ((res[i / 8] << 1) ^ bit);
        }

        return new CodeJamSolution(codeJamCase.getCounter(), new String(res));


    }

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException
    {
        Class<Function<CodeJamCase, CodeJamSolution>> clazz = (Class<Function<CodeJamCase, CodeJamSolution>>) MethodHandles.lookup().lookupClass();

        CodeJamRunner.run(new FixedLengthCaseReader(2),
                clazz.newInstance(),
                "to_io_2015",
                clazz.getSimpleName(),
                args[0]);
    }
}

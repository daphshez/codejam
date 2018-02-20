package daphshez.codejam.to_io_2018;

import daphshez.codejam.common.CodeJamCase;
import daphshez.codejam.common.CodeJamRunner;
import daphshez.codejam.common.CodeJamSolution;
import daphshez.codejam.common.VariableLengthCaseReader;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class B implements Function<CodeJamCase, CodeJamSolution>
{
    // https://codejam.withgoogle.com/codejam/contest/dashboard?c=8384486#s=p1
    @Override
    public CodeJamSolution apply(CodeJamCase codeJamCase)
    {
        List<List<Long>> lines = codeJamCase.linesAsNumbers();

        long n = lines.get(lines.size() - 1).get(0);
        int e = lines.get(lines.size() - 1).get(1).intValue();

        int minimumCEOExperienceLevel = e + 1;

        long unusedManagementCapacity = n * e;
        long employeesNeedingManager = n;

        for (int i = lines.size() - 2; i >= 0; i--)
        {
            n = lines.get(i).get(0);
            e = lines.get(i).get(1).intValue();

            long solved = Math.min(unusedManagementCapacity, n);
            unusedManagementCapacity = unusedManagementCapacity - solved + n * e;
            employeesNeedingManager = employeesNeedingManager + n - solved;
        }


        return new CodeJamSolution(codeJamCase.getCounter(), Math.max(minimumCEOExperienceLevel, employeesNeedingManager));
    }

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException
    {
        Class<Function<CodeJamCase, CodeJamSolution>> clazz = (Class<Function<CodeJamCase, CodeJamSolution>>) MethodHandles.lookup().lookupClass();

        CodeJamRunner.run(new VariableLengthCaseReader(),
                clazz.newInstance(),
                "to_io_2018",
                clazz.getSimpleName(),
                args[0]);
    }

}
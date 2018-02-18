package daphshez.codejam.to_io_2018;

import com.google.common.math.LongMath;
import daphshez.codejam.common.CodeJamCase;
import daphshez.codejam.common.CodeJamRunner;
import daphshez.codejam.common.CodeJamSolution;
import daphshez.codejam.common.FixedLengthCaseReader;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class A implements Function<CodeJamCase, CodeJamSolution>
{
    @Override
    public CodeJamSolution apply(CodeJamCase codeJamCase) {
        List<Long> values = codeJamCase.asListOfIntegers();
        Collections.sort(values);

        long distance = 0;
        long error = 0;

        int i = 0;
        for (Long value: values)
        {
            error += LongMath.pow(value - distance, 2);

            if (i++ % 2 == 1) distance += 1;
        }

        return new CodeJamSolution(codeJamCase.getCounter(), error);
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
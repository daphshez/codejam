package daphshez.codejam.to_io_2015;

import daphshez.codejam.common.CodeJamCase;
import daphshez.codejam.common.CodeJamRunner;
import daphshez.codejam.common.CodeJamSolution;
import daphshez.codejam.common.FixedLengthCaseReader;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

// solution for problem C
// https://code.google.com/codejam/contest/9214486/dashboard#s=p2

public class Multifactorials implements Function<CodeJamCase, CodeJamSolution>
{
    private final TreeMap<Integer, Integer> multifactorialDigits = new TreeMap<>();

    public void init()
    {
        for (int e = 9000; e > 0; e--) {
            int d = multifactorialDigits(9000, e);
            multifactorialDigits.put(d, e);
        }
    }

    @Override
    public CodeJamSolution apply(CodeJamCase codeJamCase)
    {
        int d = (int)codeJamCase.intAt(0);

        Map.Entry<Integer, Integer> entry = multifactorialDigits.floorEntry(d - 1);

        String result = "...";
        if (entry != null)
            result = "IT'S OVER 9000" + StringUtils.repeat("!", entry.getValue());

        return new CodeJamSolution(codeJamCase.getCounter(), result);
    }


    private static int multifactorialDigits(int n, int e)
    {
        BigInteger factorial = BigInteger.valueOf(n);
        int k = 1;
        while (n - k * e > 0) {
            BigInteger multiplyBy = BigInteger.valueOf(n - k * e);
            factorial = factorial.multiply(multiplyBy);
            k += 1;
        }
        return factorial.toString().length();       // but see https://stackoverflow.com/questions/18828377/
    }


    public static void main(String[] args) throws IOException
    {
        Multifactorials multifactorials = new Multifactorials();
        multifactorials.init();

        CodeJamRunner.run(new FixedLengthCaseReader(),
                multifactorials,
                "to_io_2015",
                "C",
                args[0]);

    }


}

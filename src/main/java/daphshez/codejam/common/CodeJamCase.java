package daphshez.codejam.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CodeJamCase
{
    private final int counter;
    private final List<String> lines;

    private long[] firstLineAsIntegers;

    public CodeJamCase(int counter, List<String> lines)
    {
        this.counter = counter;
        this.lines = lines;
    }

    public CodeJamCase(int counter, String line)
    {
        this.counter = counter;
        lines = Collections.singletonList(line);
    }

    public int getCounter()
    {
        return counter;
    }

    public String line()
    {
        return lines.get(0);
    }

    public String line(int index)
    {
        return lines.get(index);
    }


    public long intAt(int index)
    {
        if (firstLineAsIntegers == null) {
            firstLineAsIntegers = Arrays.stream(lines.get(0).split(" "))
                    .mapToLong(Long::parseLong).toArray();
        }
        return firstLineAsIntegers[index];
    }
}

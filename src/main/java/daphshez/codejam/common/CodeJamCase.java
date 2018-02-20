package daphshez.codejam.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CodeJamCase
{
    private final int counter;
    private final List<String> lines;

    private List<List<Long>> linesAsNumbers;

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

    public List<Long> asListOfNumbers(int lineNumber)
    {
        parseLines();
        return linesAsNumbers.get(lineNumber);
    }

    private void parseLines()
    {
        if (linesAsNumbers == null)
        {
            linesAsNumbers = lines.stream().map(line ->
                Arrays.stream(line.split(" ")).map(Long::parseLong).collect(Collectors.toList())
            ).collect(Collectors.toList());
        }
    }

    // for a one-line input which contains a (fixed-length) list of integers
    public long intAt(int index)
    {
        List<Long> firstLine = asListOfNumbers(0);
        return firstLine.get(index);
    }

    public long intAt(int lineNumber, int index)
    {
        List<Long> line = asListOfNumbers(lineNumber);
        return line.get(index);
    }

    public List<String> lines()
    {
        return lines;
    }

    public List<List<Long>> linesAsNumbers() {
        parseLines();
        return linesAsNumbers;
    }
}

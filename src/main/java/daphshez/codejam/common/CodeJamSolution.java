package daphshez.codejam.common;

public class CodeJamSolution
{
    private final int counter;
    private final String line;

    public CodeJamSolution(int counter, String line)
    {
        this.counter = counter;
        this.line = line;
    }

    public int getCounter()
    {
        return counter;
    }

    public String getLine()
    {
        return line;
    }

    public CodeJamSolution(int counter, long solution)
    {
        this.counter = counter;
        this.line = Long.toString(solution);
    }
}

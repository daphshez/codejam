package daphshez.codejam.common;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class FixedLengthCaseReader implements Function<Path, Stream<CodeJamCase>>
{
    private final int length;

    public FixedLengthCaseReader()
    {
        this.length = 1;
    }

    public FixedLengthCaseReader(int length)
    {
        this.length = length;
    }

    @Override
    public Stream<CodeJamCase> apply(Path path)
    {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            int caseCount = Integer.parseInt(reader.readLine());
            List<CodeJamCase> cases = new ArrayList<>(caseCount);
            while(cases.size() < caseCount)
            {
                List<String> lines = new ArrayList<>(length);
                for (int i = 0; i < length; i++) {
                    lines.add(reader.readLine());
                }
                cases.add(new CodeJamCase(cases.size() + 1, lines));
            }
            return cases.stream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

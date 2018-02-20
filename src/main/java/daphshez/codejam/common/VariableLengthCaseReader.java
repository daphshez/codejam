package daphshez.codejam.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class VariableLengthCaseReader implements Function<Path, Stream<CodeJamCase>>
{
    @Override
    public Stream<CodeJamCase> apply(Path path)
    {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            int caseCount = Integer.parseInt(reader.readLine());
            List<CodeJamCase> cases = new ArrayList<>(caseCount);
            while(cases.size() < caseCount)
            {
                int length = Integer.parseInt(reader.readLine());
                List<String> lines = new ArrayList<>(length);
                while (lines.size() < length) {
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

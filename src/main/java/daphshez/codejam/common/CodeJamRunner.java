package daphshez.codejam.common;

import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CodeJamRunner
{
    public static void run(Function<Path, Stream<CodeJamCase>> reader,
                           Function<CodeJamCase, CodeJamSolution> solver,
                           String contest,
                           String problem,
                           String stage) throws IOException
    {
        Path input = Paths.get("input", contest, problem + "-" + stage + ".in");
        Path output = Paths.get("output", contest, problem + "-" + stage + ".out");

        List<CodeJamSolution> solutions = reader.apply(input)
                .map(solver)
                .collect(Collectors.toList());

        solutions.sort(Comparator.comparingInt(CodeJamSolution::getCounter));

        try (BufferedWriter writer = Files.newBufferedWriter(output)) {
            solutions.forEach(
                    s -> {
                        try {
                            writer.write("Case #" + s.getCounter() + ": " + s.getLine() + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });


        }

        Path expected = Paths.get("input", contest, problem + "-" + stage + ".expected");

        if (Files.exists(expected))
        {
            boolean equals = FileUtils.contentEqualsIgnoreEOL(expected.toFile(), output.toFile(), "utf8");

            if (equals) {
                System.out.println("Correct result!");
            }
            else {
                System.out.println("Result different from expected :-(");
            }
        }

        System.out.println("\n\nI was running " + solver.getClass().getSimpleName());
        System.out.println("on " +  contest + " - " + problem + " - " + stage);
    }

}

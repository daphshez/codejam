package daphshez.codejam.common;

import com.codepoetics.protonpack.StreamUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrecisionFileComparer implements BiConsumer<Path, Path>
{
    private static final Pattern linePattern = Pattern.compile("Case #(\\d+): ([\\d.]+)");
    private static final double epsilon = Math.pow(10, -6);

    @Override
    public void accept(Path expected, Path output)
    {
        try {
            // check the files have the same number of lines
            if (Files.lines(expected).count() != Files.lines(output).count()) {
                System.out.println("Files have different number of lines");
            }


            StreamUtils.zip(
                    Files.lines(expected),
                    Files.lines(output),
                    ImmutablePair::new).forEach(pair -> {
                Matcher expectedMatcher = linePattern.matcher(pair.getLeft());
                Matcher outputMatcher = linePattern.matcher(pair.getRight());

                expectedMatcher.matches();
                outputMatcher.matches();


                if (!outputMatcher.group(1).equals(expectedMatcher.group(1))) {
                    System.out.println("Incorrect case numbering, should be " + expectedMatcher.group(1) + " but was " + outputMatcher.group(1));
                }

                double expectedValue = Double.parseDouble(expectedMatcher.group(2));
                double outputValue = Double.parseDouble(outputMatcher.group(2));

                if (Math.abs(expectedValue - outputValue) > epsilon) {
                    System.out.println("Wrong value " + outputValue + " expected " + expectedValue);
                }

            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

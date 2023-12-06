import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day1Test {
    @Test
    public void testSample() throws IOException {
        try (var in = getClass().getResourceAsStream("day1_sample.txt")) {
            assertEquals(142, Day1.processInput(in));
        }
    }

    @Test
    public void testSampleAlt() throws IOException {
        try (var in = getClass().getResourceAsStream("day1_sample_alt.txt")) {
            assertEquals(281, Day1.processInputAlt(in));
        }
    }

    @Test
    public void testInput() throws IOException {
        try (var in = getClass().getResourceAsStream("day1_input.txt")) {
            System.out.println(Day1.processInput(in));
        }

        try (var in = getClass().getResourceAsStream("day1_input.txt")) {
            System.out.println(Day1.processInputAlt(in));
        }
    }
}

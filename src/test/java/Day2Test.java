import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day2Test {
    @Test
    public void testSample() throws IOException {
        try (var in = getClass().getResourceAsStream("day2_sample.txt")) {
            assertEquals(8, Day2.processInput(in, 12, 13, 14));
        }
    }

    @Test
    public void testSampleAlt() throws IOException {
        try (var in = getClass().getResourceAsStream("day2_sample.txt")) {
            assertEquals(2286, Day2.processInputAlt(in));
        }
    }

    @Test
    public void testInput() throws IOException {
        try (var in = getClass().getResourceAsStream("day2_input.txt")) {
            System.out.println(Day2.processInput(in, 12, 13, 14));
        }

        try (var in = getClass().getResourceAsStream("day2_input.txt")) {
            System.out.println(Day2.processInputAlt(in));
        }
    }
}

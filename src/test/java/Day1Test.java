import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day1Test {
    @Test
    public void testSample() throws IOException {
        assertEquals(142, Day1.processInput(Util.read("day1_sample.txt")));
        assertEquals(281, Day1.processInputAlt(Util.read("day1_sample_alt.txt")));
    }

    @Test
    public void testInput() throws IOException {
        List<String> input = Util.read("day1_input.txt");
        System.out.println(Day1.processInput(input));
        System.out.println(Day1.processInputAlt(input));
    }
}

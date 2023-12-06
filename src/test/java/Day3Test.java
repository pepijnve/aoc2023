import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day3Test {
    @Test
    public void testSample() throws IOException {
        assertEquals(4361, Day3.processInput(Util.read("day3_sample.txt")));
        assertEquals(467835, Day3.processInputAlt(Util.read("day3_sample.txt")));
    }

    @Test
    public void testInput() throws IOException {
        List<String> lines = Util.read("day3_input.txt");
        System.out.println(Day3.processInput(lines));
        System.out.println(Day3.processInputAlt(lines));
    }
}

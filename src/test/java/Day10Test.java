import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Test {
    @Test
    public void testSample() throws IOException {
        assertEquals(8, Day10.processInput(Util.read("day10_sample.txt")));
        assertEquals(10, Day10.processInputAlt(Util.read("day10_sample_alt.txt")));
    }

    @Test
    public void testInput() throws IOException {
        List<String> lines = Util.read("day10_input.txt");
        System.out.println(Day10.processInput(lines));
        System.out.println(Day10.processInputAlt(lines));
    }
}

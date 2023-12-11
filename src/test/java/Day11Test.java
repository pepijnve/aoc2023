import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Test {
    @Test
    public void testSample() throws IOException {
        List<String> lines = Util.read("day11_sample.txt");
        assertEquals(374, Day11.processInput(lines, 2));
        assertEquals(1030, Day11.processInput(lines, 10));
        assertEquals(8410, Day11.processInput(lines, 100));
    }

    @Test
    public void testInput() throws IOException {
        List<String> lines = Util.read("day11_input.txt");
        System.out.println(Day11.processInput(lines, 1));
        System.out.println(Day11.processInput(lines, 1_000_000));
    }
}

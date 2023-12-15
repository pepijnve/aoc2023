import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Test {
    @Test
    public void testSample() throws IOException {
        List<String> lines = Util.read("day12_sample.txt");
        assertEquals(21, Day12.processInput(lines, 1));
        assertEquals(525152, Day12.processInput(lines, 5));
    }

    @Test
    public void testInput() throws IOException {
        List<String> lines = Util.read("day12_input.txt");
//        System.out.println(Day12.processInput(lines, 1));
        System.out.println(Day12.processInput(lines, 5));
    }
}

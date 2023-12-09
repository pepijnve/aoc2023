import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day6Test {
    @Test
    public void testSample() throws IOException {
        assertEquals(288, Day6.processInput(Util.read("day6_sample.txt")));
        assertEquals(71503, Day6.processInputAlt(Util.read("day6_sample.txt")));
    }

    @Test
    public void testInput() throws IOException {
        List<String> lines = Util.read("day6_input.txt");
        System.out.println(Day6.processInput(lines));
        System.out.println(Day6.processInputAlt(lines));
    }
}

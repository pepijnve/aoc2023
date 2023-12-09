import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day9Test {
    @Test
    public void testSample() throws IOException {
        assertEquals(114, Day9.processInput(Util.read("day9_sample.txt")));
        assertEquals(2, Day9.processInputAlt(Util.read("day9_sample.txt")));
    }

    @Test
    public void testInput() throws IOException {
        List<String> lines = Util.read("day9_input.txt");
        System.out.println(Day9.processInput(lines));
        System.out.println(Day9.processInputAlt(lines));
    }
}

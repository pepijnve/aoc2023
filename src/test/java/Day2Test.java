import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day2Test {
    @Test
    public void testSample() throws IOException {
        assertEquals(8, Day2.processInput(Util.read("day2_sample.txt"), 12, 13, 14));
        assertEquals(2286, Day2.processInputAlt(Util.read("day2_sample.txt")));
    }

    @Test
    public void testInput() throws IOException {
        List<String> lines = Util.read("day2_input.txt");
        System.out.println(Day2.processInput(lines, 12, 13, 14));
        System.out.println(Day2.processInputAlt(lines));
    }
}

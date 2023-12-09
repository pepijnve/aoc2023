import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day8Test {
    @Test
    public void testSample() throws IOException {
        assertEquals(2, Day8.processInput(Util.read("day8_sample.txt")));
        assertEquals(6, Day8.processInputAlt(Util.read("day8_sample_alt.txt")));
    }

    @Test
    public void testInput() throws IOException {
        List<String> lines = Util.read("day8_input.txt");
        System.out.println(Day8.processInput(lines));
        System.out.println(Day8.processInputAlt(lines));
    }
}

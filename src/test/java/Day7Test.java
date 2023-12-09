import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day7Test {
    @Test
    public void testSample() throws IOException {
        assertEquals(6440, Day7.processInput(Util.read("day7_sample.txt")));
        assertEquals(5905, Day7.processInputAlt(Util.read("day7_sample.txt")));
    }

    @Test
    public void testInput() throws IOException {
        List<String> lines = Util.read("day7_input.txt");
        System.out.println(Day7.processInput(lines));
        System.out.println(Day7.processInputAlt(lines));
    }
}

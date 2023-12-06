import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day5Test {
    @Test
    public void testSample() throws IOException {
        assertEquals(35, Day5.processInput(Util.read("day5_sample.txt")));
        assertEquals(46, Day5.processInputAlt(Util.read("day5_sample.txt")));
    }

    @Test
    public void testInput() throws IOException {
        List<String> lines = Util.read("day5_input.txt");
        System.out.println(Day5.processInput(lines));
        System.out.println(Day5.processInputAlt(lines));
    }
}

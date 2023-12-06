import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day4Test {
    @Test
    public void testSample() throws IOException {
        assertEquals(13, Day4.processInput(Util.read("day4_sample.txt")));
        assertEquals(30, Day4.processInputAlt(Util.read("day4_sample.txt")));
    }

    @Test
    public void testInput() throws IOException {
        List<String> lines = Util.read("day4_input.txt");
        System.out.println(Day4.processInput(lines));
        System.out.println(Day4.processInputAlt(lines));
    }
}

import java.util.*;

public class Day9 {
    public static long processInput(List<String> lines) {
        long sum = 0L;
        for (String line : lines) {
            String[] values = line.split(" ");
            long[] input = Arrays.stream(values).mapToLong(Long::parseLong).toArray();
            long extrapolated = extrapolateForward(input);
            sum += extrapolated;
        }
        return sum;
    }

    private static long extrapolateForward(long[] input) {
        if (input.length == 1 || Arrays.stream(input).skip(1).allMatch(d -> d == input[0])) {
            return input[0];
        }

        long[] deltas = new long[input.length - 1];
        for (int i = 0; i < deltas.length; i++) {
            deltas[i] = input[i + 1] - input[i];
        }

        return input[input.length - 1] + extrapolateForward(deltas);
    }

    public static long processInputAlt(List<String> lines) {
        long sum = 0L;
        for (String line : lines) {
            String[] values = line.split(" ");
            long[] input = Arrays.stream(values).mapToLong(Long::parseLong).toArray();
            long extrapolated = extrapolateBackward(input);
            sum += extrapolated;
        }
        return sum;
    }

    private static long extrapolateBackward(long[] input) {
        if (input.length == 1 || Arrays.stream(input).skip(1).allMatch(d -> d == input[0])) {
            return input[0];
        }

        long[] deltas = new long[input.length - 1];
        for (int i = 0; i < deltas.length; i++) {
            deltas[i] = input[i + 1] - input[i];
        }

        return input[0] - extrapolateBackward(deltas);
    }
}

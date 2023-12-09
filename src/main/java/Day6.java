import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day6 {
    public static long processInput(List<String> lines) {
        int result = 1;

        List<Integer> maxTimes = parseNumbers(lines.get(0));
        List<Integer> records = parseNumbers(lines.get(1));
        for (int i = 0; i < maxTimes.size(); i++) {
            Integer maxTime = maxTimes.get(i);
            Integer record = records.get(i);
            int waysToWin = numberOfWaysToWin(maxTime, record);
            result *= waysToWin;
        }

        return result;
    }

    public static long processInputAlt(List<String> lines) {
        List<Integer> maxTimes = parseNumbers(lines.get(0));
        List<Integer> records = parseNumbers(lines.get(1));
        long maxTime = Long.parseLong(maxTimes.stream().map(Objects::toString).collect(Collectors.joining()));
        long record = Long.parseLong(records.stream().map(Objects::toString).collect(Collectors.joining()));
        return numberOfWaysToWin(maxTime, record);
    }

    private static List<Integer> parseNumbers(String string) {
        Pattern pattern = Pattern.compile("[0-9]+");
        List<Integer> times = new ArrayList<>();
        Matcher timeMatcher = pattern.matcher(string);
        while(timeMatcher.find()) {
            times.add(Integer.parseInt(timeMatcher.group()));
        }
        return times;
    }

    private static int numberOfWaysToWin(double maxTime, double record) {
        double a = -1.0;
        double b = maxTime;
        double c = -record;

        double d = b * b - 4 * a * c;
        double sqrtD = Math.sqrt(d);
        double twoA = 2.0 * a;
        double x1 = (-b - sqrtD) / twoA;
        double x2 = (-b + sqrtD) / twoA;
        double minDouble = Math.min(x1, x2);
        double maxDouble = Math.max(x1, x2);
        int min = (int)Math.ceil(minDouble);
        if (min == minDouble) {
            min++;
        }
        int max = (int)Math.floor(maxDouble);
        if (max == maxDouble) {
            max--;
        }
        if (max < min) {
            return 0;
        } else {
            return max - min + 1;
        }
    }
}

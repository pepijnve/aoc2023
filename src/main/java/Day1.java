import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1 {
    public static int processInput(List<String> lines) throws IOException {
        return processInput(lines, "[0-9]");
    }

    public static int processInputAlt(List<String> lines) throws IOException {
        return processInput(lines, "[0-9]|one|two|three|four|five|six|seven|eight|nine");
    }

    private static int processInput(List<String> lines, String regex) throws IOException {
        int value = 0;
        Pattern pattern = Pattern.compile(regex);
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) {
                continue;
            }
            int firstStart = matcher.start();
            int firstEnd = matcher.end();
            String first = getValue(matcher);

            int lastStart = firstStart;
            int lastEnd = firstEnd;
            String last = first;
            while(matcher.find(lastStart + 1)) {
                lastStart = matcher.start();
                lastEnd = matcher.end();
                last = getValue(matcher);
            }

            if (firstStart != lastStart) {
                System.out.printf(
                        "%s %s %s %s %s",
                        line.substring(0, firstStart),
                        line.substring(firstStart, firstEnd),
                        line.substring(firstEnd, lastStart),
                        line.substring(lastStart, lastEnd),
                        line.substring(lastEnd)
                );
            } else {
                System.out.printf(
                        "%s %s %s",
                        line.substring(0, firstStart),
                        line.substring(firstStart, firstEnd),
                        line.substring(firstEnd)
                );
            }

            int calibrationValue = Integer.parseInt(first + last);
            System.out.println(" -> " + calibrationValue);
            value += calibrationValue;
        }
        return value;
    }

    private static String getValue(Matcher matcher) {
        String match = matcher.group();
        return switch (match) {
            case "one" -> "1";
            case "two" -> "2";
            case "three" -> "3";
            case "four" -> "4";
            case "five" -> "5";
            case "six" -> "6";
            case "seven" -> "7";
            case "eight" -> "8";
            case "nine" -> "9";
            default -> {
                if (match.length() > 1 || !Character.isDigit(match.charAt(0))) {
                    throw new IllegalArgumentException(match);
                }
                yield match;
            }
        };
    }
}

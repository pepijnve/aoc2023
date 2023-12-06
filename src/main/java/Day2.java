import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {
    public static int processInput(List<String> lines, int redAvailable, int greenAvailable, int blueAvailable) throws IOException {
        int value = 0;
        for (String line : lines) {
            Game g = parseGame(line);

            if (g.red() > redAvailable || g.green() > greenAvailable || g.blue() > blueAvailable) {
                value += g.id();
            }
        }
        return value;
    }

    public static int processInputAlt(List<String> lines) throws IOException {
        int value = 0;
        for (String line : lines) {
            Game g = parseGame(line);

            int power = g.red() * g.green() * g.blue();
            value += power;
        }
        return value;
    }

    private static Game parseGame(String line) {
        String[] idDraws = line.substring(5).split(":", 2);
        int gameId = Integer.parseInt(idDraws[0]);

        int red = 0;
        int green = 0;
        int blue = 0;
        String[] draws = idDraws[1].split(";");
        for (String draw : draws) {
            String[] dice = draw.split(",");
            for (String die : dice) {
                String[] amountColor = die.trim().split(" ", 2);
                int amount = Integer.parseInt(amountColor[0]);
                switch (amountColor[1].trim()) {
                    case "red" -> red = Math.max(red, amount);
                    case "green" -> green = Math.max(green, amount);
                    case "blue" -> blue = Math.max(blue, amount);
                    default -> throw new IllegalArgumentException(die);
                }
            }
        }

        Game g = new Game(gameId, red, green, blue);
        return g;
    }

    private record Game(int id, int red, int green, int blue) {

    }
}

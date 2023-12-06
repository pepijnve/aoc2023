import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {
    public static int processInput(List<String> lines) {
        List<Card> cards = parseCards(lines);
        return cards.stream().mapToInt(Card::score).sum();
    }

    public static int processInputAlt(List<String> lines) {
        List<Card> cards = parseCards(lines);
        int[] copies = new int[cards.size()];
        Arrays.fill(copies, 1);
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            int copiesOfCard = copies[i];
            int matchingNumbers = card.matchingNumbers();
            for (int j = 1; j <= matchingNumbers && j < copies.length; j++) {
                copies[i + j] += copiesOfCard;
            }
        }

        return Arrays.stream(copies).sum();
    }

    private static List<Card> parseCards(List<String> lines) {
        List<Card> cards = new ArrayList<>();

        Pattern pattern = Pattern.compile("Card\\s+([0-9]+):\\s+(.*)\\s+\\|\\s+(.*)");
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.matches()) {
                throw new IllegalArgumentException(line);
            }

            Set<Integer> winning = parseNumbers(matcher.group(2));
            Set<Integer> have = parseNumbers(matcher.group(3));
            Card card = new Card(Integer.parseInt(matcher.group(1)), winning, have);
            cards.add(card);
        }
        return cards;
    }

    private static Set<Integer> parseNumbers(String s) {
        Set<Integer> numbers = new HashSet<>();
        for (String number : s.split("\\s+")) {
            numbers.add(Integer.parseInt(number));
        }
        return numbers;
    }

    private record Card(int id, Set<Integer> winning, Set<Integer> have) {
        public int matchingNumbers() {
            return have.stream().mapToInt(h -> winning.contains(h) ? 1 : 0).sum();
        }

        public int score() {
            int matchingNumbers = matchingNumbers();
            return matchingNumbers == 0 ? 0 : (int)Math.pow(2, matchingNumbers - 1);
        }
    }
}

import java.util.*;

public class Day7 {
    public static long processInput(List<String> lines) {
        return processInput(lines, false);
    }

    public static long processInputAlt(List<String> lines) {
        return processInput(lines, true);
    }

    private static long processInput(List<String> lines, boolean useJoker) {
        List<Hand> hands = new ArrayList<>();
        for (String line : lines) {
            String[] cardsBid = line.split(" ");
            hands.add(parseHand(cardsBid[0], Integer.parseInt(cardsBid[1]), useJoker));
        }

        hands.sort(Hand::compareTo);

        long total = 0L;
        for (int i = 0; i < hands.size(); i++) {
            Hand hand = hands.get(i);
            total += hand.bid * (i + 1);
        }

        return total;
    }

    private static final char[] BASE_CARDS = new char[] {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
    private static final char[] JOKER_CARDS = new char[] {'J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A'};

    private static Hand parseHand(String hand, int bid, boolean useJoker) {
        char[] cardNames = useJoker ? JOKER_CARDS : BASE_CARDS;
        int[] cardCount = new int[13];
        int[] cards = new int[5];
        char[] cardChars = hand.toCharArray();
        for (int i = 0; i < cardChars.length; i++) {
            char c = cardChars[i];
            int card = -1;
            for (int j = 0; j < cardNames.length; j++) {
                char cardName = cardNames[j];
                if (cardName == c) {
                    card = j;
                    break;
                }
            }
            cards[i] = card;
            cardCount[card]++;
        }

        int five = -1;
        int four = -1;
        int three = -1;
        int two1 = -1;
        int two2 = -1;
        for (int cardIndex = 0; cardIndex < cardCount.length; cardIndex++) {
            int count = cardCount[cardIndex];
            switch (count) {
                case 5 -> five = cardIndex;
                case 4 -> four = cardIndex;
                case 3 -> three = cardIndex;
                case 2 -> {
                    if (two1 == -1) {
                        two1 = cardIndex;
                    } else {
                        two2 = cardIndex;
                    }
                }
            }
        }

        int jokers = useJoker ? cardCount[0] : 0;

        Type type;
        if (five != -1 || (four != -1 && jokers == 1) || (three != -1 && jokers == 2) || (two1 != -1 && jokers == 3) || (jokers == 4)) {
            type = Type.FIVE_OF_A_KIND;
        } else if (four != -1 || (three != -1 && jokers == 1) || ((two1 > 0 || two2 > 0) && jokers == 2) || (jokers == 3)) {
            type = Type.FOUR_OF_A_KIND;
        } else if (three != -1 && two1 != -1 || (two1 != -1 && two2 != -1 && jokers == 1)) {
            type = Type.FULL_HOUSE;
        } else if (three != -1 || (two1 != -1 && jokers == 1) || jokers == 2) {
            type = Type.THREE_OF_A_KIND;
        } else if (two1 != -1 && two2 != -1) {
            type = Type.TWO_PAIR;
        } else if (two1 != -1 || jokers == 1) {
            type = Type.ONE_PAIR;
        } else {
            type = Type.HIGH_CARD;
        }

        return new Hand(hand, type, cards, bid);
    }

    private record Hand(String cards, Type rank, int[] unsortedCardNumbers, int bid) implements Comparable<Hand> {
        @Override
        public int compareTo(Hand o) {
            int rankOrder = rank.compareTo(o.rank);
            if (rankOrder != 0) {
                return rankOrder;
            }
            return Arrays.compare(unsortedCardNumbers, o.unsortedCardNumbers);
        }
    }

    private enum Type {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND
    }
}

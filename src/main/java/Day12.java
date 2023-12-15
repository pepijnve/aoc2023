import java.util.*;

public class Day12 {
    public static long processInput(List<String> lines, int repeat) {
        long total = 0;
        for (String line : lines) {
            String[] conditionGroups = line.split(" ", 2);
            StringBuilder conditionBuilder = new StringBuilder();
            StringBuilder groupsBuilder = new StringBuilder();
            for (int i = 0; i < repeat; i++) {
                if (i > 0) {
                    conditionBuilder.append('?');
                    groupsBuilder.append(',');
                }
                conditionBuilder.append(conditionGroups[0]);
                groupsBuilder.append(conditionGroups[1]);
            }

            String condition = conditionBuilder.toString();
            String groups = groupsBuilder.toString();

            int[] groupSizes = Arrays.stream(groups.split(",")).mapToInt(Integer::parseInt).toArray();
            int totalDamaged = Arrays.stream(groupSizes).sum();
            int requiredSpace = totalDamaged + groupSizes.length - 1;
            System.out.print(condition + " " + groups);
            System.out.flush();
            long position = countPosition(condition.toCharArray(), 0, groupSizes, 0, totalDamaged, requiredSpace, new HashMap<>());
            System.out.println(" -> " + position);
            total += position;
        }
        return total;
    }

    private static long countPosition(char[] condition, int conditionOffset, int[] groups, int groupIndex, int totalDamaged, int requiredSpace, Map<Key, Long> cache) {
        Key key = new Key(conditionOffset, groupIndex);
        Long cached = cache.get(key);
        if (cached != null) {
            return cached;
        }

        if (conditionOffset + requiredSpace > condition.length) {
            return 0;
        }

        if (groupIndex >= groups.length) {
            int count = 0;
            for (int i = 0; i < condition.length; i++) {
                if (condition[i] == '#') {
                    count++;
                }
            }
            if (count == totalDamaged) {
                return 1;
            } else {
                return 0;
            }
        }


        long positions = 0;
        int groupSize = groups[groupIndex];
        for (; conditionOffset < condition.length; conditionOffset++) {
            int action = canPlaceDamaged(condition, groupSize, conditionOffset);
            if (action == ABORT) {
                break;
            } else if (action == TRY_NEXT) {
            } else if (action == UPDATE) {
                char[] updatedCondition = placeDamaged(condition, groupSize, conditionOffset);
                positions += countPosition(updatedCondition, conditionOffset + groupSize + 1, groups, groupIndex + 1, totalDamaged, requiredSpace - groupSize - 1, cache);
            } else if (action == NO_UPDATE) {
                positions += countPosition(condition, conditionOffset + groupSize + 1, groups, groupIndex + 1, totalDamaged, requiredSpace - groupSize - 1, cache);
            }
        }

        cache.put(key, positions);
        return positions;
    }

    public static final int ABORT = 0;
    public static final int TRY_NEXT = 1;
    public static final int UPDATE = 2;
    public static final int NO_UPDATE = 3;

    private static int canPlaceDamaged(char[] condition, int groupSize, int offset) {
        int lastOffset = offset + groupSize;
        if (lastOffset > condition.length) {
            return ABORT;
        }

        boolean needsUpdate = false;
        if (offset > 0) {
            char c = condition[offset - 1];
            if (c == '#') {
                return ABORT;
            }
            needsUpdate |= c == '?';
        }
        if (lastOffset < condition.length - 1) {
            char c = condition[lastOffset];
            if (c == '#') {
                return TRY_NEXT;
            }
            needsUpdate |= c == '?';
        }
        for (int i = offset; i < lastOffset; i++) {
            char c = condition[i];
            if (c == '.') {
                return TRY_NEXT;
            }
            needsUpdate |= c == '?';
        }

        return needsUpdate ? UPDATE : NO_UPDATE;
    }

    private static char[] placeDamaged(char[] condition, int groupSize, int offset) {
        int lastOffset = offset + groupSize;

        char[] updated = condition.clone();
        for (int i = offset; i < lastOffset; i++) {
            updated[i] = '#';
        }
        if (offset > 0) {
            updated[offset - 1] = '.';
        }
        if (lastOffset < condition.length - 1) {
            updated[lastOffset] = '.';
        }
        return updated;
    }

    private record Key(int conditionOffset, int groupOffset) {}
}

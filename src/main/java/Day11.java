import java.util.*;

public class Day11 {
    public static long processInput(List<String> lines, long expansionFactor) {
        List<Galaxy> galaxies = new ArrayList<>();

        int width = lines.get(0).length();
        int height = lines.size();

        int gIndex = 1;
        for (int y = 0; y < height; y++) {
            char[] chars = lines.get(y).toCharArray();
            for (int x = 0; x < width; x++) {
                if (chars[x] == '#') {
                    galaxies.add(new Galaxy(gIndex++, x, y));
                }
            }
        }

        galaxies.sort(Comparator.comparing(Galaxy::x));
        for (int x = width - 1; x >= 0; x--) {
            int finalX = x;
            if (galaxies.stream().anyMatch(g -> g.x == finalX)) {
                continue;
            }

            for (int i = galaxies.size() - 1; i >= 0; i--) {
                Galaxy g = galaxies.get(i);
                if (g.x > x) {
                    galaxies.set(i, new Galaxy(g.index, g.x + expansionFactor - 1, g.y));
                } else {
                    break;
                }
            }
        }

        galaxies.sort(Comparator.comparing(Galaxy::y));
        for (int y = height - 1; y >= 0; y--) {
            int finalY = y;
            if (galaxies.stream().anyMatch(g -> g.y == finalY)) {
                continue;
            }

            for (int i = galaxies.size() - 1; i >= 0; i--) {
                Galaxy g = galaxies.get(i);
                if (g.y > y) {
                    galaxies.set(i, new Galaxy(g.index, g.x, g.y + expansionFactor - 1));
                } else {
                    break;
                }
            }
        }

        Set<Pair> pairs = new HashSet<>();
        for (Galaxy g1 : galaxies) {
            for (Galaxy g2 : galaxies) {
                if (g1.equals(g2)) {
                    continue;
                }

                Galaxy min;
                Galaxy max;
                if (g1.x < g2.x) {
                    min = g1;
                    max = g2;
                } else if (g1.x > g2.x) {
                    min = g2;
                    max = g1;
                } else {
                    if (g1.y <= g2.y) {
                        min = g1;
                        max = g2;
                    } else {
                        min = g2;
                        max = g1;
                    }
                }

                pairs.add(new Pair(min, max));
            }
        }

        long totalDistance = 0L;
        for (Pair pair : pairs) {
            totalDistance += Math.abs(pair.g1.x - pair.g2.x) + Math.abs(pair.g1.y - pair.g2.y);
        }

        return totalDistance;
    }

    private record Pair(Galaxy g1, Galaxy g2) {
    }

    private record Galaxy(int index, long x, long y) {
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day10 {
    public static long processInput(List<String> lines) {
        Area area = new Area(new ArrayList<>(lines));
        Point startingPoint = area.find('S');
        area.derivePipe(startingPoint);
        return area.createDistanceMap(startingPoint).longestDistance();
    }

    public static long processInputAlt(List<String> lines) {
        Area area = new Area(new ArrayList<>(lines));
        Point startingPoint = area.find('S');
        area.derivePipe(startingPoint);
        DistanceMap distanceMap = area.createDistanceMap(startingPoint);
        distanceMap.markEnclosed(area);
        return distanceMap.countEnclosed();
    }

    private record Point(int x, int y) {
    }

    private record Area(List<String> lines) {
        public int width() {
            return lines.get(0).length();
        }

        public int height() {
            return lines.size();
        }

        public char get(int x, int y) {
            if (x < 0 || x >= width() || y < 0 || y >= height()) {
                return '.';
            }
            return lines.get(y).charAt(x);
        }

        public char get(Point p) {
            return get(p.x, p.y);
        }

        public Point find(char c) {
            for (int y = 0; y < height(); y++) {
                for (int x = 0; x < width(); x++) {
                    if (get(x, y) == c) {
                        return new Point(x, y);
                    }
                }
            }
            throw new IllegalArgumentException();
        }

        private DistanceMap createDistanceMap(Point startingPoint) {
            int distance = 0;

            DistanceMap map = new DistanceMap(width(), height());
            List<Point> waves = new ArrayList<>();
            waves.add(startingPoint);

            while (true) {
                distance++;
                List<Point> newWaves = new ArrayList<>();

                for (Point p : waves) {
                    map.set(p, distance);

                    int neighbours = getNeighbours(p);

                    if ((neighbours & UP) != 0) {
                        Point up = new Point(p.x, p.y - 1);
                        if (map.get(up) == 0) {
                            newWaves.add(up);
                        }
                    }

                    if ((neighbours & DOWN) != 0) {
                        Point down = new Point(p.x, p.y + 1);
                        if (map.get(down) == 0) {
                            newWaves.add(down);
                        }
                    }

                    if ((neighbours & LEFT) != 0) {
                        Point left = new Point(p.x - 1, p.y);
                        if (map.get(left) == 0) {
                            newWaves.add(left);
                        }
                    }

                    if ((neighbours & RIGHT) != 0) {
                        Point right = new Point(p.x + 1, p.y);
                        if (map.get(right) == 0) {
                            newWaves.add(right);
                        }
                    }
                }

                if (newWaves.isEmpty()) {
                    break;
                } else {
                    waves = newWaves;
                }
            }

            return map;
        }

        private static final int LEFT = 1;
        private static final int RIGHT = 2;
        private static final int UP = 4;
        private static final int DOWN = 8;

        private static final char[] JOIN_TABLE;

        static {
            JOIN_TABLE = new char[16];
            Arrays.fill(JOIN_TABLE, '.');
            JOIN_TABLE[LEFT | UP] = 'J';
            JOIN_TABLE[LEFT | DOWN] = '7';
            JOIN_TABLE[RIGHT | UP] = 'L';
            JOIN_TABLE[RIGHT | DOWN] = 'F';
            JOIN_TABLE[LEFT | RIGHT] = '-';
            JOIN_TABLE[UP | DOWN] = '|';
        }

        public void derivePipe(Point point) {
            int joins = 0;

            char left = get(point.x - 1, point.y);
            if (left == '-' || left == 'F' || left == 'L') joins |= LEFT;
            char right = get(point.x + 1, point.y);
            if (right == '-' || right == 'J' || right == '7') joins |= RIGHT;
            char up = get(point.x, point.y - 1);
            if (up == '|' || up == 'F' || up == '7') joins |= UP;
            char down = get(point.x, point.y + 1);
            if (down == '|' || down == 'J' || down == 'L') joins |= DOWN;

            char pipe = JOIN_TABLE[joins];

            String line = lines.get(point.y);
            StringBuilder b = new StringBuilder(line);
            b.setCharAt(point.x, pipe);
            line = b.toString();
            lines.set(point.y, line);
        }

        public int getNeighbours(Point point) {
            char pipe = get(point);
            return switch (pipe) {
                case '-' -> LEFT | RIGHT;
                case '|' -> UP | DOWN;
                case 'F' -> RIGHT | DOWN;
                case '7' -> LEFT | DOWN;
                case 'J' -> LEFT | UP;
                case 'L' -> RIGHT | UP;
                case '.' -> 0;
                default -> throw new IllegalArgumentException();
            };
        }
    }

    private record DistanceMap(int[][] map) {
        public DistanceMap(int width, int height) {
            this(new int[height][width]);
        }

        int get(Point p) {
            return get(p.x, p.y);
        }

        int get(int x, int y) {
            return map[y][x];
        }

        void set(Point p, int distance) {
            set(p.x, p.y, distance);
        }

        void set(int x, int y, int distance) {
            map[y][x] = distance;
        }

        public int longestDistance() {
            int longest = 0;
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    int d = map[i][j];
                    if (d > longest) {
                        longest = d;
                    }
                }
            }
            return longest - 1;
        }

        public void markEnclosed(Area area) {
            for (int y = 0; y < map.length; y++) {
                boolean insideAbove = false;
                boolean insideBelow = false;
                for (int x = 0; x < map[y].length; x++) {

                    int dist = map[y][x];
                    if (dist > 0) {
                        char pipe = area.get(x, y);
                        if (pipe == '|') {
                            insideAbove = !insideAbove;
                            insideBelow = !insideBelow;
                        } else if (pipe == 'F' || pipe == '7') {
                            insideBelow = !insideBelow;
                        } else if (pipe == 'J' || pipe == 'L') {
                            insideAbove = !insideAbove;
                        }
                    } else {
                        if (insideAbove && insideBelow) {
                            set(x, y, -1);
                        }
                    }
                }
            }
        }

        public long countEnclosed() {
            long enclosed = 0L;
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[y].length; x++) {
                    if (get(x, y) < 0) {
                        enclosed++;
                    }
                }
            }
            return enclosed;
        }
    }
}

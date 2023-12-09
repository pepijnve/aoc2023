import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day8 {
    public static long processInput(List<String> lines) {
        Network network = parseNetwork(lines);
        return findPath(network.map().get("AAA"), network.steps(), "ZZZ"::equals).stepCount();
    }

    public static long processInputAlt(List<String> lines) {
        Network network = parseNetwork(lines);
        Set<Node> startNodes = network.map().values().stream().filter(n -> n.name.endsWith("A")).collect(Collectors.toSet());
        Set<Integer> periods = new HashSet<>();
        for (Node startNode : startNodes) {
            Result result = findPath(startNode, network.steps(), name -> name.endsWith("Z"));
            int period = result.stepCount();
            Result nextResult = findPath(result.endNode(), network.steps(), name -> name.endsWith("Z"));
            if (nextResult.endNode() != result.endNode() || nextResult.stepCount() != result.stepCount()) {
                throw new IllegalArgumentException("Not periodic");
            } else {
                periods.add(period);
            }
        }

        int[] p = periods.stream().mapToInt(Integer::intValue).toArray();
        long lcm = p[0];
        for (int i = 1; i < p.length; i++) {
            lcm = lcm(lcm, p[i]);
        }

        return lcm;
    }

    public static long lcm(long a, long b) {
        return ((a * b) / gcd(a, b));
    }

    public static long gcd(long a, long b) {
        if (a < b) return gcd(b, a);
        if (a % b == 0) return b;
        else return gcd(b, a % b);
    }

    private static Network parseNetwork(List<String> lines) {
        String steps = lines.get(0);
        Map<String, Node> nodes = new HashMap<>();
        Pattern edgePattern = Pattern.compile("([A-Z0-9]+) = \\(([A-Z0-9]+), ([A-Z0-9]+)\\)");
        for (String edge : lines.subList(2, lines.size())) {
            Matcher matcher = edgePattern.matcher(edge);
            if (!matcher.matches()) {
                throw new IllegalArgumentException(edge);
            }
            Node node = nodes.computeIfAbsent(matcher.group(1), Node::new);
            node.left = nodes.computeIfAbsent(matcher.group(2), Node::new);
            node.right = nodes.computeIfAbsent(matcher.group(3), Node::new);
        }

        return new Network(steps, nodes);
    }

    private static Result findPath(Node start, String steps, Predicate<String> endNode) {
        Node current = start;
        int stepCount = 0;

        do {
            char step = steps.charAt(stepCount % steps.length());
            stepCount++;
            current = step == 'L' ? current.left : current.right;
        } while (!endNode.test(current.name));

        return new Result(stepCount, current);
    }

    private record Network(String steps, Map<String, Node> map) {
    }

    private record Result(int stepCount, Node endNode) {

    }

    private static class Node {
        private final String name;
        private Node left;
        private Node right;

        public Node(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}

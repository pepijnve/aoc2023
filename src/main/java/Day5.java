import java.util.*;
import java.util.function.Function;
import java.util.function.LongUnaryOperator;
import java.util.function.ToLongFunction;

public class Day5 {
    public static long processInput(List<String> lines) {
        String seeds = lines.get(0);
        long[] seedNumbers = Arrays.stream(seeds.split(": ")[1].split(" ")).mapToLong(Long::parseLong).toArray();
        List<Range> seedRanges = Arrays.stream(seedNumbers)
                .mapToObj(seed -> new Range(seed, seed))
                .toList();

        return findMinLocation(lines, seedRanges);
    }

    public static long processInputAlt(List<String> lines) {
        String seeds = lines.get(0);
        long[] seedNumbers = Arrays.stream(seeds.split(": ")[1].split(" ")).mapToLong(Long::parseLong).toArray();
        List<Range> seedRanges = new ArrayList<>();
        for (int i = 0; i < seedNumbers.length; i+=2) {
            long seedNumber = seedNumbers[i];
            long range = seedNumbers[i + 1];
            seedRanges.add(new Range(seedNumber, seedNumber + range - 1));
        }

        return findMinLocation(lines, seedRanges);
    }

    private static long findMinLocation(List<String> lines, List<Range> seedRanges) {
        Iterator<String> lineIterator = lines.subList(2, lines.size()).iterator();
        Mapper seedToSoil = parseMap(lineIterator);
        Mapper soilToFertilizer = parseMap(lineIterator);
        Mapper fertilizerToWater = parseMap(lineIterator);
        Mapper waterToLight = parseMap(lineIterator);
        Mapper lightToTemperature = parseMap(lineIterator);
        Mapper temperatureToHumidity = parseMap(lineIterator);
        Mapper humidityToLocation = parseMap(lineIterator);

        ToLongFunction<Range> seedToLocation = seed -> {
            List<Range> soil = seedToSoil.apply(List.of(seed));
            List<Range> fertilizer = soilToFertilizer.apply(soil);
            List<Range> water = fertilizerToWater.apply(fertilizer);
            List<Range> light = waterToLight.apply(water);
            List<Range> temperature = lightToTemperature.apply(light);
            List<Range> humidity = temperatureToHumidity.apply(temperature);
            List<Range> location = humidityToLocation.apply(humidity);
            return location.stream().mapToLong(Range::first).min().orElse(Long.MAX_VALUE);
        };
        return seedRanges.stream()
                .mapToLong(seedToLocation)
                .min()
                .orElse(0);
    }

    private static Mapper parseMap(Iterator<String> lineIterator) {
        String name = lineIterator.next();
        List<MapperEntry> entries = new ArrayList<>();
        String line;
        while (lineIterator.hasNext() && !(line = lineIterator.next()).isEmpty()) {
            long[] values = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
            long destStart = values[0];
            long sourceStart = values[1];
            long range = values[2];
            entries.add(new MapperEntry(new Range(sourceStart, sourceStart + range - 1), new Range(destStart, destStart + range - 1)));
        }
        entries.sort(Comparator.comparing(e -> e.sourceRange().first()));
        return new Mapper(name, entries);
    }

    private record Mapper(String name, List<MapperEntry> entries) implements Function<List<Range>, List<Range>> {
        @Override
        public List<Range> apply(List<Range> ranges) {
            LinkedList<Range> sourceRangesToProcess = new LinkedList<>(ranges);
            List<Range> destinationRanges = new ArrayList<>();

            while (!sourceRangesToProcess.isEmpty()) {
                Range sourceRange = sourceRangesToProcess.removeFirst();
                MapperEntry overlappingEntry = null;
                for (MapperEntry entry : entries) {
                    if (entry.sourceRange().overlaps(sourceRange)) {
                        overlappingEntry = entry;
                        break;
                    }
                }

                if (overlappingEntry == null) {
                    destinationRanges.add(sourceRange);
                    continue;
                }

                Range overlappingSource = overlappingEntry.sourceRange();
                if (overlappingSource.contains(sourceRange)) {
                    destinationRanges.add(new Range(
                            overlappingEntry.applyAsLong(sourceRange.first()),
                            overlappingEntry.applyAsLong(sourceRange.last())
                    ));
                } else if (sourceRange.contains(overlappingSource)) {
                    destinationRanges.add(new Range(
                            overlappingEntry.applyAsLong(overlappingSource.first()),
                            overlappingEntry.applyAsLong(overlappingSource.last())
                    ));
                    Range s1 = new Range(sourceRange.first(), overlappingSource.first() - 1);
                    if (!s1.isEmpty()) {
                        sourceRangesToProcess.add(s1);
                    }
                    Range s2 = new Range(overlappingSource.last() + 1, sourceRange.last());
                    if (!s2.isEmpty()) {
                        sourceRangesToProcess.add(s2);
                    }
                } else if (overlappingSource.contains(sourceRange.first())) {
                    destinationRanges.add(new Range(
                            overlappingEntry.applyAsLong(sourceRange.first()),
                            overlappingEntry.applyAsLong(overlappingSource.last())
                    ));
                    Range s = new Range(overlappingSource.last() + 1, sourceRange.last());
                    if (!s.isEmpty()) {
                        sourceRangesToProcess.add(s);
                    }
                } else if (overlappingSource.contains(sourceRange.last())) {
                    destinationRanges.add(new Range(
                            overlappingEntry.applyAsLong(overlappingSource.first()),
                            overlappingEntry.applyAsLong(sourceRange.last())
                    ));
                    Range s = new Range(sourceRange.first(), overlappingSource.first() - 1);
                    if (!s.isEmpty()) {
                        sourceRangesToProcess.add(s);
                    }
                } else {
                    throw new IllegalStateException();
                }
            }

            return destinationRanges;
        }
    }

    private record MapperEntry(Range sourceRange, Range destRange) implements LongUnaryOperator {
        @Override
        public long applyAsLong(long operand) {
            if (sourceRange().contains(operand)) {
                return operand - sourceRange().first() + destRange().first();
            } else {
                return operand;
            }
        }
    }

    private record Range(long first, long last) {
        boolean contains(long value) {
            return first() <= value && value <= last();
        }

        boolean contains(Range other) {
            return first() <= other.first() && other.last() <= last();
        }

        boolean overlaps(Range other) {
            return first() <= other.last() && last() >= other.first();
        }

        public boolean isEmpty() {
            return last < first;
        }
    }
}

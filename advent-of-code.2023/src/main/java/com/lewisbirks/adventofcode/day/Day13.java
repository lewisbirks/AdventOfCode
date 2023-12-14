package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Day13 extends Day {

    public static void main(String[] args) {
        new Day13().process();
    }

    public Day13() {
        super(13, "Point of Incidence");
    }

    private List<Pattern> patterns;

    @Override
    public void preload() {
        List<String> unprocessed = getInput(Collectors.toCollection(ArrayList::new));

        List<Pattern> patterns = new ArrayList<>();
        while (!unprocessed.isEmpty()) {
            List<String> unprocessedPattern = new ArrayList<>();
            String part;
            while (!unprocessed.isEmpty() && !(part = unprocessed.remove(0)).isEmpty()) {
                unprocessedPattern.add(part);
            }
            patterns.add(Pattern.of(unprocessedPattern));
        }

        this.patterns = Collections.unmodifiableList(patterns);

    }

    @Override
    public Object part1() {
        return patterns.stream().mapToInt(Pattern::summarise).sum();
    }

    @Override
    public Object part2() {
        return patterns.stream().mapToInt(Pattern::summariseWithSmudge).sum();
    }

    record Pattern(char[][] pattern, char[][] rotated) {

        public int summarise() {
            int count = findMirrorIndex(pattern);
            if (count != -1) {
                System.out.println("Found index " + (count));
                return count * 100;
            }
            count = findMirrorIndex(rotated);
            if (count != -1) {
              System.out.println("Found index " + (count));
              return count;
            }
            return 0;
        }

        public int summariseWithSmudge() {
            int count = findMirrorIndexWithSmudge(pattern);
            if (count != -1) {
                System.out.println("Found row index " + count);
                return count * 100;
            }
            count = findMirrorIndexWithSmudge(rotated);
            if (count != -1) {
              System.out.println("Found column index " + count);
              return count;
            }
            return 0;
        }

        private int findMirrorIndex(char[][] pattern) {
            for (int i = 0; i < pattern.length - 1; i++) {
              if (isMirror(pattern, i)) {
                    return i + 1;
                }
            }
            return -1;
        }

        private int findMirrorIndexWithSmudge(char[][] pattern) {
            return -1;
        }

      private static boolean isMirror(char[][] pattern, int index) {
          if (!Arrays.equals(pattern[index], pattern[index + 1])) {
            return false;
          }
          // now count back until the end of one of the side making sure each side is equal
          for (int j = index - 1, k = index + 2; j >= 0 && k < pattern.length; j--, k++) {
              if (!Arrays.equals(pattern[j], pattern[k])) {
                  return false;
              }
          }
          return true;
        }

        public static Pattern of(List<String> strings) {
            char[][] pattern = strings.stream().map(String::toCharArray).toArray(char[][]::new);
            char[][] rotated = new char[pattern[0].length][pattern.length];

            for (int i = 0; i < pattern.length; i++) {
                for (int j = 0; j < pattern[i].length; j++) {
                    rotated[j][i] = pattern[i][j];
                }
            }

            return new Pattern(pattern, rotated);
        }

        @Override
        public String toString() {
            String pattern = Arrays.stream(this.pattern).map(String::valueOf).collect(Collectors.joining("\n"));
            String rotated = Arrays.stream(this.rotated).map(String::valueOf).collect(Collectors.joining("\n"));

            pattern = "pattern:\n" + pattern.indent(2);
            rotated = "rotated:\n" + rotated.indent(2);

            return pattern + "\n" + rotated;
        }
    }
}

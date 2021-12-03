package git.lewisbirks.adventofcode.year21.day;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Day3 extends DayOf2021 {

  private List<String> diagnostics;
  private List<List<Character>> transformedDiagnostics;

  public Day3() {
    super(3);
  }

  @Override
  protected Object part1() {
    readDiagnostics();
    long gamma = 0;
    long epsilon = 0;
    for (List<Character> characters : transformedDiagnostics) {
      int num0 = Collections.frequency(characters, '0');
      int num1 = Collections.frequency(characters, '1');

      if (num0 > num1) {
        gamma = gamma << 1;
        epsilon = (epsilon << 1) | 1;
      } else {
        gamma = (gamma << 1) | 1;
        epsilon = epsilon << 1;
      }
    }
    return gamma * epsilon;
  }

  @Override
  protected Object part2() {
    readDiagnostics();
    BiPredicate<Integer, Integer> moreZeros = (num0, num1) -> num0 > num1;
    long oxygen = processLifeSupportSubsystem(moreZeros);
    long co2 = processLifeSupportSubsystem(moreZeros.negate());
    return oxygen * co2;
  }

  private long processLifeSupportSubsystem(BiPredicate<Integer, Integer> comparator) {
    List<List<Character>> diagnosticsReports = new ArrayList<>(transformedDiagnostics);
    List<String> lifeSupportSubsystemDiagnostics = diagnostics;

    int i = 0;
    while (lifeSupportSubsystemDiagnostics.size() > 1) {
      List<Character> characters = diagnosticsReports.get(i);
      int num0 = Collections.frequency(characters, '0');
      int num1 = Collections.frequency(characters, '1');

      char scrubberChar = comparator.test(num0, num1) ? '0' : '1';

      int index = i++;
      lifeSupportSubsystemDiagnostics = lifeSupportSubsystemDiagnostics.stream()
          .filter(diagnostic -> diagnostic.charAt(index) == scrubberChar)
          .collect(Collectors.toList());

      diagnosticsReports = transformDiagnostics(lifeSupportSubsystemDiagnostics);
    }

    return Long.parseLong(lifeSupportSubsystemDiagnostics.get(0), 2);
  }

  private void readDiagnostics() {
    if (diagnostics != null) {
      return;
    }

    List<String> inputs = getInput(Function.identity());
    if (!inputs.isEmpty() && !inputs.stream().map(String::length).allMatch(i -> i == inputs.get(0).length())) {
      throw new IllegalArgumentException("All diagnostics must have the same length");
    }

    diagnostics = Collections.unmodifiableList(inputs);
    transformedDiagnostics = Collections.unmodifiableList(transformDiagnostics(inputs));
  }

  private List<List<Character>> transformDiagnostics(List<String> inputs) {
    List<List<Character>> parsedInputs = new ArrayList<>();
    for (int i = 0; i < inputs.get(0).length(); i++) {
      List<Character> input = new ArrayList<>();
      for (String s : inputs) {
        input.add(s.charAt(i));
      }
      parsedInputs.add(input);
    }
    return parsedInputs;
  }
}

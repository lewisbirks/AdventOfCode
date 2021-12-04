package git.lewisbirks.adventofcode.year20.day;

import git.lewisbirks.adventofcode.year20.utils.PasswordPolicy;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class Day2 extends DayOf2020 {

  private Map<PasswordPolicy, List<String>> policyWithPasswords;

  public Day2() {
    super(2);
  }

  @Override
  protected Object part1() {
    return numValidPasswords(readPasswords(), PasswordPolicy::validPasswordByOccurrence);
  }

  @Override
  protected Object part2() {
    return numValidPasswords(readPasswords(), PasswordPolicy::validPasswordByPosition);
  }

  private long numValidPasswords(Map<PasswordPolicy, List<String>> policyWithPasswords,
                                 BiPredicate<PasswordPolicy, String> validator) {
    return policyWithPasswords.entrySet()
        .stream()
        .mapToLong(entry -> entry.getValue().stream()
            .filter(password -> validator.test(entry.getKey(), password))
            .count())
        .sum();
  }

  private Map<PasswordPolicy, List<String>> readPasswords() {
    policyWithPasswords = Objects.requireNonNullElseGet(policyWithPasswords, () -> getInput(Collectors.groupingBy(
        s -> PasswordPolicy.of(s.split(": ")[0].trim()),
        Collectors.mapping(s -> s.split(": ")[1].trim(), Collectors.toList())
    )));
    return policyWithPasswords;
  }
}

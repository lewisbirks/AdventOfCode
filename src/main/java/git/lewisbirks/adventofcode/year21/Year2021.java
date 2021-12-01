package git.lewisbirks.adventofcode.year21;

import git.lewisbirks.adventofcode.common.Day;
import git.lewisbirks.adventofcode.common.Year;
import git.lewisbirks.adventofcode.year21.day.Day1;

import java.util.List;

public class Year2021 extends Year {

  public Year2021() {
    super(2021);
  }

  private static final List<Day<?,?>> DAYS = List.of(new Day1());

  @Override
  protected List<Day<?, ?>> getDays() {
    return DAYS;
  }
}

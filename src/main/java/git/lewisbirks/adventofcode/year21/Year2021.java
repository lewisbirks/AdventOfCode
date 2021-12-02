package git.lewisbirks.adventofcode.year21;

import git.lewisbirks.adventofcode.common.Day;
import git.lewisbirks.adventofcode.common.Year;
import git.lewisbirks.adventofcode.year21.day.Day1;
import git.lewisbirks.adventofcode.year21.day.Day2;

import java.util.List;

public class Year2021 extends Year {

  private static final List<Day> DAYS = List.of(new Day1(), new Day2());

  public Year2021() {
    super(2021);
  }

  @Override
  protected List<Day> getDays() {
    return DAYS;
  }
}

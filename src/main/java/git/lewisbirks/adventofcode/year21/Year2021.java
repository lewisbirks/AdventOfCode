package git.lewisbirks.adventofcode.year21;

import git.lewisbirks.adventofcode.common.Year;
import git.lewisbirks.adventofcode.year21.day.Day1;
import git.lewisbirks.adventofcode.year21.day.Day2;
import git.lewisbirks.adventofcode.year21.day.DayOf2021;

import java.util.List;

public class Year2021 extends Year<DayOf2021> {

  private static final List<DayOf2021> DAYS = List.of(new Day1(), new Day2());

  public Year2021() {
    super(2021);
  }

  @Override
  protected List<DayOf2021> getDays() {
    return DAYS;
  }
}

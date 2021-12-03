package git.lewisbirks.adventofcode.year20;

import git.lewisbirks.adventofcode.common.Year;
import git.lewisbirks.adventofcode.year20.day.Day1;
import git.lewisbirks.adventofcode.year20.day.DayOf2020;

import java.util.List;

public class Year2020 extends Year<DayOf2020> {

  private static final List<DayOf2020> DAYS = List.of(new Day1());

  public Year2020() {
    super(2020);
  }

  @Override
  protected List<DayOf2020> getDays() {
    return DAYS;
  }
}

package git.lewisbirks.adventofcode;

import git.lewisbirks.adventofcode.day.Day;
import git.lewisbirks.adventofcode.day.Day1;

import java.util.List;

public class DayProcessor {

  private static final List<Day<?,?>> DAYS = List.of(new Day1());

  public static void main(String[] args) {
    DAYS.forEach(Day::process);
  }

}

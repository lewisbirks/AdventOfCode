package git.lewisbirks.adventofcode.common;

import java.util.List;
import java.util.Objects;

public abstract class Year {

  private final int year;

  protected Year(int year) {
    this.year = year;
  }

  public void process() {
    System.out.println("==========================");
    System.out.println("Year " + year);
    System.out.println("==========================");
    List<Day<?,?>> days = getDays();
    Objects.requireNonNull(days, "year must have days");
    days.stream().sorted().forEach(Day::process);
    System.out.println("==========================\n");
  }

  protected abstract List<Day<?, ?>> getDays();
}

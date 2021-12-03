package git.lewisbirks.adventofcode;

import git.lewisbirks.adventofcode.common.Year;
import git.lewisbirks.adventofcode.year20.Year2020;
import git.lewisbirks.adventofcode.year21.Year2021;

import java.util.List;

public class Driver {

  public static final Year2020 YEAR_2020 = new Year2020();
  public static final Year2021 YEAR_2021 = new Year2021();

  public static void main(String[] args) {
    int year = 0;

    if (args.length == 1) {
      year = Integer.parseInt(args[0]);
    }

    List<Year<?>> years = switch (year) {
      case 2020 -> List.of(YEAR_2020);
      case 2021 -> List.of(YEAR_2021);
      default -> List.of(YEAR_2020, YEAR_2021);
    };

    years.stream().sorted().forEach(Year::process);
  }

}

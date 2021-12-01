package git.lewisbirks.adventofcode.day;

import java.io.IOException;

public abstract class Day<I, O> {

  private final int num;

  public Day(int num) {
    this.num = num;
  }

  public void process() {
    O result;
    System.out.println("Day " + num);
    try {
      I input = readInput("day" + num + ".txt");
      result = calculateResultsPart1(input);
      System.out.printf("\tPart 1: %s%n", result);
      result = calculateResultsPart2(input);
      System.out.printf("\tPart 2: %s%n", result);
    } catch (IOException e) {
      System.err.println("Failed to process day " + num);
      e.printStackTrace();
    }
  }

  abstract O calculateResultsPart1(I input);

  abstract O calculateResultsPart2(I input);

  abstract I readInput(String file) throws IOException;

}

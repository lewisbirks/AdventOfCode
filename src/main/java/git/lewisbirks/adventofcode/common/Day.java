package git.lewisbirks.adventofcode.common;

public abstract class Day implements Comparable<Day>{

  private final int num;

  public Day(int num) {
    this.num = num;
  }

  public void process() {
    System.out.printf("Day %02d%n", num);
    try {
      System.out.printf("\tPart 1: %s%n", part1());
      System.out.printf("\tPart 2: %s%n", part2());
    } catch (Exception e) {
      System.err.printf("Failed to process day %02d%n", num);
      e.printStackTrace();
    }
  }

  protected abstract Object part1();

  protected abstract Object part2();

  @Override
  public int compareTo(Day o) {
    return Integer.compare(num, o.num);
  }
}

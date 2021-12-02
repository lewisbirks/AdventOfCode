package git.lewisbirks.adventofcode.year21.utils;

public class Vector {

  public int distance;
  public Direction direction;

  private Vector(int distance, Direction direction) {
    this.distance = distance;
    this.direction = direction;
  }

  public static Vector of(String line) {
    String[] split = line.split(" ");
    Direction direction = Direction.valueOf(split[0].toUpperCase());
    int distance = Integer.parseInt(split[1]);
    return new Vector(distance, direction);
  }
}

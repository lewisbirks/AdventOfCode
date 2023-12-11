package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.coor.Point;
import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.ArrayList;
import java.util.List;

public final class Day11 extends Day {

  public static void main(String[] args) {
    new Day11().process();
  }

  private List<Point> galaxies;

  public Day11() {
    super(11, "Cosmic Expansion");
  }

  @Override
  public void preload() {
    List<String> unprocessed = getInput();

    int rowLength = unprocessed.get(0).length();
    boolean[] columnEmpty = new boolean[rowLength];

    for (int i = 0; i < rowLength; i++) {
      boolean empty = true;
      for (String row : unprocessed) {
        if (row.charAt(i) == '#') {
          empty = false;
          break;
        }
      }
      columnEmpty[i] = empty;
    }

    List<char[]> processed = new ArrayList<>(unprocessed.size());
    for (String row : unprocessed) {
      StringBuilder expanded = new StringBuilder(row.length());
      boolean empty = true;
      for (int i = 0; i < row.length(); i++) {
        char symbol = row.charAt(i);
        expanded.append(symbol);
        if (columnEmpty[i]) {
          expanded.append('.');
        }
        if (empty && symbol == '#') {
          empty = false;
        }
      }

      char[] processedRow = expanded.toString().toCharArray();
      processed.add(processedRow);


      if (!row.contains("#")) {
        processed.add(processedRow);
      }
    }

    char[][] image = processed.toArray(new char[0][]);

    galaxies = new ArrayList<>();

    for (int y = 0; y < image.length; y++) {
      for (int x = 0; x < image[y].length; x++) {
        if (image[y][x] == '#') {
          galaxies.add(new Point(x, y));
        }
      }
    }
  }

  @Override
  public Object part1() {
    long sum = 0;
    Point[] galaxies = this.galaxies.toArray(new Point[0]);
    int numGalaxies = galaxies.length;
    for (int i = 0; i < numGalaxies; i++) {
      Point galaxy = galaxies[i];
      for (int j = i + 1; j < numGalaxies; j++) {
        int distance = galaxy.calculateCartesianDistance(galaxies[j]);
        sum += distance;
      }
    }

    return sum;
  }

  @Override
  public Object part2() {
    return null;
  }
}

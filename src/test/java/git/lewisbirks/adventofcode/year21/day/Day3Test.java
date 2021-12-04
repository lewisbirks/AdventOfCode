package git.lewisbirks.adventofcode.year21.day;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day3Test {

  private Day3 underTest = new Day3();

  @Test
  void part1() {
    assertThat(underTest.part1()).isEqualTo(198L);
  }

  @Test
  void part2() {
    assertThat(underTest.part2()).isEqualTo(230L);
  }
}
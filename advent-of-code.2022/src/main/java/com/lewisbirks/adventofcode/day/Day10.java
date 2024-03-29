package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.List;

public final class Day10 extends Day {

    private static final int PROCESSING_TIME = 2;

    private List<String> instructions;

    public Day10() {
        super(10, "Cathode-Ray Tube");
    }

    @Override
    public void preload() {
        instructions = getInput();
    }

    @Override
    public Object part1() {
        int[] cycleChecks = {20, 60, 100, 140, 180, 220};
        int addition = 0;
        int processing = -1;
        int x = 1;
        long signalStrength = 0;
        int checkIndex = 0;
        for (int i = 0, j = 0; i < 220; i++) {
            if (i + 1 == cycleChecks[checkIndex]) {
                signalStrength += (long) cycleChecks[checkIndex] * x;
                checkIndex++;
            }
            if (processing == -1) {
                String instruction = instructions.get(j++);
                if (instruction.startsWith("add")) {
                    addition = Integer.parseInt(instruction.substring(5));
                    processing = PROCESSING_TIME;
                }
            }
            if (processing != -1) {
                if (--processing == 0) {
                    x += addition;
                    processing = -1;
                }
            }
        }
        return signalStrength;
    }

    @Override
    public Object part2() {
        StringBuilder output = new StringBuilder("\n");
        int addition = 0;
        int processing = -1;
        int x = 1;
        for (int i = 0, j = 0; i < 240; i++) {
            if (processing == -1) {
                String instruction = instructions.get(j++);
                if (instruction.startsWith("add")) {
                    addition = Integer.parseInt(instruction.substring(5));
                    processing = PROCESSING_TIME;
                }
            }

            int pixel = i % 40;
            output.append(x >= pixel - 1 && x <= pixel + 1 ? "#" : ".");

            if (processing != -1) {
                if (--processing == 0) {
                    x += addition;
                    processing = -1;
                }
            }
            if ((i + 1) % 40 == 0) {
                output.append("\n");
            }
        }

        return output.toString();
    }
}

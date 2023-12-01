package com.lewisbirks.adventofcode.model.vectors;

public record Vector(int distance, Direction direction) {
    public static Vector of(String line) {
        String[] split = line.split(" ");
        Direction direction = Direction.valueOf(split[0].toUpperCase());
        int distance = Integer.parseInt(split[1]);
        return new Vector(distance, direction);
    }
}

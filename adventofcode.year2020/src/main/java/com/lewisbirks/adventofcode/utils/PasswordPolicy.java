package com.lewisbirks.adventofcode.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record PasswordPolicy(int min, int max, char character) {
    private static final Pattern PATTERN = Pattern.compile("(?<min>\\d+)-(?<max>\\d+) (?<character>[a-z])");

    public static PasswordPolicy of(String policy) {
        Matcher matcher = PATTERN.matcher(policy);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("'" + policy + "' is not in the correct format");
        }
        int min = Integer.parseInt(matcher.group("min"));
        int max = Integer.parseInt(matcher.group("max"));
        char character = matcher.group("character").charAt(0);
        return new PasswordPolicy(min, max, character);
    }

    public boolean validPasswordByOccurrence(String password) {
        long occurrences = password.chars().filter(c -> c == character).count();
        return occurrences >= min && occurrences <= max;
    }

    public boolean validPasswordByPosition(String password) {
        char first = password.charAt(min - 1);
        char second = password.charAt(max - 1);
        return (first == character || second == character) && (first != second);
    }
}

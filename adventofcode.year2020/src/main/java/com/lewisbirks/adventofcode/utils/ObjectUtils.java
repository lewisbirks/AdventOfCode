package com.lewisbirks.adventofcode.utils;

import java.util.Arrays;
import java.util.Objects;

public class ObjectUtils {
    public static boolean allNonNull(Object... objects) {
        return Arrays.stream(objects).allMatch(Objects::nonNull);
    }
}

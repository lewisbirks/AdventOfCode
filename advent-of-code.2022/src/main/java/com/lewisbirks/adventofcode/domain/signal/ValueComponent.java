package com.lewisbirks.adventofcode.domain.signal;

import java.util.List;

public record ValueComponent(int value) implements Component {
    public ListComponent convert() {
        return new ListComponent(List.of(this));
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

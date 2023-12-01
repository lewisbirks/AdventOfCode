package com.lewisbirks.adventofcode.domain.signal;

import java.util.ArrayList;
import java.util.List;

public record ListComponent(List<Component> values) implements Component {

    public ListComponent() {
        this(new ArrayList<>());
    }

    public void add(Component component) {
        values.add(component);
    }

    @Override
    public String toString() {
        return values.toString();
    }
}

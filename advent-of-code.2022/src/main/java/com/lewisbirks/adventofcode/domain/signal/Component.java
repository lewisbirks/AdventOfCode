package com.lewisbirks.adventofcode.domain.signal;

import java.util.List;

public interface Component extends Comparable<Component> {
    @Override
    default int compareTo(Component other) {
        if (this.getClass().equals(other.getClass())) {
            if (this instanceof ValueComponent) {
                return Integer.compare(((ValueComponent) this).value(), ((ValueComponent) other).value());
            }
            List<Component> thisValues = ((ListComponent) this).values();
            List<Component> otherValues = ((ListComponent) other).values();

            if (!thisValues.isEmpty() && otherValues.isEmpty()) {
                return 1;
            }

            for (int i = 0; i < thisValues.size(); i++) {
                if (i >= otherValues.size()) {
                    return 1;
                }
                int assertion = thisValues.get(i).compareTo(otherValues.get(i));
                if (assertion == 0) {
                    continue;
                }
                return assertion;
            }
            return thisValues.size() < otherValues.size() ? -1 : 0;
        }
        if (this instanceof ListComponent) {
            return compareTo(((ValueComponent) other).convert());
        }
        return ((ValueComponent) this).convert().compareTo(other);
    }
}

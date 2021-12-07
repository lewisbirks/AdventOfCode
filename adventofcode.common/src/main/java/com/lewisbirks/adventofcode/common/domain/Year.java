package com.lewisbirks.adventofcode.common.domain;

import java.util.List;
import java.util.Objects;

public abstract class Year<D extends Day> {

    private final int year;

    public Year(int year) {
        this.year = year;
    }

    public void process() {
        System.out.println("==========================");
        System.out.println("Year " + year);
        System.out.println("==========================");
        Objects.requireNonNull(getDays(), "year must have days").stream().sorted().forEach(Day::process);
        System.out.println("==========================\n");
    }

    protected abstract List<D> getDays();

}

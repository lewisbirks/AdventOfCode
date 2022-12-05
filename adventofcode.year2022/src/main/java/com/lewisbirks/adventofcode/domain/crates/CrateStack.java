package com.lewisbirks.adventofcode.domain.crates;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class CrateStack {
    private ArrayDeque<Character> crates;
    private final ArrayDeque<Character> source;


    CrateStack(ArrayDeque<Character> crates) {
        this.crates = crates;
        this.source = crates.clone();
    }

    public void reset() {
        crates = source.clone();
    }

    public Character pop() {
        return crates.pollLast();
    }

    public List<Character> popBulk(int amount) {
        List<Character> popped = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            popped.add(0, this.pop());
        }
        return popped;
    }
    public void add(Character crate) {
        crates.add(crate);
    }

    public void addAll(List<Character> crates) {
        this.crates.addAll(crates);
    }

    @Override
    public String toString() {
        return "CrateStack{" +
               "crates=" + crates +
               '}';
    }

    public static CrateStackBuilder builder() {
        return new CrateStackBuilder();
    }

    public static class CrateStackBuilder {
        private final ArrayDeque<Character> crates = new ArrayDeque<>();

        private CrateStackBuilder() {
        }

        public CrateStackBuilder add(Character c) {
            crates.addFirst(c);
            return this;
        }

        public CrateStack build() {
            return new CrateStack(crates);
        }
    }
}

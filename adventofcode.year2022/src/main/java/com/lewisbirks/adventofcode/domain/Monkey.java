package com.lewisbirks.adventofcode.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public final class Monkey {

    private final List<Long> startingWorryLevels;
    private final int id;
    private List<Long> worryLevels;
    private final Function<Long, Long> operation;
    private final int test;
    private final int trueId;
    private final int falseId;
    private int itemsInspected;

    public Monkey(int id, List<Long> worryLevels, Function<Long, Long> op, int test,
                  int trueId, int falseId) {
        this.id = id;
        this.worryLevels = new ArrayList<>(worryLevels);
        this.operation = op;
        this.test = test;
        this.trueId = trueId;
        this.falseId = falseId;
        this.startingWorryLevels = Collections.unmodifiableList(worryLevels);
        this.itemsInspected = 0;
    }

    public int id() {return id;}

    public int numberOfWorries() {
        return worryLevels.size();
    }

    public long inspectItem() {
        itemsInspected++;
        return worryLevels.remove(0);
    }

    public int getNumberOfItemsInspected() {
        return itemsInspected;
    }

    public Function<Long, Long> operation() {return operation;}

    public int testDivisor() {return test;}

    public int trueId() {return trueId;}

    public int falseId() {return falseId;}

    public void addWorry(long worry) {
        worryLevels.add(worry);
    }

    public void reset() {
        this.worryLevels = new ArrayList<>(startingWorryLevels);
        this.itemsInspected = 0;
    }

    public static Monkey from(String idLine, String itemsLine, String operationLine, String testLine, String trueLine, String falseLine) {
        int id = Integer.parseInt(idLine.substring(idLine.indexOf(" ") + 1, idLine.indexOf(":")));

        List<Long> items = Arrays.stream(itemsLine.substring(itemsLine.indexOf(":") + 1).split(","))
            .map(String::trim)
            .map(Long::parseLong)
            .toList();

        int space = operationLine.lastIndexOf(" ");
        String s = operationLine.substring(space + 1);
        int operationAmount = s.equals("old") ? -1 : Integer.parseInt(s);
        Function<Long, Long> operation;
        if (operationAmount == -1) {
            operation = i -> i * i;
        } else {
            operation = switch (operationLine.charAt(space - 1)) {
                case '*' -> i -> i * operationAmount;
                case '+' -> i -> i + operationAmount;
                default -> throw new IllegalArgumentException("Unexpected " + operationLine.charAt(space - 1));
            };
        }

        int testDivisor = Integer.parseInt(testLine.substring(testLine.lastIndexOf(" ") + 1));
        int trueMonkey = Integer.parseInt(trueLine.substring(trueLine.lastIndexOf(" ") + 1));
        int falseMonkey = Integer.parseInt(falseLine.substring(falseLine.lastIndexOf(" ") + 1));

        return new Monkey(id, items, operation, testDivisor, trueMonkey, falseMonkey);
    }
}

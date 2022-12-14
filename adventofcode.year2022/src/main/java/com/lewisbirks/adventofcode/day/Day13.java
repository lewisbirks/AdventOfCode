package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.ReferenceInt;
import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.common.tuple.Pair;
import com.lewisbirks.adventofcode.domain.signal.Component;
import com.lewisbirks.adventofcode.domain.signal.ListComponent;
import com.lewisbirks.adventofcode.domain.signal.ValueComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Day13 extends Day {

    private static final ListComponent DIVIDER_2 = new ListComponent(List.of(
        new ListComponent(List.of(new ValueComponent(2)))
    ));
    private static final ListComponent DIVIDER_6 = new ListComponent(List.of(
        new ListComponent(List.of(new ValueComponent(6)))
    ));
    private List<Pair<Component, Component>> pairs;

    public Day13() {
        super(13, "Distress Signal");
    }

    @Override
    protected void preLoad() {
        List<String> lines = getInput().stream()
            .filter(Predicate.not(String::isBlank))
            .collect(Collectors.toCollection(ArrayList::new));

        pairs = new ArrayList<>();

        while (!lines.isEmpty()) {
            String first = lines.remove(0);
            String second = lines.remove(0);

            Component firstLine = parse(first, new ReferenceInt());
            Component secondLine = parse(second, new ReferenceInt());
            pairs.add(new Pair<>(firstLine, secondLine));
        }
    }

    @Override
    protected Object part1() {
        int sum = 0;
        for (int i = 0; i < pairs.size(); i++) {
            Pair<Component, Component> p = pairs.get(i);
            if (p.left().compareTo(p.right()) < 0) {
                sum += (i + 1);
            }
        }
        return sum;
    }

    @Override
    protected Object part2() {
        List<Component> collect = pairs.stream()
            .flatMap(p -> Stream.of(p.left(), p.right()))
            .collect(Collectors.toCollection(ArrayList::new));
        collect.add(DIVIDER_2);
        collect.add(DIVIDER_6);
        collect.sort(Comparator.naturalOrder());
        return (collect.indexOf(DIVIDER_2) + 1) * (collect.indexOf(DIVIDER_6) + 1);
    }

    private Component parse(String line, ReferenceInt index) {
        // character is either a digit or '['
        if (Character.isDigit(line.charAt(index.value))) {
            StringBuilder foundNumber = new StringBuilder();
            do {
                foundNumber.append(line.charAt(index.value));
                index.value++;
            } while (Character.isDigit(line.charAt(index.value)));
            int number = Integer.parseInt(foundNumber.toString());
            return new ValueComponent(number);
        }
        index.value++;

        ListComponent listComponent = new ListComponent();

        while (line.charAt(index.value) != ']') {
            listComponent.add(parse(line, index));
            if (line.charAt(index.value) == ',') {
                index.value++;
            }
        }
        // ]
        index.value++;
        return listComponent;
    }
}

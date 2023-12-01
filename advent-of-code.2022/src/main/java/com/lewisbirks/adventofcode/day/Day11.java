package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.domain.Monkey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class Day11 extends Day {

    private List<Monkey> monkeys;

    Map<Integer, Monkey> monkeysById;

    public Day11() {
        super(11, "Monkey in the Middle");
    }

    @Override
    protected void preLoad() {
        List<String> lines = getInput(String::trim).stream()
            .filter(Predicate.not(String::isBlank))
            .collect(Collectors.toCollection(ArrayList::new));

        monkeys = new ArrayList<>();

        while (!lines.isEmpty()) {
            List<String> monkeyDetails = lines.subList(0, 6);
            monkeys.add(Monkey.from(
                monkeyDetails.get(0), monkeyDetails.get(1), monkeyDetails.get(2), monkeyDetails.get(3),
                monkeyDetails.get(4), monkeyDetails.get(5)
            ));
            monkeyDetails.clear();
        }

        monkeys = Collections.unmodifiableList(monkeys);

        monkeysById = monkeys.stream().collect(Collectors.toMap(Monkey::id, monkey -> monkey));
    }

    @Override
    protected Object part1() {
        Function<Long, Long> div = l -> l / 3;
        for (int i = 0; i < 20; i++) {
            round(div);
        }
        return calculateMonkeyBusiness();
    }

    @Override
    protected Object part2() {
        reset();
        int multiple = monkeys.stream().mapToInt(Monkey::testDivisor).reduce(1, (i, j) -> i * j);
        Function<Long, Long> mod = l -> l % multiple;
        for (int i = 0; i < 10000; i++) {
            round(mod);
        }
        return calculateMonkeyBusiness();
    }

    private void reset() {
        monkeys.forEach(Monkey::reset);
    }

    private Long calculateMonkeyBusiness() {
        return monkeys.stream()
            .map(Monkey::getNumberOfItemsInspected)
            .map(Integer::longValue)
            .sorted(Comparator.reverseOrder())
            .limit(2)
            .reduce(1L, (i, k) -> i * k);
    }

    private void round(Function<Long, Long> reliefManagement) {
        for (Monkey monkey : monkeys) {
            int iterations = monkey.numberOfWorries();
            for (int i = 0; i < iterations; i++) {
                Long worryLevel = monkey.inspectItem();
                long newWorry = monkey.operation().andThen(reliefManagement).apply(worryLevel);
                int id = newWorry % monkey.testDivisor() == 0 ? monkey.trueId() : monkey.falseId();
                monkeysById.get(id).addWorry(newWorry);
            }
        }
    }
}

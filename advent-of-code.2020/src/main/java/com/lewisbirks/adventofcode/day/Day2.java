package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.model.password.PasswordPolicy;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public final class Day2 extends Day {

    private Map<PasswordPolicy, List<String>> policies;

    public Day2() {
        super(2, "Password Philosophy");
    }

    @Override
    public void preload() {
        policies = getInput(Collectors.groupingBy(
                s -> PasswordPolicy.of(s.split(": ")[0].trim()),
                Collectors.mapping(s -> s.split(": ")[1].trim(), Collectors.toList())));
    }

    @Override
    public Object part1() {
        return numValidPasswords(policies, PasswordPolicy::validPasswordByOccurrence);
    }

    @Override
    public Object part2() {
        return numValidPasswords(policies, PasswordPolicy::validPasswordByPosition);
    }

    private long numValidPasswords(
            Map<PasswordPolicy, List<String>> policyWithPasswords, BiPredicate<PasswordPolicy, String> validator) {
        return policyWithPasswords.entrySet().stream()
                .mapToLong(entry -> entry.getValue().stream()
                        .filter(password -> validator.test(entry.getKey(), password))
                        .count())
                .sum();
    }
}

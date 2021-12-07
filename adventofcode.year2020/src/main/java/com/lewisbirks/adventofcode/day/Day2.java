package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.model.PasswordPolicy;

import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class Day2 extends Day {

    private final Supplier<Map<PasswordPolicy, List<String>>> policySupplier;

    public Day2() {
        super(2);
        policySupplier = CachedSupplier.memoize(() -> getInput(Collectors.groupingBy(
            s -> PasswordPolicy.of(s.split(": ")[0].trim()),
            Collectors.mapping(s -> s.split(": ")[1].trim(), Collectors.toList())
        )));
    }

    @Override
    protected Object part1() {
        return numValidPasswords(policySupplier.get(), PasswordPolicy::validPasswordByOccurrence);
    }

    @Override
    protected Object part2() {
        return numValidPasswords(policySupplier.get(), PasswordPolicy::validPasswordByPosition);
    }

    private long numValidPasswords(Map<PasswordPolicy, List<String>> policyWithPasswords,
                                   BiPredicate<PasswordPolicy, String> validator) {
        return policyWithPasswords.entrySet()
            .stream()
            .mapToLong(entry -> entry.getValue().stream()
                .filter(password -> validator.test(entry.getKey(), password))
                .count())
            .sum();
    }
}

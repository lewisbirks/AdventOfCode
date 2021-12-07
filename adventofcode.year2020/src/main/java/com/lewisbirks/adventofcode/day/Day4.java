package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.model.Passport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class Day4 extends Day {

    private final Supplier<List<Passport>> passportsSupplier;

    public Day4() {
        super(4);
        passportsSupplier = CachedSupplier.memoize(() -> {
            List<String> lines = getInput(Collectors.toCollection(ArrayList::new));
            List<Passport> passports = new ArrayList<>();
            while (!lines.isEmpty()) {
                StringBuilder passport = new StringBuilder();
                String line;
                while (!lines.isEmpty() && !(line = lines.remove(0)).isBlank()) {
                    passport.append(line).append(" ");
                }
                passports.add(Passport.of(passport.toString().trim()));
            }
            return Collections.unmodifiableList(passports);
        });
    }

    @Override
    protected Object part1() {
        List<Passport> passports = passportsSupplier.get();
        return passports.stream().filter(Passport::isValid).count();
    }

    @Override
    protected Object part2() {
        List<Passport> passports = passportsSupplier.get();
        return passports.stream().filter(Passport::isStrictValid).count();
    }
}

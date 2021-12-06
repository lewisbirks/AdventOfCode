package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Day4 extends DayOf2020 {

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
        return null;
    }

    public static final class Passport {
        private String birthYear;
        private String issueYear;
        private String expirationYear;
        private String height;
        private String hairColour;
        private String eyeColour;
        private String passportId;

        public static Passport of(String entries) {
            Passport passport = new Passport();
            for (String entry : entries.split(" ")) {
                String[] data = entry.split(":");
                switch (data[0]) {
                    case "byr" -> passport.birthYear = data[1];
                    case "iyr" -> passport.issueYear = data[1];
                    case "eyr" -> passport.expirationYear = data[1];
                    case "hgt" -> passport.height = data[1];
                    case "hcl" -> passport.hairColour = data[1];
                    case "ecl" -> passport.eyeColour = data[1];
                    case "pid" -> passport.passportId = data[1];
                    default -> {
                    }
                }
            }
            return passport;
        }

        public boolean isValid() {
            return ObjectUtils.allNonNull(
                birthYear, issueYear, expirationYear, height, hairColour, eyeColour, passportId
            );
        }
    }
}

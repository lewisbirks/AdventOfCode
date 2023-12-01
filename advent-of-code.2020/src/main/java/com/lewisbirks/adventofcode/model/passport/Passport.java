package com.lewisbirks.adventofcode.model.passport;

import com.lewisbirks.adventofcode.utils.ObjectUtils;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Passport {
    private static final Pattern HEIGHT_PATTERN = Pattern.compile("(?<number>\\d+)(?<unit>cm|in)");
    private static final List<String> EYE_COLOURS = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

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
                default -> {}
            }
        }
        return passport;
    }

    public boolean isValid() {
        return ObjectUtils.allNonNull(birthYear, issueYear, expirationYear, height, hairColour, eyeColour, passportId);
    }

    public boolean isStrictValid() {
        return isValid()
                && validNumber(birthYear, 1920, 2002)
                && validNumber(issueYear, 2010, 2020)
                && validNumber(expirationYear, 2020, 2030)
                && validHeight()
                && validHairColour()
                && validEyeColour()
                && validPassportId();
    }

    private boolean validPassportId() {
        return passportId.length() == 9 && passportId.chars().allMatch(c -> Character.digit(c, 10) != -1);
    }

    private boolean validEyeColour() {
        return EYE_COLOURS.contains(eyeColour);
    }

    private boolean validHairColour() {
        return hairColour.length() == 7 && hairColour.substring(1).chars().allMatch(c -> Character.digit(c, 16) != -1);
    }

    private boolean validHeight() {
        Matcher matcher = HEIGHT_PATTERN.matcher(height);
        if (!matcher.matches()) {
            return false;
        }
        String number = matcher.group("number");
        return switch (matcher.group("unit")) {
            case "cm" -> validNumber(number, 150, 193);
            case "in" -> validNumber(number, 59, 76);
            default -> false;
        };
    }

    private boolean validNumber(String number, long min, long max) {
        try {
            long parsed = Long.parseLong(number);
            return min <= parsed && parsed <= max;
        } catch (Exception ignored) {
            return false;
        }
    }
}

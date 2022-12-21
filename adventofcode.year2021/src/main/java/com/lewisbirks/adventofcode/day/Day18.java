package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.ReferenceInt;
import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.tree.SnailNumber;
import com.lewisbirks.adventofcode.utils.TreeUtils;

import java.util.List;

public final class Day18 extends Day {

    private List<SnailNumber> numbers;

    public Day18() {
        super(18, "Snailfish");
    }

    @Override
    protected void preLoad() {
        numbers = getInput(line -> this.parse(line, new ReferenceInt()));
    }

    @Override
    protected Object part1() {
        SnailNumber answer = numbers.stream()
            .map(SnailNumber::copy)
            .reduce(null, (result, element) -> reduce(result == null ? element : new SnailNumber(result, element)));

        if (answer == null) {
            throw new NullPointerException();
        }

        reduce(answer);

        return magnitude(answer);
    }

    @Override
    protected Object part2() {
        long max = 0;
        for (SnailNumber number1 : numbers) {
            for (SnailNumber number2 : numbers) {
                if (number1 != number2) {
                    max = Math.max(max, magnitude(reduce(new SnailNumber(number1.copy(), number2.copy()))));
                }
            }
        }
        return max;
    }

    private SnailNumber reduce(SnailNumber number) {
        while (true) {
            SnailNumber exploding = findExplodingNumber(number, 0);
            if (exploding != null) {
                explode(number, exploding);
                continue;
            }
            SnailNumber splitting = findSplittingNumber(number);
            if (splitting != null) {
                splitting.split();
                continue;
            }
            break;
        }


        return number;
    }

    private SnailNumber findExplodingNumber(SnailNumber number, int depth) {
        if (number.isRegularNumber()) {
            return null;
        }

        SnailNumber exploder = findExplodingNumber(number.getLeft(), depth + 1);

        if (exploder != null) {
            return exploder;
        }

        return number.isPair()
               && depth >= 4
               && number.getLeft().isRegularNumber()
               && number.getRight().isRegularNumber()
               ? number : findExplodingNumber(number.getRight(), depth + 1);
    }

    private void explode(SnailNumber root, SnailNumber toExplode) {
        List<SnailNumber> allNumbers = TreeUtils.walk(root);
        int explodingIndex = allNumbers.indexOf(toExplode);
        // get first number to the left and add the left number
        // need to ignore this node's values so search bounds explodingIndex ± 2
        for (int i = explodingIndex - 2; i >= 0; i--) {
            SnailNumber number = allNumbers.get(i);
            if (number.isRegularNumber()) {
                number.setValue(number.getValue() + toExplode.getLeft().getValue());
                break;
            }
        }
        for (int i = explodingIndex + 2; i < allNumbers.size(); i++) {
            SnailNumber number = allNumbers.get(i);
            if (number.isRegularNumber()) {
                number.setValue(number.getValue() + toExplode.getRight().getValue());
                break;
            }
        }
        // clean me
        toExplode.setLeft(null);
        toExplode.setRight(null);
        toExplode.setValue(0);
    }

    private SnailNumber findSplittingNumber(SnailNumber number) {
        if (number.isRegularNumber()) {
            return number.getValue() > 9 ? number : null;
        }
        SnailNumber splitting = findSplittingNumber(number.getLeft());
        return splitting != null ? splitting : findSplittingNumber(number.getRight());
    }

    private long magnitude(SnailNumber number) {
        return number.isRegularNumber() ? number.getValue()
                                        : 3L * magnitude(number.getLeft()) + 2L * magnitude(number.getRight());
    }

    private SnailNumber parse(String line, ReferenceInt index) {
        char c = line.charAt(index.value);
        // character is either a digit or '['
        index.value++;
        if (Character.isDigit(c)) {
            return new SnailNumber(Character.digit(c, 10));
        }

        SnailNumber number = new SnailNumber();

        number.setLeft(parse(line, index));

        // next character should be a ','
        index.value++;

        number.setRight(parse(line, index));

        // next character should be a ']'
        index.value++;

        return number;
    }
}

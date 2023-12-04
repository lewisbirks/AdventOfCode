package com.lewisbirks.adventofcode.day;

import static java.util.function.Predicate.not;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Day4 extends Day {

    private List<Card> cards;

    public Day4() {
        super(4, "Scratchcards");
    }

    @Override
    public void preload() {
        Function<String, Integer> toInt = s -> Integer.valueOf(s.trim());
        Function<String, Set<Integer>> collectNums = s -> Arrays.stream(s.trim().split(" "))
                .filter(not(String::isBlank))
                .map(toInt)
                .collect(Collectors.toSet());
        Function<String, Card> cardTransformer = line -> {
            int cardSep = line.indexOf(':');
            int winningSep = line.indexOf('|');
            int id = line.substring(line.indexOf(' '), cardSep).transform(toInt);
            Set<Integer> winning = line.substring(cardSep + 1, winningSep).transform(collectNums);
            Set<Integer> given = line.substring(winningSep + 1).transform(collectNums);
            return new Card(id, winning, given);
        };
        this.cards = getInput(cardTransformer);
    }

    @Override
    public Object part1() {
        return cards.stream().mapToLong(Card::calculateScore).sum();
    }

    @Override
    public Object part2() {
        Map<Integer, Long> idToNumCards = new HashMap<>();

        for (Card card : cards) {
            long wins = card.numberOfWins();
            int id = card.id();
            long currentCopies = idToNumCards.computeIfAbsent(id, i -> 1L);
            for (int i = id + 1; i <= wins + id; i++) {
                idToNumCards.put(i, idToNumCards.getOrDefault(i, 1L) + currentCopies);
            }
        }

        return idToNumCards.values().stream().mapToLong(l -> l).sum();
    }

    record Card(int id, Set<Integer> winning, Set<Integer> given) {

        public long calculateScore() {
            long wins = numberOfWins();
            return wins == 0 ? 0 : 1L << (wins - 1);
        }

        public long numberOfWins() {
            return given.stream().filter(winning::contains).count();
        }
    }
}

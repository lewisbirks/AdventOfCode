package com.lewisbirks.adventofcode.model.polymer;

import com.lewisbirks.adventofcode.common.collection.FrequencyMap;

import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class PolymerFormula {
    private final Map<String, String> insertionPairs;
    private final String template;
    private FrequencyMap<Character> characterCounts;

    public PolymerFormula(String template, Map<String, String> insertionPairs) {
        this.template = template;
        this.insertionPairs = insertionPairs;
    }

    public static PolymerFormula of(List<String> components) {
        components = new ArrayList<>(components);
        String template = components.remove(0);
        Map<String, String> insertionPairs = components.stream()
            .filter(s -> !s.isBlank())
            .map(s -> s.split(" -> "))
            .collect(Collectors.toUnmodifiableMap(split -> split[0], split -> split[1], (a, b) -> b));
        return new PolymerFormula(template, insertionPairs);
    }

    public PolymerFormula process(final int times) {
        characterCounts = template.chars().mapToObj(c -> (char) c).collect(FrequencyMap.collector());

        FrequencyMap<String> pairs = IntStream.range(0, template.length() - 1)
            .mapToObj(i -> template.substring(i, i + 2))
            .collect(FrequencyMap.collector());

        for (int i = 0; i < times; i++) {
            FrequencyMap<String> updatedPairs = new FrequencyMap<>();
            for (String pair : pairs.keySet()) {
                long currentFrequency = pairs.get(pair);
                if (insertionPairs.containsKey(pair)) {
                    String insertionCharacter = insertionPairs.get(pair);
                    updatedPairs.put(pair.charAt(0) + insertionCharacter, currentFrequency);
                    updatedPairs.put(insertionCharacter + pair.charAt(1), currentFrequency);
                    characterCounts.put(insertionCharacter.charAt(0), currentFrequency);
                } else {
                    updatedPairs.put(pair, currentFrequency);
                }
            }
            pairs = updatedPairs;
        }

        return this;
    }

    public LongSummaryStatistics summaryStats() {
        return characterCounts.valueStream().summaryStatistics();
    }

}

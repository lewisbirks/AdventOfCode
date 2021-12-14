package com.lewisbirks.adventofcode.model.polymer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class PolymerFormula {
    private final Map<String, String> insertionPairs;
    private String template;

    public PolymerFormula(String template, Map<String, String> insertionPairs) {
        this.template = template;
        this.insertionPairs = insertionPairs;
    }

    public PolymerFormula(PolymerFormula formula) {
        this.template = formula.template;
        this.insertionPairs = Map.copyOf(formula.insertionPairs);
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

    public void process(int times) {
        String template = this.template;

        for (int i = 0; i < times; i++) {
            StringBuilder sb = new StringBuilder();
            for (int index = 0; index < template.length(); index++) {
                sb.append(template.charAt(index));
                if (index >= template.length() - 1) {
                    continue;
                }
                String pair = template.substring(index, index + 2);
                String insertion = this.insertionPairs.get(pair);
                if (insertion != null) {
                    sb.append(insertion);
                }
            }
            template = sb.toString();
        }
        this.template = template;
    }

    public LongSummaryStatistics summaryStats() {
        return Arrays.stream(template.split(""))
            .collect(Collectors.groupingBy(Function.identity(), HashMap::new, Collectors.counting()))
            .values()
            .stream()
            .mapToLong(Long::longValue)
            .summaryStatistics();
    }
}

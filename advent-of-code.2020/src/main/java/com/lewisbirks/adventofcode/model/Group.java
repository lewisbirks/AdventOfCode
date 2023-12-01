package com.lewisbirks.adventofcode.model;

import com.lewisbirks.adventofcode.common.collection.FrequencyMap;
import java.util.List;

public record Group(List<String> answers) {
    public long numberOfUniqueAnswers() {
        return answers.stream().flatMapToInt(String::chars).distinct().count();
    }

    public long numberOfSharedAnswers() {
        return answers.stream()
                .flatMap(answer -> answer.chars().boxed())
                .collect(FrequencyMap.collector())
                .valueStream()
                .filter(l -> l == answers.size())
                .count();
    }
}

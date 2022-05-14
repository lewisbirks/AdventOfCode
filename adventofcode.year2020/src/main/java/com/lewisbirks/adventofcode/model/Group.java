package com.lewisbirks.adventofcode.model;

import com.lewisbirks.adventofcode.collection.FrequencyMap;

import java.util.List;

public record Group(List<String> answers) {
    public long numberOfUniqueAnswers() {
        return answers.stream()
            .flatMapToInt(String::chars)
            .distinct()
            .count();
    }
}

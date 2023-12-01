package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.domain.crates.Command;
import com.lewisbirks.adventofcode.domain.crates.CrateStack;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Day5 extends Day {

    private static final Pattern COMMAND_PATTERN = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

    private List<CrateStack> stacks;
    private List<Command> commands;

    public Day5() {
        super(5, "Supply Stacks");
    }

    @Override
    protected void preLoad() {
        String all = readInput();
        String[] sections = all.split("\n\n");

        this.stacks = parseStacks(sections[0]);
        this.commands = parseCommands(sections[1]);
    }

    private List<CrateStack> parseStacks(String stacks) {
        String[] lines = stacks.split("\n");
        String info = lines[lines.length - 1];
        int amount = Integer.parseInt(info.substring(info.lastIndexOf(" ") + 1));
        List<CrateStack.CrateStackBuilder> builders =
                IntStream.range(0, amount).mapToObj(i -> CrateStack.builder()).toList();

        for (int i = 0; i < lines.length - 1; i++) {
            String line = lines[i];
            int builderIndex = -1;
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if (j % 4 == 0) {
                    builderIndex++;
                }
                if (c != ' ' && c != '[' && c != ']') {
                    builders.get(builderIndex).add(c);
                }
            }
        }

        return builders.stream().map(CrateStack.CrateStackBuilder::build).toList();
    }

    private List<Command> parseCommands(String commands) {
        return commands.lines()
                .map(command -> {
                    Matcher commandMatcher = COMMAND_PATTERN.matcher(command);
                    if (!commandMatcher.matches()) {
                        throw new IllegalArgumentException(command);
                    }
                    int amount = Integer.parseInt(commandMatcher.group(1));
                    int source = Integer.parseInt(commandMatcher.group(2)) - 1;
                    int receiver = Integer.parseInt(commandMatcher.group(3)) - 1;
                    return new Command(amount, source, receiver);
                })
                .toList();
    }

    @Override
    protected Object part1() {
        commands.forEach(command -> {
            CrateStack source = stacks.get(command.source());
            CrateStack destination = stacks.get(command.destination());
            for (int i = 0; i < command.amount(); i++) {
                destination.add(source.pop());
            }
        });
        return getOutput();
    }

    @Override
    protected Object part2() {
        commands.forEach(command -> {
            CrateStack source = stacks.get(command.source());
            CrateStack destination = stacks.get(command.destination());
            destination.addAll(source.popBulk(command.amount()));
        });
        return getOutput();
    }

    private String getOutput() {
        return stacks.stream().map(crateStack -> crateStack.pop().toString()).collect(Collectors.joining());
    }
}

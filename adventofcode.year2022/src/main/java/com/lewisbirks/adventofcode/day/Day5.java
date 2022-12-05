package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.ArrayDeque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Day5 extends Day {

    private static final Pattern COMMAND_PATTERN = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

    private final List<CrateStack> stacks;
    private final List<Command> commands;


    public Day5() {
        super(5, "Supply Stacks");
        String all = readInput();
        String[] sections = all.split("\n\n");

        String stacks = sections[0];
        String commands = sections[1];
        this.stacks = parseStacks(stacks);
        this.commands = parseCommands(commands);
    }

    private List<CrateStack> parseStacks(String stacks) {
        String[] lines = stacks.split("\n");
        String info = lines[lines.length - 1];
        int amount = Integer.parseInt(info.substring(info.lastIndexOf(" ") + 1));
        List<CrateStack.CrateStackBuilder> builders = IntStream.range(0, amount)
            .mapToObj(i -> CrateStack.builder())
            .toList();

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
        return commands.lines().map(command -> {
            System.out.println(command);
            Matcher commandMatcher = COMMAND_PATTERN.matcher(command);
            commandMatcher.matches();
            int amount = Integer.parseInt(commandMatcher.group(1));
            int source = Integer.parseInt(commandMatcher.group(2)) - 1;
            int receiver = Integer.parseInt(commandMatcher.group(3)) - 1;
            return new Command(amount, source, receiver);
        }).toList();
    }

    @Override
    protected void preLoad() {}

    void reset() {
        this.stacks.forEach(CrateStack::reset);
    }

    @Override
    protected Object part1() {
        commands.forEach(command -> {
            CrateStack source = stacks.get(command.source);
            CrateStack destination = stacks.get(command.destination);
            for (int i = 0; i < command.amount; i++) {
                Character crate = source.pop();
                destination.add(crate);
            }
        });
        return stacks.stream().map(crateStack -> crateStack.pop().toString()).collect(Collectors.joining());
    }

    @Override
    protected Object part2() {
        reset();
        return null;
    }


    static class CrateStack {
        private ArrayDeque<Character> crates;
        private final ArrayDeque<Character> source;


        CrateStack(ArrayDeque<Character> crates) {
            this.crates = crates;
            this.source = crates;
        }

        void reset() {
            crates = source.clone();
        }

        public Character pop() {
            return crates.pollLast();
        }

        public void add(Character c) {
            crates.add(c);
        }

        @Override
        public String toString() {
            return "CrateStack{" +
                   "crates=" + crates +
                   '}';
        }

        public static CrateStackBuilder builder() {
            return new CrateStackBuilder();
        }

        public static class CrateStackBuilder {
            private final ArrayDeque<Character> crates = new ArrayDeque<>();

            private CrateStackBuilder() {
            }

            public CrateStackBuilder add(Character c) {
                crates.addFirst(c);
                return this;
            }

            public CrateStack build() {
                return new CrateStack(crates);
            }
        }
    }

    record Command(int amount, int source, int destination) {
    }
}

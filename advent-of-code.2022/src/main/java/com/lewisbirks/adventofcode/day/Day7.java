package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.domain.disk.DiskNode;
import com.lewisbirks.adventofcode.utils.TerminalCommands;
import java.util.List;
import java.util.Objects;

public final class Day7 extends Day {

    private static final int SYSTEM_SIZE = 70000000;
    private static final int UPDATE_SIZE = 30000000;
    private static final int MAX_DIR_SIZE = 100000;

    private DiskNode root = null;

    public Day7() {
        super(7, "No Space Left On Device");
    }

    @Override
    public void preload() {
        List<String> consoleOutput = getInput();
        DiskNode current = null;
        for (String s : consoleOutput) {
            if (TerminalCommands.isCommand(s)) {
                if (TerminalCommands.changingDirectory(s)) {
                    String name = TerminalCommands.getDirectory(s);
                    if (TerminalCommands.isNavigatingOut(name)) {
                        current = Objects.requireNonNull(current).getParent();
                    } else {
                        if (current == null) {
                            current = new DiskNode(null, name);
                            root = current;
                        } else {
                            current = current.getChild(name).orElseThrow();
                        }
                    }
                }
                continue;
            }
            // we are listing
            String name = TerminalCommands.getFileName(s);
            if (TerminalCommands.isDirectory(s)) {
                DiskNode dir = new DiskNode(current, name);
                Objects.requireNonNull(current).addChild(dir);
                continue;
            }
            // we are a file
            long fileSize = TerminalCommands.getFileSize(s);
            DiskNode file = new DiskNode(current, name);
            Objects.requireNonNull(current).addChild(file);
            file.addSize(fileSize);
        }
    }

    @Override
    public Object part1() {
        return root.getDirectoryNodes().stream()
                .mapToLong(DiskNode::getSize)
                .filter(size -> size <= MAX_DIR_SIZE)
                .sum();
    }

    @Override
    public Object part2() {
        long required = UPDATE_SIZE - (SYSTEM_SIZE - root.getSize());
        return root.getDirectoryNodes().stream()
                .mapToLong(DiskNode::getSize)
                .filter(size -> size >= required)
                .min()
                .orElseThrow();
    }
}

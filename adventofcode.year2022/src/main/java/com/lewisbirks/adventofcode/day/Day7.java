package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Day7 extends Day {

    private static final int SYSTEM_SIZE = 70000000;
    private static final int UPDATE_SIZE = 30000000;
    private static final int MAX_DIR_SIZE = 100000;

    private DiskNode root = null;

    public Day7() {
        super(7, "No Space Left On Device");
    }

    @Override
    protected void preLoad() {
        List<String> consoleOutput = getInput();
        DiskNode current = null;
        for (String s : consoleOutput) {
            if (isCommand(s)) {
                if (changingDirectory(s)) {
                    String name = getDirectory(s);
                    if (isNavigatingOut(name)) {
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
            String name = getFileName(s);
            if (isDirectory(s)) {
                DiskNode dir = new DiskNode(current, name);
                Objects.requireNonNull(current).addChild(dir);
                continue;
            }
            // we are a file
            long fileSize = getFileSize(s);
            DiskNode file = new DiskNode(current, name);
            Objects.requireNonNull(current).addChild(file);
            file.addSize(fileSize);
        }
    }

    @Override
    protected Object part1() {
        return root.getDirectoryNodes().stream()
            .mapToLong(DiskNode::getSize)
            .filter(size -> size <= MAX_DIR_SIZE)
            .sum();
    }

    @Override
    protected Object part2() {
        long required = UPDATE_SIZE - (SYSTEM_SIZE - root.getSize());
        return root.getDirectoryNodes().stream()
            .mapToLong(DiskNode::getSize)
            .filter(size -> size >= required)
            .min()
            .orElseThrow();
    }

    private String getFileName(String line) {
        return line.substring(line.indexOf(' ') + 1);
    }

    private boolean isCommand(String line) {
        return line.startsWith("$");
    }

    private boolean changingDirectory(String line) {
        return line.startsWith("$ cd");
    }

    private boolean isNavigatingOut(String name) {
        return name.startsWith("..");
    }

    private String getDirectory(String line) {
        return line.substring(5);
    }

    private boolean listDirectory(String line) {
        return line.startsWith("$ ls");
    }

    private boolean isDirectory(String line) {
        return line.startsWith("dir");
    }

    private long getFileSize(String line) {
        return Long.parseLong(line.substring(0, line.indexOf(' ')));
    }

    static final class DiskNode {
        private final List<DiskNode> children;
        private final DiskNode parent;
        private final String name;
        private long size;

        DiskNode(DiskNode parent, String name) {
            this.parent = parent;
            this.name = name;
            this.children = new ArrayList<>();
            this.size = 0;
        }

        public DiskNode getParent() {
            return parent;
        }

        public void addChild(DiskNode child) {
            children.add(child);
        }

        public boolean isDir() {
            return !children.isEmpty();
        }

        public Optional<DiskNode> getChild(String name) {
            return children.stream().filter(node -> node.name.equals(name)).findFirst();
        }

        public long getSize() {return size;}

        public void addSize(long amount) {
            size += amount;
            if (parent != null) {
                parent.addSize(amount);
            }
        }

        public List<DiskNode> getDirectoryNodes() {
            if (!isDir()) {
                return List.of();
            }
            List<DiskNode> dirs = new ArrayList<>();
            dirs.add(this);
            dirs.addAll(
                children.stream().filter(DiskNode::isDir).flatMap(node -> node.getDirectoryNodes().stream()).toList()
            );
            return dirs;
        }
    }
}

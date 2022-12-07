package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Day7 extends Day {

    private List<String> consoleOutput;

    public Day7() {
        super(7, "No Space Left On Device");
    }

    @Override
    protected void preLoad() {
        consoleOutput = getInput();
    }

    @Override
    protected Object part1() {
        Node current = null;
        Node root = null;
        for (String s : consoleOutput) {
            System.out.println(s);
            if (isCommand(s)) {
                System.out.print("Is command | ");
                if (changingDirectory(s)) {
                    String name = getDirectory(s);
                    System.out.printf("Changing dir to \"%s\" | ", name);
                    if (isNavigatingOut(name)) {
                        System.out.print("Navigating up one level");
                        current = Objects.requireNonNull(current).getParent();
                    } else {
                        if (current == null) {
                            System.out.println("Creating root node");
                            current = new Node(null, name);
                            root = current;
                        } else {
                            Optional<Node> child = current.getChild(name);
                            if (child.isPresent()) {
                                System.out.println("Found child");
                            } else {
                                System.out.println("Creating child");
                            }
                            current = child.orElse(new Node(current, name));
                        }
                    }
                } else {
                    System.out.println("Not changing dir");
                }
                continue;
            }
            // we are listing
            System.out.print("Is not command | ");
            if (isDirectory(s)) {
                System.out.println("Is a dir ignoring");
                continue;
            }
            // we are a file
            long fileSize = getFileSize(s);
            String fileName = getFileName(s);
            System.out.printf("Is a file with name %s and a size of %d%n", fileName, fileSize);
            Node file = new Node(current, fileName);
            Objects.requireNonNull(current).addChild(file);
            file.addSize(fileSize);
        }

        return Objects.requireNonNull(root).getDirectoryNodes().stream()
            .mapToLong(Node::getSize)
            .filter(size -> size <= 100000)
            .sum();
    }

    private String getFileName(String line) {
        return line.substring(line.indexOf(' ') + 1);
    }

    @Override
    protected Object part2() {
        return null;
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

    static final class Node {
        private final List<Node> children;
        private final Node parent;
        private final String name;
        private long size;

        Node(Node parent, String name) {
            this.parent = parent;
            this.name = name;
            this.children = new ArrayList<>();
            this.size = 0;
            if (parent != null) {
                parent.addChild(this);
            }
        }

        public void addChild(Node child) {
            children.add(child);
        }

        public boolean isDir() {
            return !children.isEmpty();
        }

        public Optional<Node> getChild(String name) {
            return children.stream().filter(node -> node.name.equals(name)).findFirst();
        }

        public List<Node> getChildren() {return children;}

        public long getSize() {return size;}

        public void addSize(long amount) {
            size += amount;
            if (parent != null) {
                parent.addSize(amount);
            }
        }

        public List<Node> getDirectoryNodes() {
            if (!isDir()) {
                return List.of();
            }
            List<Node> dirs = new ArrayList<>();
            dirs.add(this);
            dirs.addAll(
                children.stream().filter(Node::isDir).flatMap(node -> node.getDirectoryNodes().stream()).toList()
            );
            return dirs;
        }

        public Node getParent() {
            return parent;
        }
    }
}

package com.lewisbirks.adventofcode.domain.disk;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class DiskNode {
    private final List<DiskNode> children;
    private final DiskNode parent;
    private final String name;
    private long size;

    public DiskNode(DiskNode parent, String name) {
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
        List<DiskNode> childDirs = children.stream()
//            .filter(DiskNode::isDir)
            .flatMap(node -> node.getDirectoryNodes().stream())
            .toList();
        List<DiskNode> dirs = new ArrayList<>(childDirs);
        dirs.add(this);
        return dirs;
    }
}

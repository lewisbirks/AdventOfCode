package com.lewisbirks.adventofcode.utils;

import com.lewisbirks.adventofcode.tree.Node;

import java.util.ArrayList;
import java.util.List;

public final class TreeUtils {
    public static <T extends Node<T>> List<T> walk(T node) {
        List<T> traversed = new ArrayList<>();
        internalWalk(node, traversed);
        return traversed;
    }

    private static <T extends Node<T>> void internalWalk(T node, List<T> traversed) {
        if (node.isLeaf()) {
            traversed.add(node);
            return;
        }
        internalWalk(node.getLeft(), traversed);
        traversed.add(node);
        internalWalk(node.getRight(), traversed);
    }
}

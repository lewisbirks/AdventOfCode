package com.lewisbirks.adventofcode.tree;

public interface Node<T> {
    T getLeft();

    T getRight();

    boolean isLeaf();
}

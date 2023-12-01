package com.lewisbirks.adventofcode.tree;

public class SnailNumber implements Node<SnailNumber> {
    private SnailNumber left;
    private SnailNumber right;
    private int value;

    public SnailNumber() {}

    public SnailNumber(int value) {
        this.value = value;
    }

    public SnailNumber(SnailNumber left, SnailNumber right) {
        this.left = left;
        this.right = right;
    }

    public boolean isRegularNumber() {
        return !isPair();
    }

    public boolean isPair() {
        return left != null;
    }

    public void split() {
        int leftValue = value / 2;
        int rightValue = (value / 2) + (value % 2);
        this.left = new SnailNumber(leftValue);
        this.right = new SnailNumber(rightValue);
        this.value = 0;
    }

    public SnailNumber copy() {
        return isRegularNumber() ? new SnailNumber(this.value) : new SnailNumber(left.copy(), right.copy());
    }

    @Override
    public SnailNumber getLeft() {
        return left;
    }

    public void setLeft(SnailNumber left) {
        this.left = left;
    }

    @Override
    public SnailNumber getRight() {
        return right;
    }

    public void setRight(SnailNumber right) {
        this.right = right;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean isLeaf() {
        return isRegularNumber();
    }

    @Override
    public String toString() {
        if (this.isRegularNumber()) {
            return String.valueOf(value);
        }
        return String.format("[%s,%s]", left, right);
    }
}

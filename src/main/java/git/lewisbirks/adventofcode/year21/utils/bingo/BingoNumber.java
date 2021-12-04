package git.lewisbirks.adventofcode.year21.utils.bingo;

public final class BingoNumber {
    private final int value;

    private boolean marked;

    private BingoNumber(int value, boolean marked) {
        this.value = value;
        this.marked = marked;
    }

    public BingoNumber(int value) {
        this(value, false);
    }

    public int value() {return value;}

    public boolean marked() {return marked;}

    public void setMarked(boolean marked) {
        this.marked = marked;
    }
}

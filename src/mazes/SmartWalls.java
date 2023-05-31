package mazes;

public class SmartWalls {
    private final Cell first;
    private final Cell other;
    private final boolean horizontal;

    protected SmartWalls(Cell first, Cell other, boolean horizontal) {
        this.first = first;
        this.other = other;
        this.horizontal = horizontal;


    }

    protected Cell getFirst() {
        return first;
    }

    protected Cell getOther() {
        return other;
    }

    protected boolean isHorizontal() {
        return horizontal;
    }
}

package mazes;

import java.util.ArrayList;
import java.util.List;

/**
 * Direction provides enums for the four points of the compass and stores the
 * x- and y-offsets for moving in each direction according to board geometry.
 *
 * @author Jack Trainor
 * @version CS_351 : 2021 - 02 - 20
 */
public enum Direction {
    N(0, -1), E(1, 0), S(0, 1), W(-1, 0), NO(0, 0);

    private static final List<Direction> validDirections =
            createValidDirections();
    private final int X;
    private final int Y;
    Direction(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    protected static List<Direction> getValidDirections() {
        return validDirections;
    }

    protected static List<Direction> createValidDirections() {
        List<Direction> directions = new ArrayList<>();
        directions.add(N);
        directions.add(E);
        directions.add(S);
        directions.add(W);
        return directions;
    }

    protected static Direction getReverseDirection(Direction d) {
        switch (d) {
            case N:
                return S;
            case E:
                return W;
            case S:
                return N;
            case W:
                return E;
            default:
                return NO;
        }
    }

    protected static Direction getCrossDirection(Direction d) {
        switch (d) {
            case N:
                return E;
            case E:
                return S;
            case S:
                return W;
            case W:
                return N;
            default:
                return NO;
        }
    }

    protected static Direction getRightDirection(Direction d) {
        switch (d) {
            case N:
                return E;
            case E:
                return S;
            case S:
                return W;
            case W:
                return N;
            default:
                return NO;
        }
    }

    protected static Direction getLeftDirection(Direction d) {
        Direction rightDirection = getRightDirection(d);
        return getReverseDirection(rightDirection);
    }

    public static void main(String[] args) {

    }

    /**
     * Getter for x.
     *
     * @return int X for Direction.
     */
    protected int x() {
        return X;
    }

    /**
     * Getter for y.
     *
     * @return int Y for Direction.
     */
    protected int y() {
        return Y;
    }
}

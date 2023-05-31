package mazes;

/**
 * SolverEvent is the message object passed back and forth between Graph elements
 * on their producer-consumer blocking queues.
 *
 * @author Jack Trainor
 * @version CS_351_002 : 2021 - 03 - 29
 */

public class SolverEvent {
    private static final Direction prefD = Direction.S;
    private final String type;
    private final String from;
    private final String to;
    private final Direction direction;
    private final String path;
    private final int turns;

    /**
     * Constructor for SolverEvent, which is an event passed between GraphComps.
     *
     * @param type
     * @param from
     * @param to
     * @param direction
     * @param path
     * @param turns
     */
    public SolverEvent(String type, String from, String to, Direction direction,
                       String path, int turns) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.direction = direction;
        this.path = path;
        this.turns = turns;
    }

    public SolverEvent(String type, String from, String to, Direction direction,
                       String path) {
        this(type, from, to, direction, path, 0);
    }

    protected static Direction getPrefD() {
        return prefD;
    }

    protected String getType() {
        return type;
    }

    protected String getFrom() {
        return from;
    }

    protected String getTo() {
        return to;
    }

    protected Direction getDirection() {
        return direction;
    }

    protected String getPath() {
        return path;
    }

    protected int getTurns() {
        return turns;
    }

    public String toString() {
        return String.format("%s %s %s %s %s",
                type, from, to, direction.toString(), path);
    }
}

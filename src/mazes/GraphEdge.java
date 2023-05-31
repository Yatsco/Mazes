package mazes;

import java.awt.*;

/**
 * GraphEdge supports an edge object in the Graph framework.
 *
 * @author Jack Trainor
 * @version CS_351_002 : 2021 - 03 - 29
 */

public class GraphEdge extends GraphComp {
    private final int startCellX;
    private final int startCellY;
    private final int endCellX;
    private final int endCellY;
    private Direction direction;
    private String startVertId;
    private String endVertId;

    /**
     * Constructor for GraphEdge. Connects two GraphVerts.
     *
     * @param v1
     * @param v2
     */
    public GraphEdge(GraphVert v1, GraphVert v2) {
        this(v1.getCellX(), v1.getCellY(), v2.getCellX(), v2.getCellY());
    }

    /**
     * Constructor for GraphEdge based on xy cell coordinates.
     *
     * @param cellX1
     * @param cellY1
     * @param cellX2
     * @param cellY2
     */
    public GraphEdge(int cellX1, int cellY1, int cellX2, int cellY2) {
        super();
        int cell1 = 10 * cellY1 + cellX1;
        int cell2 = 10 * cellY2 + cellX2;
        if (cell1 < cell2) {
            this.startCellX = cellX1;
            this.startCellY = cellY1;
            this.endCellX = cellX2;
            this.endCellY = cellY2;
        } else {
            this.startCellX = cellX2;
            this.startCellY = cellY2;
            this.endCellX = cellX1;
            this.endCellY = cellY1;
        }
        id = createId(startCellX, startCellY, endCellX, endCellY);
        if (startCellX < endCellX) {
            direction = Direction.E;
        }
        if (startCellY < endCellY) {
            direction = Direction.S;
        }
    }

    public static void main(String[] args) {


    }

    protected void setStartVertId(String vertId) {
        startVertId = vertId;
    }

    protected void setEndVertId(String vertId) {
        endVertId = vertId;
    }

    protected String getStartVertId(String vertId) {
        return startVertId;
    }

    protected String getEndVertId(String vertId) {
        return endVertId;
    }

    private String createId(int startCellX, int startCellY, int endCellX,
                            int endCellY) {
        return String.format("%02d%02d-%02d%02d", startCellX, startCellY,
                endCellX, endCellY);
    }

    protected Point getStartPoint() {
        return new Point(startCellX,
                startCellY);
    }

    protected Point getEndPoint() {
        return new Point(endCellX, endCellY);
    }

    protected Direction getDirection() {
        return direction;
    }

    protected Direction getRevDirection() {
        return Direction.getReverseDirection(direction);
    }

    protected int getStartCellX() {
        return startCellX;
    }

    protected int getStartCellY() {
        return startCellY;
    }

    protected int getEndCellX() {
        return endCellX;
    }

    protected int getEndCellY() {
        return endCellY;
    }

    protected Direction getNextDirection(String fromId) {
        if (fromId.equals(startVertId)) {
            return direction;
        } else if (fromId.equals(endVertId)) {
            return Direction.getReverseDirection(direction);
        }
        return null;
    }

    protected String getNextVertId(String fromId) {
        if (fromId.equals(startVertId)) {
            return endVertId;
        } else if (fromId.equals(endVertId)) {
            return startVertId;
        }
        return null;
    }

    protected String toDb() {
        return String.format("[%s] %s -- %s", id,
                startVertId, endVertId);
    }
}

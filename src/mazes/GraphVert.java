package mazes;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * GraphVert supports a vertex object in the Graph framework.
 *
 * @author Jack Trainor
 * @version CS_351_002 : 2021 - 03 - 29
 */

public class GraphVert extends GraphComp {
    private final int cellX;
    private final int cellY;
    private final Map<Direction, String> edgeMap;

    /**
     * Constructor for GraphVert
     *
     * @param cellX
     * @param cellY
     */
    public GraphVert(int cellX, int cellY) {
        super();
        this.cellX = cellX;
        this.cellY = cellY;
        id = createId(cellX, cellY);
        edgeMap = new TreeMap<>();
    }

    protected static boolean isEqualPoint(GraphVert v1, GraphVert v2) {
        return (v1.cellX == v2.cellX && v1.cellY == v2.cellY);
    }

    protected static String createId(int cellX, int cellY) {
        return String.format("%02d%02d", cellX, cellY);
    }

    protected int getCellX() {
        return cellX;
    }

    protected int getCellY() {
        return cellY;
    }

    protected List<String> getListEdges() {
        return null;
    }

    protected Map<Direction, String> getEdgeMap() {
        return edgeMap;
    }

    protected void setEdge(Direction d, String id) {
        edgeMap.put(d, id);
    }

    protected String getEdge(Direction d) {
        return edgeMap.get(d);
    }

    protected Point getPoint() {
        return new Point(cellX, cellY);
    }

    protected String toDb() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[%s]     ", id));
        for (Direction d : Direction.createValidDirections()) {
            String id = edgeMap.get(d);
            if (id != null) {
                sb.append(String.format(" %s %s", d, id));
            }
        }
        return sb.toString();
    }
}

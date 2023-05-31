package mazes;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

import java.awt.*;
import java.util.*;

/**
 * Graph starts as a conversion from Maze then provides the basis for the Graph
 * framework supporting Solver logic for maze searches.
 *
 * @author Jack Trainor
 * @version CS_351_002 : 2021 - 03 - 29
 */

public class Graph {
    private final int width;
    private final int height;
    private final int cellWidth;
    private final int cellHeight;
    private final int rowCells;
    private final int colCells;
    private final String solverType;
    private final Map<String, GraphComp> compMap;
    private final Map<Point, Set<String>> pointIndex;

    /**
     * Constructor for Graph. Includes flexibility for non-square mazes.
     *
     * @param width
     * @param height
     * @param cellWidth
     * @param cellHeight
     * @param solverType
     */
    public Graph(int width, int height, int cellWidth, int cellHeight, String solverType) {
        this.width = width;
        this.height = height;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.solverType = solverType;
        this.rowCells = (int) Math.floor(width / cellWidth);
        this.colCells = (int) Math.floor(height / cellHeight);
        this.pointIndex = new HashMap<>();
        this.compMap = new TreeMap();
    }

    public static void main(String[] args) {
    }

    protected int getWidth() {
        return width;
    }

    protected int getHeight() {
        return height;
    }

    protected int getCellWidth() {
        return cellWidth;
    }

    protected int getCellHeight() {
        return cellHeight;
    }

    protected int getRowCells() {
        return rowCells;
    }

    protected int getColCells() {
        return colCells;
    }

    protected String getSolverType() {
        return solverType;
    }

    protected Map<String, GraphComp> getCompMap() {
        return compMap;
    }

    protected double cellXtoWindowX(int x) {
        double halfCellWidth = cellWidth / 2.0;
        return x * cellWidth + halfCellWidth;
    }

    protected double cellYtoWindowY(int y) {
        double halfCellHeight = cellHeight / 2.0;
        return y * cellHeight + halfCellHeight;
    }

    protected Point2D cellPointToWindowPoint(Point cellPoint) {
        double halfCellWidth = cellWidth / 2.0;
        double halfCellHeight = cellHeight / 2.0;
        double windowPointX = cellPoint.x * cellWidth + halfCellWidth;
        double windowPointY = cellPoint.y * cellHeight + halfCellHeight;
        return new Point2D(windowPointX, windowPointY);
    }

    protected Line graphEdgeToLine(GraphEdge edge) {
        double startX = cellXtoWindowX(edge.getStartCellX());
        double startY = cellYtoWindowY(edge.getStartCellY());
        double endX = cellXtoWindowX(edge.getEndCellX());
        double endY = cellYtoWindowY(edge.getEndCellY());
        return new Line(startX, startY, endX, endY);
    }

    protected void connectGraph() {
        Set<Point> points = pointIndex.keySet();
        for (Point point : points) {
            Set<String> idSet = pointIndex.get(point);
            Set<String> vertSet = new TreeSet<>();
            Set<String> edgeSet = new TreeSet<>();
            for (String id : idSet) {
                GraphComp comp = compMap.get(id);
                if (comp instanceof GraphVert) {
                    vertSet.add(id);
                } else if (comp instanceof GraphEdge) {
                    edgeSet.add(id);
                }
            }
            if (vertSet.size() == 1) {
                String vertId = vertSet.iterator().next();
                GraphVert vert = ((GraphVert) compMap.get(vertId));
                for (String edgeId : edgeSet) {
                    GraphEdge edge = ((GraphEdge) compMap.get(edgeId));
                    Direction edgeD = edge.getDirection();
                    Direction edgeRevD = edge.getRevDirection();
                    if (edge.getStartCellX() == vert.getCellX()
                            && edge.getStartCellY() == vert.getCellY()) {
                        edge.setStartVertId(vertId);
                        vert.setEdge(edgeD, edge.getId());
                    } else if (edge.getEndCellX() == vert.getCellX()
                            && edge.getEndCellY() == vert.getCellY()) {
                        edge.setEndVertId(vertId);
                        vert.setEdge(edgeRevD, edge.getId());
                    }
                }
            } else {
                Log.err(String.format("connectGraph: no GraphVert for %s", point.toString()));
            }
        }
    }

    protected void addCompToPointIndex(Point point, GraphComp comp) {
        String id = comp.getId();
        Set<String> idSet = pointIndex.get(point);
        if (idSet == null) {
            idSet = new TreeSet<>();
        }
        idSet.add(id);
        pointIndex.put(point, idSet);
    }

    protected void addComp(GraphComp comp) {
        String id = comp.getId();
        // Db.out(String.format("addComp: %s", comp.toDb()));

        // every comp is associated with one or two points
        // build a map with points as keys to the comp ids associated with point
        // map is called point index since it is an index based on points
        compMap.put(id, comp);
        if (comp instanceof GraphVert) {
            Point point = ((GraphVert) comp).getPoint();
            addCompToPointIndex(point, comp);
        } else if (comp instanceof GraphEdge) {
            Point startPoint = ((GraphEdge) comp).getStartPoint();
            Point endPoint = ((GraphEdge) comp).getEndPoint();
            addCompToPointIndex(startPoint, comp);
            addCompToPointIndex(endPoint, comp);
        }
    }

    protected String toDb() {
        StringBuilder sb = new StringBuilder();
        Set<String> ids = compMap.keySet();
        for (String id : ids) {
            GraphComp comp = compMap.get(id);
            sb.append(comp.toDb() + "\n");
        }
        return sb.toString();
    }

    protected GraphComp getComp(String id) {
        return compMap.get(id);
    }

    protected Set<String> getIds() {
        return compMap.keySet();
    }

    protected Map<Direction, String> getEdgeMap(String vertId) {
        GraphComp comp = compMap.get(vertId);
        if (comp instanceof GraphVert) {
            GraphVert vert = ((GraphVert) comp);
            return vert.getEdgeMap();
        }
        return null;
    }

    protected void addVertsToGraph(GraphVert startVert, GraphVert endVert) {
        GraphEdge edge = new GraphEdge(startVert, endVert);

        // will clobber previous comps but that doesn't matter until after connectGraph()
        addComp(startVert);
        addComp(endVert);
        addComp(edge);
    }

    protected boolean hasComp(String id) {
        Set<String> ids = getIds();
        return ids.contains(id);
    }

    protected void addEdgeToGraph(GraphEdge edge) {
        GraphVert startVert = new GraphVert(edge.getStartCellX(), edge.getStartCellY());
        GraphVert endVert = new GraphVert(edge.getEndCellX(), edge.getEndCellY());

        // will clobber previous comps but that doesn't matter until after connectGraph()
        addComp(startVert);
        addComp(endVert);
        addComp(edge);
    }
}

package mazes;

import java.util.HashMap;
import java.util.Map;

/**
 * MazeConverter converts a Maze to a Graph for passing on to the Solver.
 *
 * @author Jack Trainor
 * @version CS_351_002 : 2021 - 03 - 29
 */

public class MazeConverter {
    private final Maze maze;
    private final Graph graph;

    /**
     * Constructor for MazeConverter based on maze argument.
     *
     * @param maze
     */
    public MazeConverter(Maze maze) {
        this.maze = maze;
        int width = Maze.windowSize;
        int height = Maze.windowSize;
        int cellWidth = Maze.cellSize;
        int cellHeight = Maze.cellSize;
        String solverType = Maze.solverType;
        graph = new Graph(width, height, cellWidth, cellHeight, solverType);
        convert();
    }

    public static void main(String[] args) {
    }

    protected Graph getGraph() {
        return graph;
    }

    protected void addVertsToGraph(GraphVert startVert, GraphVert endVert) {
        GraphEdge edge = new GraphEdge(startVert, endVert);
        graph.addComp(startVert);
        graph.addComp(endVert);
        graph.addComp(edge);
    }

    protected Graph convert() {
        int numRows = maze.size;
        int numCols = maze.size;
        GraphCell[][] graphCells = new GraphCell[numRows][numCols];

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Cell cell = maze.getCell(row, col);
                GraphCell graphCell = new GraphCell(cell);
                graphCells[row][col] = graphCell;
            }
        }

        Direction acrossD = Direction.E;
        Direction downD = Direction.S;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                GraphVert startVert = new GraphVert(col, row);
                GraphCell curCell = graphCells[row][col];
                if (!curCell.getWall(acrossD)) {
                    GraphVert endVert = new GraphVert(col + 1, row);
                    addVertsToGraph(startVert, endVert);
                }
                if (!curCell.getWall(downD)) {
                    GraphVert endVert = new GraphVert(col, row + 1);
                    addVertsToGraph(startVert, endVert);
                }
            }
        }
        //Db.out(graph.toDb());
        graph.connectGraph();
        //Db.out(graph.toDb());
        return graph;
    }

    private class GraphCell {
        int row;
        int col;
        Map<Direction, Boolean> graphWalls = new HashMap<>();

        protected GraphCell(Cell cell) {
            this.row = cell.getRow();
            this.col = cell.getCol();
            Walls walls = cell.getWalls();
            graphWalls.put(Direction.N, walls.getNorthWall());
            graphWalls.put(Direction.E, walls.getEastWall());
            graphWalls.put(Direction.S, walls.getSouthWall());
            graphWalls.put(Direction.W, walls.getWestWall());
        }

        protected boolean getWall(Direction d) {
            return graphWalls.get(d);
        }
    }
}

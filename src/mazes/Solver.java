package mazes;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Solver solves a Maze with a specified search algorithm.
 *
 * @author Jack Trainor
 * @version CS_351_002 : 2021 - 03 - 29
 */

public class Solver {
    private final Graph graph;
    private String solverType;
    private final Map<String, SolverEntry> entries;
    private final String startId;
    private final String endId;
    private final int calcSleep;
    private String solution;
    private boolean solved;
    /**
     * Constructor Solver which provides enough information to solve a maze.
     *
     * @param graph
     * @param startId
     * @param endId
     * @param solverType
     */
    public Solver(Graph graph, String startId, String endId, String solverType) {
        this.graph = graph;
        this.solverType = solverType;
        this.startId = startId;
        this.endId = endId;
        this.calcSleep = calcCalcSleep();
        this.solution = "";
        this.solved = false;
        this.entries = new HashMap<>();
        Set<String> ids = graph.getIds();
        for (String id : ids) {
            SolverEntry thread = new SolverEntry(id, this);
            entries.put(id, thread);
        }
        this.startThreads();
    }

    /**
     * Static method for creating a Sovler from a Graph.
     *
     * @param graph
     * @return
     */
    public static Solver createSolver(Graph graph) {
        // creates random start and end cells on perimeter of maze
        int rowCells = graph.getRowCells();
        int startX;
        int startY;
        int endX;
        int endY;
        Random rand = new Random();
        int coinFlip = rand.nextInt(2);
        if (coinFlip == 0) {
            startX = 0;
            startY = rand.nextInt(rowCells);
            endX = rowCells - 1;
            endY = rand.nextInt(rowCells);
        } else {
            startX = rand.nextInt(rowCells);
            startY = 0;
            endX = rand.nextInt(rowCells);
            endY = rowCells - 1;
        }

        String startId = GraphVert.createId(startX, startY);
        String endId = GraphVert.createId(endX, endY);
        return new Solver(graph, startId, endId, graph.getSolverType());
    }

    public static void main(String[] args) {
    }

    protected Graph getGraph() {
        return graph;
    }

    protected synchronized boolean getSolved() {
        return solved;
    }

    protected synchronized void setSolved(boolean flag) {
        solved = flag;
    }

    protected synchronized String getSolution() {
        return solution;
    }

    protected String getStartId() {
        return startId;
    }

    protected String getEndId() {
        return endId;
    }

    protected String getSolverType() {
        return solverType;
    }

    protected void setSolverType(String solverType) {
        this.solverType = solverType;
    }

    protected int getCalcSleep() {
        return calcSleep;
    }

    protected int calcCalcSleep() {
        int numCells = graph.getColCells() * graph.getRowCells();
        int sleep = (int) (numCells / 2.0);
        return sleep;
    }

    protected void startThreads() {
        Set<String> ids = graph.getIds();
        for (String id : ids) {
            SolverEntry entry = entries.get(id);
            Thread thread = entry.getThread();
            thread.start();
        }
    }

    protected Search getSearchType(SolverEvent event) {
        String type = event.getType();
        if (type.equals("mouse")) {
            return Search.RANDOM;
        } else if (type.equals("wall")) {
            return Search.WALL;
        } else if (type.equals("wall_thread")) {
            return Search.MT_WALL;
        }

        return null;
    }

    protected void handleRandomEvent(String id, SolverEvent event) {
        if (!id.equals(endId)) {
            GraphVert vert = (GraphVert) graph.getComp(id);
            Map<Direction, String> edgeMap = vert.getEdgeMap();
            Direction curD = event.getDirection();
            Direction revD = Direction.getReverseDirection(curD);
            List<String> candidates = new ArrayList<>();
            for (Direction d : edgeMap.keySet()) {
                if (d != revD) {
                    String edge = edgeMap.get(d);
                    candidates.add(edge);
                }
            }

            if (candidates.size() == 0) {
                // sometimes you must backtrack
                String edge = edgeMap.get(revD);
                candidates.add(edge);
            }

            if (candidates.size() > 0) {
                Collections.shuffle(candidates);
                String candidate = candidates.get(0);
                GraphEdge edge = (GraphEdge) graph.getComp(candidate);
                String toId = edge.getNextVertId(id);
                Direction toD = edge.getNextDirection(id);
                String curPath = event.getPath();
                String nextPath = appendSearchPath(curPath, id);
                SolverEvent nextEvent = new SolverEvent(event.getType(), id, toId, toD, nextPath);
                Db.out(String.format("  nextEvent: %s", nextEvent.toString()));
                putEvent(toId, nextEvent);
            } else {
                Db.out(String.format("No candidate:", event.toString()));
            }
        } else {
            solved = true;
            solution = String.format("%s,%s", event.getPath(), id);
            Db.out(String.format("\nReached end: %s %s -- Game over!", id, solution));
        }
    }

    protected Direction getWallNextDirection(String id, Direction curD) {
        GraphVert vert = (GraphVert) graph.getComp(id);
        Direction wallD = Direction.getRightDirection(curD);
        Direction nextD;
        String edgeId;

        boolean hasRightWall = (vert.getEdge(wallD) == null);
        if (hasRightWall) {
            // try to go straight ahead
            edgeId = vert.getEdge(curD);
            if (edgeId != null) {
                nextD = curD;
            } else {
                // turn left and try again
                nextD = Direction.getLeftDirection(curD);
                edgeId = vert.getEdge(nextD);
                if (edgeId == null) {
                    // back up
                    nextD = Direction.getReverseDirection(curD);
                }
            }
        } else {
            // no right wall -- turn right
            nextD = wallD;
        }

        return nextD;
    }

    protected void handleWallEvent(String id, SolverEvent event) {
        if (!id.equals(endId)) {
            GraphVert vert = (GraphVert) graph.getComp(id);
            Direction curD = event.getDirection();
            Direction nextD = getWallNextDirection(id, curD);
            String edgeId = vert.getEdge(nextD);
            GraphEdge edge = (GraphEdge) graph.getComp(edgeId);

            String toId = edge.getNextVertId(id);
            Direction toD = edge.getNextDirection(id);
            String curPath = event.getPath();
            String nextPath = appendSearchPath(curPath, id);
            SolverEvent nextEvent = new SolverEvent(event.getType(), id, toId, toD, nextPath);
            Db.out(String.format("  nextEvent: %s", nextEvent.toString()));
            putEvent(toId, nextEvent);
        } else {
            solved = true;
            solution = String.format("%s,%s", event.getPath(), id);
            Db.out(String.format("\nReached end: %s %s -- Game over!", id, solution));
        }
    }

    protected String appendSearchPath(String path, String id) {
        String nextPath;
        if (path.length() == 0) {
            nextPath = id;
        } else {
            nextPath = String.format("%s,%s", path, id);
        }
        return nextPath;
    }

//    protected void handlePledgeEvent(String id, SolverEvent event) {
//        if (!id.equals(endId)) {
//            GraphVert vert = (GraphVert) graph.getComp(id);
//            Direction prefD = SolverEvent.getPrefD();
//            int turns = event.getTurns();
//            Direction curD = event.getDirection();
//            Direction nextD;
//            String edgeId;
//            if (turns == 0) {
//                nextD = prefD;
//            } else {
//                nextD = prefD;
//            }
//    }

    protected void handleEvent(String id, SolverEvent event) {
        Db.out(String.format("handleEvent: %s", event.toString()));
        Search search = getSearchType(event);
        switch (search) {
            case RANDOM:
                handleRandomEvent(id, event);
                break;

            case WALL:
                handleWallEvent(id, event);
                break;

            case MT_WALL:
                handleWallEvent(id, event);
                break;

//            case PLEDGE:
//                handlePledgeEvent(id, event);
//                break;
        }
    }

    protected void putEvent(String id, SolverEvent event) {
        SolverEntry entry = entries.get(id);
        ArrayBlockingQueue<SolverEvent> queue = entry.getQueue();
        try {
            if (!solved) {
                Db.out(String.format("\n    put %s %s", id, event.toString()));
                queue.put(event);
            }
        } catch (InterruptedException e) {
        }
    }

// take is handled at SolverEntry.run()
//    protected void takeEvent(String id, SolverEvent event) {
//    }

    public enum Search {RANDOM, MT_RANDOM, WALL, MT_WALL, PLEDGE}

}

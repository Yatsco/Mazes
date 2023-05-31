package mazes;

import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller manages game logic and communication between view
 * and data objects.
 *
 * @author Jack Trainor
 * @version CS_351_002 : 2021 - 03 - 29
 */
public class Controller {
    private final Stage stage;
    private View view;
    private Maze maze;
    private ArrayList<Maze> mazeList;
    private String filePath;
    private Graph graph;

    /**
     * Controller constructor
     */
    public Controller(Stage stage) throws IOException {
        this.stage = stage;
        initApp();
    }

    public static void main(String[] args) {
    }

    protected void setFilePath(String filePath) {
        this.filePath = Main.filePath;
    }

    protected Graph getGraph() {
        return graph;
    }

    protected void setGraph(Graph graph) {
        this.graph = graph;
    }

    protected void drawGraph() {
        view.redrawAll();
    }

    protected String getSolution(Graph graph, String startId,
                                 String endId, String searchType) {
        Solver solver = new Solver(graph, startId, endId, searchType);
        SolverEvent event = new SolverEvent(searchType,
                null, startId, Direction.S, "");
        solver.putEvent(startId, event);
        try {
            Thread.sleep(solver.getCalcSleep());
        } catch (InterruptedException e) {
        }
        String solution = solver.getSolution();
        Db.out(String.format("Solved: %b %s", solver.getSolved(), solution));
        return solution;
    }

    protected String getSolution(Solver solver) {

        return getSolution(solver, solver.getSolverType());
    }

    protected String getSolution(Solver solver, String solverType) {
        String startId = solver.getStartId();
        String endId = solver.getEndId();
        SolverEvent event = new SolverEvent(solverType,
                null, startId, Direction.S, "");
        solver.putEvent(startId, event);
        try {
            Thread.sleep(solver.getCalcSleep());
        } catch (InterruptedException e) {
        }
        String solution = solver.getSolution();
        Db.out(String.format("Solved: %b %s", solver.getSolved(), solution));
        return solution;
    }

    protected void animateConfigSolution() {
        String solverType = graph.getSolverType();
        if (solverType.equals("wall_thread")) {
            animateWallMtSoln();
        } else {
            Solver solver = Solver.createSolver(graph);
            String solution = getSolution(solver);
            view.animateSolution(solution, solver);
        }
    }

    protected void animateRandomSolution() {
        Solver solver = Solver.createSolver(graph);
        solver.setSolverType("mouse");
        String solution = getSolution(solver);
        view.animateSolution(solution, solver);
    }

    protected void animateWallSolution() {
        Solver solver = Solver.createSolver(graph);
        solver.setSolverType("wall");
        String solution = getSolution(solver);
        view.animateSolution(solution, solver);
    }

    protected void animateWallMtSoln() {
        int maxCoord = maze.getSize() - 1;

        String startId = "0000";
        String endId = String.format("%02d%02d", maxCoord, maxCoord);
        String searchType = "wall_thread";
        String solution = getSolution(graph, startId, endId, searchType);

        String startId2 = String.format("%02d%02d", 0, maxCoord);
        String endId2 = String.format("%02d%02d", maxCoord, 0);
        String solution2 = getSolution(graph, startId2, endId2, searchType);

        view.animateWallMtSolution(solution, solution2, graph);
    }

    protected void animatePledgeSoln() {
    }

    /**
     * Getters
     */
    protected Stage getStage() {
        return stage;
    }

    protected View getView() {
        return view;
    }

    protected Maze getMaze() {
        return maze;
    }

    public ArrayList<Maze> getMazeList() {
        return mazeList;
    }

    public void setMazeList(ArrayList<Maze> mazeList) {
        this.mazeList = mazeList;
    }

    /**
     * Checks to see if game is over.
     *
     * @return boolean flag for gameover.
     */
    protected boolean isGameOver() {
        return false;
    }

    protected void handleClickAt(double x, double y) {
        if (!isGameOver()) {
        }
    }

    protected void initMaze() throws IOException {
        /*maze = new Maze();*/
        setFilePath(filePath);
        Parser parser = new Parser(new File(filePath));
        parser.setParserValues();
        maze = new Maze(Maze.windowSize, Maze.cellSize);


        Generator mazeGen = new Generator(maze);


        //mazeGen.depthFirstSearch(maze);


        if (Maze.genType.equals("dfs")) {
            mazeGen.depthFirstSearch(maze);
            this.setMazeList(mazeGen.getDepthMazeList());

        } else if (Maze.genType.equals("kruskal")) {
            mazeGen.randomKruskal(maze);
            this.setMazeList(mazeGen.getKruskalMazeList());


        } else if (Maze.genType.equals("aldous")) {
            mazeGen.aldous(maze);
            this.setMazeList(mazeGen.getAldousMazeList());


        } else if (Maze.genType.equals("prim")) {
            System.out.println("Dont have this one");

        } else if (Maze.genType.equals("rec")) {
            //setMazeList();
            System.out.println("Dont have this one");

        }
    }

    protected void initApp() throws IOException {
        initMaze();
        View.setWindowHeight(Maze.windowSize + (Maze.cellSize / 10) + 50);
        View.setWindowWidth(Maze.windowSize + (Maze.cellSize / 10));
        view = new View(this);
        MazeConverter converter = new MazeConverter(maze);
        graph = converter.getGraph();
    }
}

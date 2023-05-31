package mazes;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;

/**
 * View creates and manages the MVC view of Maze.
 *
 * @author Jack Trainor
 * @version CS_351_002 : 2021 - 03 - 29
 */

public class View {
    private final static Color BG_COLOR = Color.WHITESMOKE;
    private final static Color WHITE_TEXT_COLOR = Color.WHITE;
    private final static Color BLACK_TEXT_COLOR = Color.BLACK;
    private final static Color WALL_COLOR = Color.BLACK;
    private final static Color MAZE_COLOR = Color.WHITE;
    private final static Color WALL_THREAD_COLOR_1 = Color.PINK;
    private final static Color WALL_THREAD_COLOR_2 = Color.LIGHTBLUE;
    private static double WINDOW_WIDTH = 900;
    private static double WINDOW_HEIGHT = 900;
    private final Controller controller;
    private Stage stage;
    private Canvas canvas;
    private Pane pane;

    private Maze maze;
    private int counter = 0;
    private ArrayList<Maze> mazeList;
    AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (counter >= controller.getMazeList().size()) {
                counter = 0;
                animationTimer.stop();
            } else {
                animateMaze();
                counter++;
            }
        }
    };
    private Button animateConfigSolnBtn;
    private Button animateRandomSolnBtn;
    private Button animateWallSolnBtn;
    private Button animateWallMtSolnBtn;
    private Button animatePledgeBtn;
    private Button animateMazeBtn;

    /**
     * GuiView constructor
     */
    public View(Controller controller) {
        this.controller = controller;
        this.stage = controller.getStage();
        initUI(this.stage);
    }

    public static void setWindowHeight(double windowHeight) {
        WINDOW_HEIGHT = windowHeight;
    }

    public static void setWindowWidth(double windowWidth) {
        WINDOW_WIDTH = windowWidth;
    }

    public static void main(String[] args) {
    }

    private void drawLine(GraphicsContext gc, Line line, Color color) {
        double startX = line.getStartX();
        double startY = line.getStartY();
        double endX = line.getEndX();
        double endY = line.getEndY();
        gc.setStroke(color);
        gc.strokeLine(startX, startY, endX, endY);
    }

    private void drawRect(GraphicsContext gc, Rectangle rect, Color color) {
        double left = rect.getX();
        double top = rect.getY();
        double width = rect.getWidth();
        double height = rect.getHeight();
        gc.setStroke(color);
        gc.strokeRect(left, top, width, height);
    }

    private void fillRect(GraphicsContext gc, Rectangle rect, Color color) {
        double left = rect.getX();
        double top = rect.getY();
        double width = rect.getWidth();
        double height = rect.getHeight();
        gc.setFill(color);
        gc.fillRect(left, top, width, height);
    }

    protected void drawText(GraphicsContext gc, Color color, int size, String text, int x, int y) {
        gc.setFill(color);
        gc.setFont(new Font("Courier", size));
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setTextBaseline(VPos.TOP);
        gc.fillText(text, x, y);
    }

    protected void paintMaze(Maze maze, GraphicsContext gc) {
        for (int i = 0; i < maze.size; i++) {
            for (int j = 0; j < maze.size; j++) {

                paintCell(maze.getCell(i, j), gc, j * (Maze.cellSize), i * (Maze.cellSize));


            }


        }
    }

    protected void animateMaze() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Rectangle canvasRect = new Rectangle(0.0, 0.0, canvas.getWidth(),
                canvas.getHeight());
        fillRect(gc, canvasRect, BG_COLOR);


        // draw maze
        maze = controller.getMaze();

        setMazeList(controller.getMazeList());
        int endSize = mazeList.size();
        //System.out.println(endSize);
        paintMaze(mazeList.get(counter), gc);
        //animationTimer.stop();



        /*maze.setGenType("Aldous");
        if (maze.genType.equals("Aldous")){
            controller.getMazeList();
            System.out.println(counter);
            int endSize = controller.getMazeList().get(counter).size;
            if (counter>endSize){
                for (int i = 0; i < endSize; i++) {
                    for (int j = 0; j < endSize; j++) {
                        //System.out.println(i+"  "+j);

                        paintCell(controller.getMazeList().get(endSize-1).getCell(i,j),gc,j*50,i*50,50);



                    }


                }
                return;

            }
            for (int i = 0; i < controller.getMazeList().
                    get(counter).size; i++) {
                for (int j = 0; j < controller.getMazeList().
                        get(counter).size; j++) {
                    System.out.println("hello");


                    paintCell(controller.getMazeList().
                            get(counter).getCell(i,j),gc,j*50,i*50,50);




                }


            }



        }*/

    }

    protected void setMazeList(ArrayList<Maze> mazeList) {
        this.mazeList = mazeList;
    }

    protected void drawMaze(GraphicsContext gc) {
        // draw maze
        maze = controller.getMaze();

        for (int i = 0; i < maze.size; i++) {
            for (int j = 0; j < maze.size; j++) {
                //System.out.println(i+"  "+j);

                paintCell(maze.getCell(i, j), gc, j * Maze.cellSize, i * Maze.cellSize);


            }


        }
    }

    protected void drawBackground(GraphicsContext gc) {
        Rectangle canvasRect = new Rectangle(0.0, 0.0, canvas.getWidth(), canvas.getHeight());
        fillRect(gc, canvasRect, BG_COLOR);
    }

    protected void drawGraph(GraphicsContext gc) {
        Graph graph = controller.getGraph();
        if (graph != null) {
            drawGraph(gc, graph);
            for (String id : graph.getIds()) {
                GraphComp comp = graph.getComp(id);
                if (comp instanceof GraphEdge) {
                    GraphEdge edge = (GraphEdge) comp;
                    Line edgeLine = graph.graphEdgeToLine(edge);
                    drawLine(gc, edgeLine, Color.GRAY);
                }
            }
        }
    }

    /**
     * Redraws all elements of mazes window.
     */
    protected void redrawAll() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawBackground(gc);
        drawMaze(gc);
        drawGraph(gc);
    }

    private void paintCell(Cell c, GraphicsContext gc, double x, double y) {
        int cellSize = Maze.cellSize;
        int wallSize = cellSize / 10;
        Rectangle cell = new Rectangle(x, y, cellSize, cellSize);
        Color cellColor = new Color(0, 0, 0, 1);
        //c.setVisited(true);
        //c.getWalls().setNorthWall(false);
       /* System.out.println(c.getWalls().getNorthWall());
        System.out.println(c.getWalls().getEastWall());
        System.out.println(c.getWalls().getSouthWall());
        System.out.println(c.getWalls().getWestWall());*/

        if (c.getVisited()) {
            //cellColor = Color.PALEGOLDENROD;
            cellColor = MAZE_COLOR;
            fillRect(gc, cell, cellColor);

            if (c.getWalls().getNorthWall()) {
                Rectangle wall = new Rectangle(x, y, cellSize, cellSize / 10);
                fillRect(gc, wall, WALL_COLOR);

            }
            if (c.getWalls().getEastWall()) {
                Rectangle wall = new Rectangle(x + cellSize, y, cellSize / 10, cellSize);
                fillRect(gc, wall, WALL_COLOR);

            }
            if (c.getWalls().getSouthWall()) {
                Rectangle wall = new Rectangle(x, y + cellSize, cellSize, cellSize / 10);
                fillRect(gc, wall, WALL_COLOR);

            }
            if (c.getWalls().getWestWall()) {
                Rectangle wall = new Rectangle(x, y, cellSize / 10, cellSize);
                fillRect(gc, wall, WALL_COLOR);

            }


        } else {
            fillRect(gc, cell, cellColor);

            if (c.getWalls().getNorthWall()) {
                Rectangle wall = new Rectangle(x, y, cellSize, cellSize / 10);
                fillRect(gc, wall, WALL_COLOR);

            }
            if (c.getWalls().getEastWall()) {
                Rectangle wall = new Rectangle(x + cellSize, y, cellSize / 10, cellSize);
                fillRect(gc, wall, WALL_COLOR);

            }
            if (c.getWalls().getSouthWall()) {
                Rectangle wall = new Rectangle(x, y + cellSize, cellSize, cellSize / 10);
                fillRect(gc, wall, WALL_COLOR);

            }
            if (c.getWalls().getWestWall()) {
                Rectangle wall = new Rectangle(x, y, cellSize / 10, cellSize);
                fillRect(gc, wall, WALL_COLOR);

            }


        }


    }

    /*  private void drawCell(Cell c,GraphicsContext gc,int x, int y, int size){
          Rectangle cell = new Rectangle(size,size);
          fillRect(gc,cell,Color.GREEN);
          Line nWall = c.getWalls().setWallLine(c.getWalls().getNorthWall(),
                  (int)cell.getX(),(int)cell.getX()+size,false);

          Line eWall = c.getWalls().setWallLine(c.getWalls().getEastWall(),
                  (int)cell.getY()+size       ,(int)cell.getY()+size,true);

          c.getWalls().getEastWall();
          c.getWalls().getSouthWall();
          c.getWalls().getWestWall();




      }*/
    protected int getMouseSize(Graph graph) {
        return graph.getCellHeight() / 2 - 2;
    }

    protected Color getMouseColor(String solverType) {
        if (solverType.equals("mouse")) {
            return Color.LIGHTSALMON;
        } else if (solverType.equals("wall")) {
            return Color.LIGHTSEAGREEN;
        } else {
            return Color.BLACK;
        }
    }

    protected Path getSolutionPath(String solution, Graph graph) {
        String[] ids = solution.split(",");
        Path path = new Path();
        boolean first = true;
        for (String id : ids) {
            GraphVert vert = (GraphVert) graph.getComp(id);
            if (vert == null) {
                Db.out("getSolutionPath failed.");
            }
            Point point = vert.getPoint();
            Point2D windowPoint = graph.cellPointToWindowPoint(point);
            if (first) {
                path.getElements().add(new MoveTo(windowPoint.getX(), windowPoint.getY()));
                first = false;
            } else {
                path.getElements().add(new LineTo(windowPoint.getX(), windowPoint.getY()));
            }
        }
        return path;
    }

    protected PathTransition getSolutionPathTransiton(Circle circle, Path path, int millis) {
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(millis));
        pathTransition.setNode(circle);
        pathTransition.setPath(path);

        return pathTransition;
    }

    protected int getMillisPerId(int numIds) {
        return 50 * numIds;
    }

    protected void animateWallMtSolution(String solution, String solution2, Graph graph) {
        String[] ids = solution.split(",");
        int mouseSize = getMouseSize(graph);
        int millis = getMillisPerId(ids.length);

        String[] ids2 = solution2.split(",");
        int millis2 = getMillisPerId(ids2.length);

        Circle circle = new Circle(mouseSize, WALL_THREAD_COLOR_1);
        Circle circle2 = new Circle(mouseSize, WALL_THREAD_COLOR_2);

        Path path = getSolutionPath(solution, graph);
        PathTransition pathTransition = getSolutionPathTransiton(circle, path, millis);

        Path path2 = getSolutionPath(solution2, graph);
        PathTransition pathTransition2 = getSolutionPathTransiton(circle2, path2, millis2);

        pane.getChildren().addAll(circle, circle2);
        pathTransition.play();
        pathTransition2.play();
    }

    protected void animateSolution(String solution, Solver solver) {
        Graph graph = solver.getGraph();
        String[] ids = solution.split(",");

        int mouseSize = getMouseSize(graph);
        Circle circle = new Circle(mouseSize, getMouseColor(solver.getSolverType()));

        Path path = getSolutionPath(solution, graph);
        int millis = getMillisPerId(ids.length);
        PathTransition pathTransition = getSolutionPathTransiton(circle, path, millis);

        pane.getChildren().add(circle);
        pathTransition.play();
    }

    protected void animatePledgeSolution(String solution, Solver solver) {
//        String[] ids = solution.split(",");
//        Circle circle = new Circle(16, Color.LIGHTSEAGREEN);
//
//        Path path = getSolutionPath(solution, graph);
//        int millis = 100 * ids.length;
//        PathTransition pathTransition = getSolutionPathTransiton(circle, path, millis);
//
//        pane.getChildren().add(circle);
//        pathTransition.play();
    }

    protected void drawGraph(GraphicsContext gc, Graph graph) {
        for (String id : graph.getIds()) {
            GraphComp comp = graph.getComp(id);
            if (comp instanceof GraphEdge) {
                GraphEdge edge = (GraphEdge) comp;
                Line edgeLine = graph.graphEdgeToLine(edge);
                drawLine(gc, edgeLine, Color.GRAY);
            }
        }
    }

    protected void initUI(Stage stage) {
        this.stage = stage;

        pane = new Pane();
        this.canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        pane.getChildren().add(canvas);
        Scene scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT, BG_COLOR);

        // add controls here

        animateConfigSolnBtn = new Button();
        animateConfigSolnBtn.setText("Config");
        animateConfigSolnBtn.setMinHeight(24);
        animateConfigSolnBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.animateConfigSolution();
            }
        });


        animateRandomSolnBtn = new Button();
        animateRandomSolnBtn.setText("Mouse");
        animateRandomSolnBtn.setMinHeight(24);
        animateRandomSolnBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.animateRandomSolution();
            }
        });

        animateWallSolnBtn = new Button();
        animateWallSolnBtn.setText("Wall");
        animateWallSolnBtn.setMinHeight(24);
        animateWallSolnBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.animateWallSolution();
            }
        });

        animateWallMtSolnBtn = new Button();
        animateWallMtSolnBtn.setText("Wall Thread");
        animateWallMtSolnBtn.setMinHeight(24);
        animateWallMtSolnBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.animateWallMtSoln();

            }
        });

        animatePledgeBtn = new Button();
        animatePledgeBtn.setText("Pledge");
        animatePledgeBtn.setMinHeight(24);
        animatePledgeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.animatePledgeSoln();
            }
        });

        animateMazeBtn = new Button();
        animateMazeBtn.setText("Animate Maze");
        animateMazeBtn.setMinHeight(24);
        animateMazeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                animationTimer.start();
                //counter++;
            }
        });

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!controller.isGameOver()) {
                    double x = event.getSceneX();
                    double y = event.getSceneY();
                    controller.handleClickAt(x, y);
                }
            }
        });

        HBox hbox = new HBox(10);
        hbox.setLayoutX(0);
        hbox.setLayoutY(WINDOW_HEIGHT - 24);
        hbox.getChildren().addAll(animateConfigSolnBtn, animateRandomSolnBtn, animateWallSolnBtn, animateWallMtSolnBtn, animateMazeBtn);
        pane.getChildren().add(hbox);

        redrawAll();

        //counter++;

        stage.setTitle("Mazes");
        stage.setScene(scene);
        stage.show();
    }
}

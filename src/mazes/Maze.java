package mazes;

public class Maze {
    static int windowSize;
    static int cellSize;
    static String genType;
    static String solverType;
    int size;
    Cell[][] maze;
    private Cell startCell;
    private Cell endCell;


    protected Maze(int size) {
        //this.maze = maze;
        this.size = size;
        this.maze = new Cell[size][size];


    }


    protected Maze(int windowSize, int cellSize) {
        this.size = windowSize/cellSize;
        this.maze = new Cell[size][size];
        /*Maze.genType = genType;
        Maze.solverType = solverType;
        Maze.cellSize = cellSize;*/

    }

    /**
     * deep copy constructor
     * @param maze maze to copy
     */
    public Maze(Maze maze) {

        this.size = maze.size;
        this.maze = new Cell[size][size];
        for (int i = 0; i < maze.size; i++) {
            for (int j = 0; j < maze.size; j++) {
                this.maze[i][j] = new Cell(maze.getCell(i, j));

            }


        }


    }

    protected int getSize() {
        return size;
    }


    /**
     * fills the maze with cells
     */
    public void fillMaze() {
        int counter = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                maze[row][col] = new Cell(row, col, counter);
                counter++;


            }

        }

    }

    protected void printMaze() {
        String line = "";
        for (int row = 0; row < maze.length; row++) {

            for (int col = 0; col < maze.length; col++) {
                line += maze[row][col].getId();


            }
            System.out.println(line);
            line = "";

        }

    }

    /**
     * gets a cell on the board given a row and col and returns the given cell
     */
    public Cell getCell(int row, int col) {
        return maze[row][col];

    }


}

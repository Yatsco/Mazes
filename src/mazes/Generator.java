package mazes;

import java.util.*;

public class Generator {

    private final Maze maze;
    private List<Cell> allCellList;
    private final ArrayList<Maze> depthMazeList;
    private final ArrayList<Maze> kruskalMazeList;
    private final ArrayList<Maze> primMazeList;
    private final ArrayList<Maze> aldousMazeList;
    private int counter = 0;
    //private Stack<Cell> mazeCells;


    protected Generator(Maze maze) {
        this.maze = maze;
        Stack<Cell> mazeCells = new Stack<>();
        maze.fillMaze();
        depthMazeList = new ArrayList<>();
        kruskalMazeList = new ArrayList<>();
        primMazeList = new ArrayList<>();
        aldousMazeList = new ArrayList<>();


    }


    public static void main(String[] args) {
        /*Maze maze = new Maze(6);
        Generator generator = new Generator(maze);
        //generator.getMaze().fillMaze();
        generator.depthFirstSearch(generator.getMaze());
        generator.printMaze(maze);*/

    }


    protected List<Cell> validStartEndCells() {
        List<Cell> validCells = new ArrayList<>();
        for (int i = 0; i < maze.size; i++) {
            for (int j = 0; j < maze.size; j++) {
                if (i == 0 || i == maze.size - 1 || j == 0 ||
                        j == maze.size - 1) {
                    validCells.add(maze.getCell(i, j));

                }

            }

        }

        Collections.shuffle(validCells);

        return validCells;

    }

    protected Cell endCell(List<Cell> startEndCell, Cell start) {
        for (int i = 0; i < startEndCell.size(); i++) {
            if (start.getRow() == 0) {
                if (startEndCell.get(i).getRow() == 0) {
                    startEndCell.remove(i);

                }
            } else if (start.getCol() == 0) {
                if (startEndCell.get(i).getCol() == 0) {
                    startEndCell.remove(i);

                }
            }


        }
        return startEndCell.get(0);


    }

    protected Cell getStartEnd(Cell c) {
        if (c.getRow() == 0) {
            if (c.getCol() == 0) {

                return c;

            } else if (c.getCol() == getMaze().size - 1) {
                //c.getWalls().setEastWall(false);
                return c;


            } else {
                //c.getWalls().setNorthWall(false);
                return c;


            }

        } else if (c.getRow() == getMaze().size - 1) {
            if (c.getCol() == 0) {
                //c.getWalls().setEastWall(false);
                return c;


            } else if (c.getCol() == getMaze().size - 1) {
                // c.getWalls().setWestWall(false);
                return c;


            } else {
                // c.getWalls().setSouthWall(false);
                return c;


            }

        } else {
            if (c.getCol() == 0) {
                // c.getWalls().setWestWall(false);
                return c;

            } else {
                // c.getWalls().setEastWall(false);
                return c;

            }
        }
    }

    /**
     * Generates the maze with depth first search algorithm
     *
     * @param maze the maze to gen
     */
    public void depthFirstSearch(Maze maze) {
        Stack<Cell> mazeCells = new Stack<>();
        List<Cell> validCells = validStartEndCells();
        Cell startCell = getStartEnd(validCells.get(0));


        mazeCells.push(getStartEnd(validCells.get(0)));
        validCells.remove(0);
        Cell current;


        while (!mazeCells.empty()) {
            counter++;
            if (maze != null) {
                if (maze.size >= 50) {
                    if (counter % (maze.size / 10) == 0) {
                        depthMazeList.add(new Maze(maze));
                        counter = 0;

                    }

                } else {
                    depthMazeList.add(new Maze(maze));

                }


            }
            current = mazeCells.pop();

            current.setVisited(true);
            List<Cell> unvisitedNeighbors = findUnvisitedNeighbors(current);
            if (!unvisitedNeighbors.isEmpty()) {
                mazeCells.push(current);
                Collections.shuffle(unvisitedNeighbors);
                Cell neighbor = unvisitedNeighbors.get(0);

                removeWalls(current, neighbor);
                neighbor.setVisited(true);
                mazeCells.push(neighbor);

            }


        }
        depthMazeList.add(maze);
        mazeCells.push(getStartEnd(validCells.get(0)));
        validCells.remove(0);

    }

    protected void removeWalls(Cell cell, Cell other) {
        if (cell.getRow() != other.getRow()) {
            if (cell.getRow() > other.getRow()) {
                cell.getWalls().setNorthWall(false);
                other.getWalls().setSouthWall(false);


            } else {
                cell.getWalls().setSouthWall(false);
                other.getWalls().setNorthWall(false);
            }


        }
        if (cell.getCol() != other.getCol()) {
            if (cell.getCol() > other.getCol()) {
                cell.getWalls().setWestWall(false);
                other.getWalls().setEastWall(false);


            } else {
                cell.getWalls().setEastWall(false);
                other.getWalls().setWestWall(false);

            }

        }

    }

    /**
     * helper method that checks the neighbors of a given cell
     *
     * @param c         cell
     * @param rowOffset the offsets
     * @param colOffset the offsets
     * @return the neighbor cell
     */
    public Cell checkNeighbor(Cell c, int rowOffset, int colOffset) {
        int row = c.getRow() + rowOffset;
        int col = c.getCol() + colOffset;
        try {
            return maze.getCell(row, col);

        } catch (ArrayIndexOutOfBoundsException e) {
            return null;

        }

    }

    protected boolean isVisited(Cell c) {
        if (c != null) {
            return c.getVisited();
        }
        return true;

    }

    /**
     * finds all unvisited neighbors
     *
     * @param c given cell
     * @return list of all unvisited neighbors
     */
    public List<Cell> findUnvisitedNeighbors(Cell c) {
        List<Cell> unvisitedCells = new ArrayList<>();
        //North
        Cell neighbor = checkNeighbor(c, -1, 0);
        if (!isVisited(neighbor)) {
            unvisitedCells.add(neighbor);

        }
        //East
        neighbor = checkNeighbor(c, 0, 1);
        if (!isVisited(neighbor)) {
            unvisitedCells.add(neighbor);

        }
        //South
        neighbor = checkNeighbor(c, 1, 0);
        if (!isVisited(neighbor)) {
            unvisitedCells.add(neighbor);

        }

        //West
        neighbor = checkNeighbor(c, 0, -1);
        if (!isVisited(neighbor)) {
            unvisitedCells.add(neighbor);

        }
        return unvisitedCells;


    }

    protected void printMaze(Maze maze) {
        for (int i = 0; i < maze.size; i++) {
            for (int j = 0; j < maze.size; j++) {
                if (maze.getCell(i, j).getVisited()) {
                    System.out.println("Row " + i + " Col " + j);

                }

            }

        }

    }
    /*public boolean checkVisited(Maze maze) {
        for (int i = 0; i < maze.size; i++) {
            for (int j = 0; j < maze.size; j++) {
                if (maze.getCell(i, j).getVisited()) {

                }

            }

        }
    }*/

    protected Maze getMaze() {
        return maze;
    }

    /**
     * generates the maze with the kruskal algorithm
     *
     * @param maze the maze to gen
     */
    public void randomKruskal(Maze maze) {
        List<SmartWalls> cellWalls = new ArrayList<>();
        List<Set<Cell>> setList = new ArrayList<>();
        Map<Cell, Set> cellMap = new HashMap();
        for (int i = 0; i < maze.size; i++) {
            for (int j = 0; j < maze.size; j++) {
                Set<Cell> cellSet = new HashSet<>();
                Cell current = maze.getCell(i, j);
                cellSet.add(current);
                cellMap.put(current, cellSet);
                setList.add(cellSet);
                try {

                    Cell cellRight = maze.getCell(i, j + 1);
                    cellWalls.add(new SmartWalls(current, cellRight,
                            true));


                } catch (ArrayIndexOutOfBoundsException e) {


                }

                try {

                    Cell cellDown = maze.getCell(i + 1, j);
                    cellWalls.add(new SmartWalls(current, cellDown,
                            false));

                } catch (ArrayIndexOutOfBoundsException e) {

                }

            }

        }
        Collections.shuffle(cellWalls);
        ListIterator<SmartWalls> wallIterator = cellWalls.listIterator();

        while ((setList.size() > 1)) {

            SmartWalls currentWall = wallIterator.next();

            if (cellMap.get(currentWall.getFirst()) !=
                    cellMap.get(currentWall.getOther())) {
                currentWall.getFirst().setVisited(true);
                currentWall.getOther().setVisited(true);
                Set<Cell> firstSet = cellMap.get(currentWall.getFirst());
                Set<Cell> otherSet = cellMap.get(currentWall.getOther());
                firstSet.addAll(otherSet);
                for (Cell c : otherSet) {
                    cellMap.replace(c, firstSet);
                }
                setList.remove(otherSet);
                if (currentWall.isHorizontal()) {


                    currentWall.getFirst().getWalls().setEastWall(false);
                    currentWall.getOther().getWalls().setWestWall(false);

                } else {
                    currentWall.getFirst().getWalls().setSouthWall(false);
                    currentWall.getOther().getWalls().setNorthWall(false);

                }


            }
            counter++;
            if (maze != null) {
                if (maze.size >= 50) {
                    if (counter % (maze.size / 10) == 0) {
                        kruskalMazeList.add(new Maze(maze));
                        counter = 0;

                    }

                } else {
                    kruskalMazeList.add(new Maze(maze));

                }


            }


        }
        kruskalMazeList.add(maze);


    }

    protected void prim(Maze maze) {

    }

    /**
     * getters
     *
     * @return
     */
    public ArrayList<Maze> getAldousMazeList() {
        return aldousMazeList;
    }

    /**
     * getters
     *
     * @return
     */
    public ArrayList<Maze> getDepthMazeList() {
        return depthMazeList;
    }

    /**
     * getters
     *
     * @return
     */
    public ArrayList<Maze> getKruskalMazeList() {
        return kruskalMazeList;
    }

    /**
     * getters
     *
     * @return
     */
    public ArrayList<Maze> getPrimMazeList() {
        return primMazeList;
    }

    /**
     * gets a random cell on a maze
     *
     * @return the random cell
     */
    public Cell randomCell() {
        List<Cell> randomCellList = new ArrayList<>();
        for (int i = 0; i < maze.size; i++) {
            for (int j = 0; j < maze.size; j++) {
                randomCellList.add(maze.getCell(i, j));


            }

        }
        allCellList = randomCellList;
        Collections.shuffle(randomCellList);
        return randomCellList.get(0);

    }

    /**
     * creates a list of neighbors given a cell
     *
     * @param c the cell
     * @return list of neighbors
     */
    public List<Cell> neighborList(Cell c) {
        List<Cell> neighborList = new ArrayList<>();
        if (checkNeighbor(c, 0, 1) != null) {
            neighborList.add(checkNeighbor(c, 0, 1));

        }
        if (checkNeighbor(c, 1, 0) != null) {
            neighborList.add(checkNeighbor(c, 1, 0));

        }
        if (checkNeighbor(c, 0, -1) != null) {
            neighborList.add(checkNeighbor(c, 0, -1));

        }
        if (checkNeighbor(c, -1, 0) != null) {
            neighborList.add(checkNeighbor(c, -1, 0));

        }
        return neighborList;

    }


    /**
     * generates a maze with the Aldous method
     *
     * @param maze
     */
    public void aldous(Maze maze) {
        Cell current = randomCell();
        while (allCellList.size() > 0) {

            current.setVisited(true);
            List<Cell> neighborList = neighborList(current);
            Collections.shuffle(neighborList);
            Cell neighborCell = neighborList.get(0);
            if (!neighborCell.getVisited()) {


                neighborCell.setVisited(true);
                removeWalls(current, neighborCell);
                allCellList.remove(current);
                current = neighborCell;


            } else {
                allCellList.remove(current);
                current = neighborCell;

            }
            counter++;
            if (maze != null) {
                if (maze.size >= 50) {
                    if (counter % (maze.size / 10) == 0) {
                        aldousMazeList.add(new Maze(maze));
                        counter = 0;

                    }

                } else {
                    aldousMazeList.add(new Maze(maze));

                }


            }


        }
        aldousMazeList.add(maze);


    }


}


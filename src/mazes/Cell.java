package mazes;

import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

public class Cell {
    private int row;
    private int col;
    private int id;
    private Walls walls;
    private HBox cellBox;
    private Line wall;
    private boolean visited;

    protected Cell(int row, int col, int id){
        this.row = row;
        this.col = col;
        this.id = id;
        this.visited = visited;

        this.walls = new Walls(true,true,
                true,true);

    }

    /**
     * deep copy constructor for cell
     * @param c cell to copy
     */
    public Cell(Cell c){
        this.row = c.getRow();
        this.col = c.getCol();
        this.id = c.getId();
        this.visited = c.getVisited();

        this.walls = new Walls(c.getWalls());

    }


    protected void setVisited(boolean visited) {
        this.visited = visited;
    }

    protected boolean getVisited() {
        return visited;

    }

    protected int getRow() {
        return row;
    }

    protected int getCol() {
        return col;
    }

    protected Walls getWalls() {
        return walls;
    }



    protected int getId() {
        return id;
    }



}

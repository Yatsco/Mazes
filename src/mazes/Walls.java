package mazes;

public class Walls {
    private boolean northWall;
    private boolean eastWall;
    private boolean southWall;
    private boolean westWall;

    protected Walls(boolean northWall, boolean eastWall, boolean southWall,boolean westWall){

         this.northWall = northWall;
         this.eastWall = eastWall;
         this.southWall = southWall;
         this.westWall = westWall;



    }

    /**
     * deep copy constructor for walls
     * @param walls walls to copy
     */
    public Walls(Walls walls){
        this.northWall = walls.getNorthWall();
        this.eastWall = walls.getEastWall();
        this.southWall = walls.getSouthWall();
        this.westWall = walls.getWestWall();
    }
    protected void setNorthWall(boolean northWall) {
        this.northWall = northWall;
    }

    protected void setEastWall(boolean eastWall) {
        this.eastWall = eastWall;
    }

    protected void setSouthWall(boolean southWall) {
        this.southWall = southWall;
    }

    protected void setWestWall(boolean westWall) {
        this.westWall = westWall;
    }

    protected boolean getNorthWall() {
        return northWall;
    }


    protected boolean getEastWall() {
        return eastWall;
    }

    protected boolean getSouthWall() {
        return southWall;
    }

    protected boolean getWestWall() {
        return westWall;
    }





}

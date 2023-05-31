package mazes;

/**
 * GraphComp is the base class for GraphEdge and GraphVert.
 *
 * @author Jack Trainor
 * @version CS_351_002 : 2021 - 03 - 29
 */

public class GraphComp implements Runnable {
    protected String id;

    /**
     * Constructor for GraphComp
     */
    public GraphComp() {
    }

    protected String getId() {
        return id;
    }

    protected void setId(String id) {
        this.id = id;
    }

    protected String toDb() {
        return id;
    }

    @Override
    public void run() {
    }
}

package mazes;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * SolverEntry provides a blocking queue for each Graph element plus a thread
 * for taking events from other Graph elements.
 *
 * @author Jack Trainor
 * @version CS_351_002 : 2021 - 03 - 29
 */

public class SolverEntry implements Runnable {
    private final String id;
    private final Solver solver;
    private final ArrayBlockingQueue<SolverEvent> queue;
    private final Thread thread;

    /**
     * Constructor for SolverEntry.
     *
     * @param id
     * @param solver
     */
    public SolverEntry(String id, Solver solver) {
        this.id = id;
        this.solver = solver;
        this.queue = new ArrayBlockingQueue<SolverEvent>(1, true);
        this.thread = new Thread(this);
    }

    public static void main(String[] args) {
    }

    protected Thread getThread() {
        return thread;
    }

    protected ArrayBlockingQueue<SolverEvent> getQueue() {
        return queue;
    }

    /**
     * Thread override run() which handles taking SolverEntry's from blockingqueue.
     */
    @Override
    public void run() {

        try {
            while (!solver.getSolved()) {
                SolverEvent event = queue.take();
                Db.out(String.format("   take %s %s", id, event.toString()));
                solver.handleEvent(id, event);
            }
        } catch (InterruptedException e) {
        }
    }
}

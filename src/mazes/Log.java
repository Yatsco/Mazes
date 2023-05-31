package mazes;

/**
 * Log provides println calls to out and err while allowing the possibility of
 * turning off these println calls.
 * <p>
 * Log is for debugging. When plyCount >= 4, displaying debugging info can
 * noticeably degrade performance.
 *
 * @author Jack Trainor
 * @version CS_251_002 : 2021 - 03 - 29
 */
public class Log {
    private static boolean on = true;

    /**
     * Enables logging.
     *
     * @return Nothing
     */
    protected static void enable() {
        on = true;
    }

    /**
     * Disables logging.
     *
     * @return Nothing
     */
    protected static void disable() {
        on = false;
    }

    /**
     * Sends string to System.out
     *
     * @param s String sent to System.out
     * @return Nothing
     */
    protected static void out(String s) {
        if (on) {
            System.out.println(s);
        }
    }

    /**
     * Sends string to System.err
     *
     * @param s String sent to System.err
     * @return Nothing
     */
    protected static void err(String s) {
        if (on) {
            System.err.println(s);
        }
    }
}

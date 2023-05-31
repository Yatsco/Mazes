package mazes;

/**
 * Db handles debug messages. Can be turned on and off programmatically.
 *
 * @author Jack Trainor
 * @version CS_351_002 : 2021 - 03 - 29
 */
public class Db {
    private static boolean dbOn = true;

    protected static void setDbOn(boolean b) {
        dbOn = b;
    }

    protected static void out(String s) {
        if (dbOn) {
            System.out.println(s);
        }
    }

}

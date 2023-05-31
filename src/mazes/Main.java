package mazes;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main is a JavaFx application which launches a maze.
 * as a GUI app.
 *
 * @author Jack Trainor
 * @version CS_351_002 : 2021 - 02 - 12
 */
public class Main extends Application {
    public static String filePath = null;
    private static String[] args;
    private Controller controller;

    /**
     * Constructor for Main
     */
    public Main() {
        Db.setDbOn(false);
    }

    public static void main(String[] args) {
        Main.args = args; // so args are available at start()
        filePath = args[0];
        launch(args);
    }

    /**
     * This is a callback from JavaFx to start Main.
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) throws IOException {
        controller = new Controller(stage);
        View view = controller.getView();
    }
}

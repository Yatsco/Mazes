package mazes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
    File file;
    int windowSize;
    int cellSize;
    String mazeGenStr;
    String solveMethodStr;

    public Parser(File file) {
        this.file = file;

    }

    /**
     * sets up the parser values for the config file
     *
     * @throws IOException if file doesnt exist
     */
    public void setParserValues() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(this.file));
        String line = br.readLine();
        int counter = 0;
        while (line != null) {
            if (counter == 0) {
                Maze.windowSize = Integer.parseInt(line);
                //maze.setWindowSize(Integer.parseInt(line));
                /*this.windowSize = Integer.parseInt(line);
                System.out.println(this.windowSize);*/
            }
            if (counter == 1) {
                Maze.cellSize = Integer.parseInt(line);
                /*System.out.println(this.windowSize);*/
            }
            if (counter == 2) {
                Maze.genType = (line);
                /*this.mazeGenStr = line;
                System.out.println(this.mazeGenStr);*/
            }
            if (counter == 3) {
                Maze.solverType = (line);
               /* this.solveMethodStr = line;
                System.out.println(this.solveMethodStr);*/

            }
            line = br.readLine();
            counter++;
            if (counter == 4) {
                line = br.readLine();
                br.close();
            }


        }


    }

 /*   public int getCellSize() {
        return cellSize;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public String getMazeGenStr() {
        return mazeGenStr;
    }

    public String getSolveMethodStr() {
        return solveMethodStr;
    }*/
}

package mazes;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Utils is a collection of static utility functions, usually on standard Java data objects,
 * for use throughout program and for later reuse.
 *
 * @author Jack Trainor
 * @version CS_351_002 : 2021 - 03 - 29
 */

public class Utils {
    protected static String readTextFile(String path) {
        File file = new File(path);
        String separator = System.getProperty("line.separator");
        StringBuilder contents = new StringBuilder();
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            try {
                String line = null;
                while ((line = input.readLine()) != null) {
                    contents.append(line);
                    contents.append(separator);
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents.toString();
    }

    protected static void writeTextFile(String path, String text) {
        File file = new File(path);
        try {
            Writer output = new BufferedWriter(new FileWriter(file));
            try {
                output.write(text);
            } finally {
                output.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static LinkedList<String> toLines(String text) {
        LinkedList<String> stringList = new LinkedList<>();
        text = text.replaceAll("\r", "");
        String[] strings = text.split("\n");
        stringList.addAll(Arrays.asList(strings));

        return stringList;
    }


}

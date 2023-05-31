package mazes;

import java.util.LinkedList;

/**
 * GraphParser is a debugging class for writing a Graph to disk,
 * then reading it back.
 *
 * @author Jack Trainor
 * @version CS_351_002 : 2021 - 03 - 29
 */

public class GraphParser {
    /**
     * Constructor for GraphParser
     */
    public GraphParser() {
    }

    protected static void saveGraphFile(Graph graph, String path) {
        StringBuilder sb = new StringBuilder();
        String graphText = String.format("graph: %d %d %d %d\n",
                graph.getWidth(), graph.getHeight(), graph.getCellWidth(),
                graph.getCellHeight());
        sb.append(graphText);
        for (String id : graph.getCompMap().keySet()) {
            GraphComp comp = graph.getComp(id);
            if (comp instanceof GraphEdge) {
                GraphEdge edge = (GraphEdge) comp;
                int startCellX = edge.getStartCellX();
                int startCellY = edge.getStartCellY();
                int endCellX = edge.getEndCellX();
                int endCellY = edge.getEndCellY();
                String edgeText = String.format("edge: %d %d %d %d\n",
                        startCellX, startCellY, endCellX, endCellY);
                sb.append(edgeText);
            }
        }

        Utils.writeTextFile(path, sb.toString());
    }

    protected Graph graphLineToGraph(String line) {
        String[] items = line.split(" ");
        String type = "";
        int width = 0;
        int height = 0;
        int cellWidth = 0;
        int cellHeight = 0;
        int counter = 0;
        for (String item : items) {
            if (counter == 0) {
                type = item;
            } else if (counter == 1) {
                width = Integer.parseInt(item);
            } else if (counter == 2) {
                height = Integer.parseInt(item);
            } else if (counter == 3) {
                cellWidth = Integer.parseInt(item);
            } else if (counter == 4) {
                cellHeight = Integer.parseInt(item);
            }
            counter++;
        }

        if (type.equals("graph:")) {
            return new Graph(width, height, cellWidth, cellHeight, "");
        }
        return null;
    }

    protected GraphEdge edegLineToGraph(String line) {
        String[] items = line.split(" ");
        String type = "";
        int startCellX = 0;
        int startCellY = 0;
        int endCellX = 0;
        int endCellY = 0;
        int counter = 0;
        for (String item : items) {
            if (counter == 0) {
                type = item;
            } else if (counter == 1) {
                startCellX = Integer.parseInt(item);
            } else if (counter == 2) {
                startCellY = Integer.parseInt(item);
            } else if (counter == 3) {
                endCellX = Integer.parseInt(item);
            } else if (counter == 4) {
                endCellY = Integer.parseInt(item);
            }
            counter++;
        }

        if (type.equals("edge:")) {
            return new GraphEdge(startCellX, startCellY, endCellX, endCellY);
        }
        return null;
    }

    protected Graph readGraphFile(String path) {
        String text = Utils.readTextFile(path);
        LinkedList<String> lines = Utils.toLines(text);
        String graphLine = lines.pop();
        Graph graph = graphLineToGraph(graphLine);
        int counter = 0;
        while (!lines.isEmpty()) {
            String edgeLine = lines.pop();
            GraphEdge edge = edegLineToGraph(edgeLine);
            graph.addEdgeToGraph(edge);

        }

        return graph;
    }
}

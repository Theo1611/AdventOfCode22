import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Puzzle24 {
    int ROWS = 41;
    int COLS = 159;
    Vertex[][] grid = new Vertex[ROWS][];
    public void solve() {
        for (int i = 0; i < grid.length; i++) {
            grid[i] = new Vertex[COLS];
        }
        String file = "./inputs/puzzle23_24.txt";
        Vertex start = null;
        //String file = "./inputs/test.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                char[] row = line.toCharArray();
                for (int i = 0; i < row.length; i++) {
                    Vertex current = new Vertex(row[i]);
                    if (current.level == 'S') {
                        current.level = 'a';
                    } else if (current.level == 'E') {
                        current.level = 'z';
                        start = current;
                    }
                    grid[lineNum][i] = current;
                }
                lineNum++;
            }
            findNeighbors();
            System.out.println(BFS(start));
            printPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // prints BFS traversal from a given source s
    int BFS(Vertex start) {
        // Mark all the vertices as not visited(By default
        // set as false)

        // Create a queue for BFS
        LinkedList<Vertex> queue = new LinkedList<>();

        // Mark the current node as visited and enqueue it
        start.visited = true;
        queue.add(start);

        Vertex node = start;
        while (queue.size() != 0 && node.level != 'a') {
            // Dequeue a vertex from queue and print it
            node = queue.poll();

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            List<Vertex> neighbors = node.neighbors;
            for (Vertex v : neighbors) {
                if (!v.visited) {
                    v.visited = true;
                    v.pred = node;
                    queue.add(v);
                }
            }
        }
        int pathLength = 0;
        while (node != start) {
            node = node.pred;
            node.path = true;
            pathLength++;
        }
        return pathLength;
    }

    private void printPath() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                String color = grid[i][j].path ? "\u001B[33m" : "\u001B[30m";
                System.out.print(color + grid[i][j].level + "\u001B[0m");
            }
            System.out.print(System.lineSeparator());
        }
    }

    private void findNeighbors() {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < COLS; column++) {
                Vertex curVertex = grid[row][column];
                if (row != 0) {
                    if (curVertex.level - grid[row - 1][column].level <= 1) curVertex.addNeighbor(grid[row - 1][column]);
                }
                if (row != grid.length - 1) {
                    if (curVertex.level - grid[row + 1][column].level <= 1) curVertex.addNeighbor(grid[row + 1][column]);
                }
                if (column != 0) {
                    if (curVertex.level - grid[row][column - 1].level <= 1) curVertex.addNeighbor(grid[row][column - 1]);
                }
                if (column != COLS - 1) {
                    if (curVertex.level - grid[row][column + 1].level<= 1) curVertex.addNeighbor(grid[row][column + 1]);
                }
            }
        }
    }

    class Vertex {
        char level;
        Vertex pred = null;
        boolean visited = false;
        boolean path = false;
        List<Vertex> neighbors = new ArrayList<>();

        Vertex(char level) {
            this.level = level;
        }

        void addNeighbor(Vertex neighbor) {
            neighbors.add(neighbor);
        }
    }
}




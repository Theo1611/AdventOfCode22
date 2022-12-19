import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Puzzle32 {
    int TIME = 26;
    Map<String, Valve> map = new HashMap<>();
    String startingValve;
    public void solve() {
        //String file = "./inputs/puzzle31_32.txt";
        String file = "./inputs/test.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                String[] inputs = line.split(";");
                String[] valve = inputs[0].replace("Valve ", "").split(" has flow rate=");
                String[] tunnels = inputs[1].replace(" tunnels lead to valves ","").replace(" tunnel leads to valve ", "").split(",");
                for (int i = 0; i < tunnels.length; i++) {
                    tunnels[i] = tunnels[i].trim();
                }
                String name = valve[0];
                if (lineNum++ == 0) startingValve = name;
                Valve newValve = new Valve(name, Integer.parseInt(valve[1]), tunnels);
                map.put(name, newValve);
            }
            System.out.println(traverse());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    int traverse() {
        List<String> path = new ArrayList<>();
        int time = TIME;
        int totalPressure = 0;
        int ppm = 0;
        Valve currentV = map.get(startingValve);
        Valve toGetTo = BFS(currentV.name);
        while (time > 0) {
            path.add(currentV.name);
            System.out.println("Current valve: " + currentV.name);
            System.out.println("Total pressure: " + totalPressure);
            System.out.println("ppm: " + totalPressure);
            System.out.println("Time: " + time);
            System.out.println("---------------------------------");
            totalPressure += ppm;
            time--;
            if (toGetTo == null) {
                continue;
            }
            if (currentV.pressure > 0 && !currentV.opened) {
                currentV.opened = true;
                ppm += currentV.pressure;
                resetPred();
                toGetTo = BFS(currentV.name);
            } else {
                Valve nextValve = toGetTo;
                while (nextValve.pred != currentV) {
                    nextValve = nextValve.pred;
                }
                currentV = nextValve;
            }
            System.out.println();
        }
        System.out.println(path);
        return totalPressure;
    }

    void resetPred() {
        for (Valve v: map.values()) {
            v.pred = null;
            v.visited = false;
        }
    }

    // prints BFS traversal from a given source s
    Valve BFS(String start) {
        // Mark all the vertices as not visited(By default
        // set as false)
        for (Valve v: map.values()) v.visited = false;
        // Create a queue for BFS
        LinkedList<Valve> queue = new LinkedList<>();
        Valve startValve = map.get(start);
        // Mark the current node as visited and enqueue it
        startValve.visited = true;
        queue.add(startValve);
        Map<Valve, Double> nodeAndVal = new HashMap<>();
        Valve node;
        while (queue.size() != 0) {
            // Dequeue a vertex from queue and print it
            node = queue.poll();
            if (!node.opened && node.pressure != 0) nodeAndVal.put(node, 0.0);
            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            List<Valve> neighbors = Arrays.stream(node.connectedValves).map(v->map.get(v)).toList();
            for (Valve v : neighbors) {
                if (!v.visited) {
                    v.visited = true;
                    v.pred = node;
                    queue.add(v);
                }
            }
        }
        for (Valve v: nodeAndVal.keySet()) {
            int pathLength = 1;
            Valve prev = v.pred;
            while (!prev.name.equals(start)) {
                pathLength++;
                prev = prev.pred;
            }
            nodeAndVal.put(v, ((double) v.pressure)/ pathLength);
        }
        Map.Entry<Valve, Double> debug = nodeAndVal.entrySet().stream().max((entry1, entry2) -> {
            if (Objects.equals(entry1.getValue(), entry2.getValue())) {
                return entry1.getKey().pressure > entry2.getKey().pressure ? 1 : -1;
            }
            return entry1.getValue() > entry2.getValue() ? 1 : -1;
        }).orElse(null);
        return debug == null ? null : debug.getKey();
    }

    class Valve {
        String[] connectedValves;
        int pressure;
        boolean visited;
        boolean opened = false;
        Valve pred = null;
        String name;
        Valve(String name, int pressure, String[] connectedValves) {
            this.name = name;
            this.pressure = pressure;
            this.connectedValves = connectedValves;
        }
    }
}
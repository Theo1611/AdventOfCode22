import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Puzzle29 {
    int LINE = 2000000;
    List<Diamond> allDiamonds = new ArrayList<>();
    Set<Point> noBeacon = new HashSet<>();
    Set<Point> allBeacons = new HashSet<>();
    public void solve() {
        String file = "./inputs/puzzle29_30.txt";
        //String file = "./inputs/test.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] inputs = line.split(":");
                String[] sensor = inputs[0].split(",");
                String[] beacon = inputs[1].split(",");
                Point sensorP = new Point(Integer.parseInt(sensor[0].replace("Sensor at x=", "")),
                        Integer.parseInt(sensor[1].replace(" y=", "")));
                Point beaconP = new Point(Integer.parseInt(beacon[0].replace(" closest beacon is at x=", "")),
                        Integer.parseInt(beacon[1].replace(" y=", "")));
                allBeacons.add(beaconP);
                allDiamonds.add(new Diamond(sensorP, beaconP));
            }
            allDiamonds.forEach(System.out::println);
            allDiamonds.forEach(d -> d.addPointsOnLine(LINE));
            int answer = noBeacon.size();
            for (Point beacon: allBeacons) {
                if (noBeacon.contains(beacon)) answer--;
            }
            System.out.println(System.lineSeparator() + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class Diamond {
        Point center;
        int rad;
        Diamond(Point sensor, Point beacon) {
            this.center = sensor;
            this.rad = Math.abs(sensor.x - beacon.x) + Math.abs(sensor.y - beacon.y);
        }

        void addPointsOnLine(int y) {
            int upperMost = center.y - rad;
            int lowerMost = center.y + rad;
            if (lowerMost >= y && upperMost <= y) {
                int numPoints = (rad*2+1) - 2*Math.abs(center.y - y);
                int startingX = center.x - (numPoints - 1) / 2;
                int newPoint = 0;
                for (int x = startingX; x < startingX + numPoints; x++) {
                    if (noBeacon.add(new Point(x, y))) newPoint++;
                }
                System.out.print(System.lineSeparator() + this);
                System.out.print(" | No Points on 10: " + numPoints);
                System.out.print(" | new points: " + newPoint);
            }

        }

        @Override
        public String toString() {
            return "Center: " + this.center.toString() + " | Rad: " + this.rad;
        }
    }
}




import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.List;

public class Puzzle30 {
    int MIN = 0;
    int MAX = 4000000;
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
            for (int i = MIN; i <= MAX; i++) {
                Ranges ranges = new Ranges();
                int finalI = i;
                noBeacon.clear();
                allDiamonds.forEach(d -> d.addPointsOnLine(finalI, ranges));
                if(ranges.allRanges.size() != 1) {
                    int xCoord = ranges.allRanges.get(1).begin - 1;
                    System.out.println(xCoord);
                    System.out.println("line: " + finalI);
                    System.out.println(BigInteger.valueOf(xCoord).multiply(BigInteger.valueOf(MAX)).add(BigInteger.valueOf(finalI)));
                }
            }
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

        void addPointsOnLine(int y, Ranges ranges) {
            int upperMost = center.y - rad;
            int lowerMost = center.y + rad;
            if (lowerMost >= y && upperMost <= y) {
                int numPoints = (rad*2+1) - 2*Math.abs(center.y - y);
                int startingX = Math.max(center.x - (numPoints - 1) / 2, MIN);
                int endingX = Math.min(startingX + numPoints - 1, MAX);
                Range newRange = new Range(startingX, endingX);
                ranges.addRange(newRange);
            }

        }

        @Override
        public String toString() {
            return "Center: " + this.center.toString() + " | Rad: " + this.rad;
        }
    }
    class Ranges {
        List<Range> allRanges = new ArrayList<>();
        void addRange(Range newRange) {
            allRanges.add(newRange);
            while (mergeRangesOnce());
        }

        boolean mergeRangesOnce() {
            allRanges.sort(Range::compareTo);
            for (int i = 0; i < allRanges.size() - 1; i++) {
                Range currentRange = allRanges.get(i);
                Range nextRange = allRanges.get(i+1);
                if (currentRange.contains(nextRange.begin) || currentRange.contains(nextRange.end) ||
                        nextRange.contains(currentRange.begin)) {
                    currentRange.begin = Math.min(currentRange.begin, nextRange.begin);
                    currentRange.end = Math.max(currentRange.end, nextRange.end);
                    allRanges.remove(i+1);
                    return true;
                } else if (currentRange.end + 1 == nextRange.begin) {
                    currentRange.end++;
                    return true;
                }
            }
            return false;
        }
    }

    class Range implements Comparable<Range> {
        int begin;
        int end;
        Range(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        boolean contains(int i) {
            return i >= this.begin && i <= this.end;
        }

        @Override
        public int compareTo(Range otherRange) {
            return this.begin - otherRange.begin;
        }
    }
}




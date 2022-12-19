import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Puzzle27 {

    Set<Point> allRocks = new HashSet<>();
    Set<Point> allSand = new HashSet<>();
    int maxX = 0;
    int maxY = 0;
    int minX = 1000;
    char[][] display;
    public void solve() {
        String file = "./inputs/puzzle27_28.txt";
        //String file = "./inputs/test.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] rockCoords = line.split(" -> ");
                for (int i = 0; i < rockCoords.length - 1; i++) {
                    drawLine(rockCoords[i], rockCoords[i+1]);
                }
            }
            System.out.println(allRocks.size());
            makeRocks();
            pourSand();
            System.out.println("-------------------------------------------------------------------");
            printDisplay();
            System.out.println(allSand.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeRocks() {
        System.out.println(maxX);
        System.out.println(maxY);
        System.out.println(minX);
        display = new char[maxY + 1][maxX - minX + 1];
        for (char[] chars : display) {
            Arrays.fill(chars, '.');
        }
        for (Point rock: allRocks) {
            rock.x  = rock.x - minX;
            display[rock.y][rock.x] = '#';
        }
    }

    private void printDisplay() {
        for (int i = 0; i < display.length; i++) {
            System.out.println(Arrays.toString(display[i]));
        }
    }

    private void pourSand() {
        Point newSand = new Point(500 - minX, 0);
        newSand = moveSand(newSand);
        while (newSand != null) {
            display[newSand.y][newSand.x] = 'o';
            allSand.add(newSand);
            newSand = new Point(500 - minX, 0);
            newSand = moveSand(newSand);
        }
    }

    private Point moveSand(Point sand) {
        while (sand.y < maxY && sand.x < maxX && sand.x > 0) {
            Point orgSand = new Point(sand);
            if (display[sand.y + 1][sand.x] == '.') {
                sand.y++;
            } else if (display[sand.y + 1][sand.x - 1] == '.') {
                sand.x--;
                sand.y++;
            }
            else if (display[sand.y + 1][sand.x + 1] == '.') {
                sand.x++;
                sand.y++;
            }
            if (orgSand.equals(sand)) {return sand;};
        }
        return null;
    }

    private void drawLine(String point1, String point2) {
        String[] p1Text = point1.split(",");
        String[] p2Text = point2.split(",");
        assert p1Text[0].equals(p2Text[0]) || p1Text[1].equals(p2Text[1]);
        Point p1 = new Point(Integer.parseInt(p1Text[0]), Integer.parseInt(p1Text[1]));
        Point p2 = new Point(Integer.parseInt(p2Text[0]), Integer.parseInt(p2Text[1]));
        updateMaxMin(p1);
        updateMaxMin(p2);
        if (p1.x == p2.x) {
            int smaller = Math.min(p1.y, p2.y);
            int bigger = Math.max(p1.y, p2.y);
            for (int i = smaller; i < bigger + 1; i++) {
                allRocks.add(new Point(p1.x, i));
            }
        } else {
            int smaller = Math.min(p1.x, p2.x);
            int bigger = Math.max(p1.x, p2.x);
            for (int i = smaller; i < bigger + 1; i++) {
                allRocks.add(new Point(i, p1.y));
            }
        }
    }

    private void updateMaxMin(Point p) {
        maxX = Math.max(p.x, maxX);
        maxY = Math.max(p.y, maxY);
        minX = Math.min(p.x, minX);
    }
}




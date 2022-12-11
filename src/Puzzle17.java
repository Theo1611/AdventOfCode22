import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class Puzzle17 {
    Set<Point> tailTrace = new HashSet<>();
    public void solve() {
        String file = "./inputs/puzzle17_18.txt";
        tailTrace.add(new Point(0,0));
        Point head = new Point(0,0);
        Point tail = new Point(0,0);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] inputs = line.split(" ");
                for (int i = 0; i < Integer.parseInt(inputs[1]); i++) {
                    switch (inputs[0]) {
                        case "R":
                            head.x++;
                            break;
                        case "L":
                            head.x--;
                            break;
                        case "U":
                            head.y++;
                            break;
                        case "D":
                            head.y--;
                            break;
                    }
                    tailTrace.add(adjustTail(head, tail));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(tailTrace.size());
    }

    Point adjustTail(Point head, Point tail) {
        if (Math.abs(head.x - tail.x) > 1) {
            if (head.y - tail.y > 0) tail.y++;
            else if (head.y - tail.y < 0) tail.y--;
            tail.x += head.x > tail.x ? 1 : -1;
        } else if (Math.abs(head.y - tail.y) > 1) {
            if (head.x - tail.x > 0) tail.x += 1;
            else if (head.x - tail.x < 0) tail.x -= 1;
            tail.y += head.y > tail.y ? 1 : -1;
        }
        return new Point(tail);
    }
}

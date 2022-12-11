import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class Puzzle20 {

    char[][] display = new char[6][40];
    int X = 1;
    public void solve() {
        String file = "./inputs/puzzle19_20.txt";
        for (int i = 0; i < 6; i++) {
            Arrays.fill(display[i], '.');
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int cycle = 1;
            int waiting;
            int toAdd = 0;
            while ((line = br.readLine()) != null) {
                String[] inputs = line.split(" ");
                if (inputs[0].equals("addx")) {
                    waiting = 2;
                    toAdd = Integer.parseInt(inputs[1]);
                } else waiting = 1;
                while (waiting != 0) {
                    draw(cycle);
                    waiting--;
                    cycle++;
                }
                X += toAdd;
                toAdd = 0;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        printing();
    }

    void draw(int cycle) {
        int drawPosX = (cycle-1)%40;
        int drawPosY = (cycle-1)/40;
        System.out.println(drawPosY + " and " + drawPosX + " and X: " + X);
        if (drawPosX >= X - 1 && drawPosX <= X + 1) {
            display[drawPosY][drawPosX] = '#';
        }
    }

    void printing() {
        for (int i = 0; i < 6; i++) {
            System.out.println(new String(display[i]));
        }
    }
}


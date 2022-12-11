import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class Puzzle19 {

    public void solve() {
        String file = "./inputs/puzzle19_20.txt";
        //String file = "./inputs/test.txt";
        String[][] display = new String[6][];
        int sum = 0;
        int X = 1;
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
                    //System.out.println("During " + cycle + "th cycle: " + X);
                    if ((cycle - 20) % 40 == 0) {
                        System.out.println("During " + cycle + "th cycle: " + X);
                        System.out.println(sum);
                        sum += X * cycle;
                        System.out.println(sum);
                    }
                    waiting--;
                    cycle++;
                }
                X += toAdd;
                toAdd = 0;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(X);
        System.out.println(sum);
    }
}


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Puzzle6 {

    public void solve(){
        String file = "./inputs/puzzle5_6.txt";
        int totalPrio = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNum = 0;
            String badge;
            Set<String> prevItems = new HashSet<>(Collections.singleton("`"));
            while ((line = br.readLine()) != null) {
                Set<String> items = Arrays.stream(line.split("")).collect(Collectors.toSet());
                if (lineNum % 3 == 0) {
                    badge = prevItems.stream().findFirst().get();
                    totalPrio += getPrio(badge);
                    System.out.println(badge);
                    System.out.println(totalPrio);
                    prevItems = items;
                } else {
                    prevItems.retainAll(items);
                }
                ++lineNum;
            }
            badge = prevItems.stream().findFirst().get();
            totalPrio += getPrio(badge);
            System.out.println(badge);
            System.out.println(totalPrio);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(totalPrio);
    }

    private int getPrio(String input) {
        char item = input.charAt(0);
        if (Character.isUpperCase(item)) {
            return (int) item - 38;
        } else {
            return (int) item - 96;
        }
    }
}

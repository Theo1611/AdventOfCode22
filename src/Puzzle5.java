import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Puzzle5 {

    public void solve(){
        String file = "./inputs/puzzle5_6.txt";
        int score = 0;
        int totalPrio = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                Set<String> firstComp = Arrays.stream(line.substring(0,line.length()/2).split("")).collect(Collectors.toSet());
                Set<String> secondComp = Arrays.stream(line.substring(line.length()/2).split("")).collect(Collectors.toSet());
                firstComp.retainAll(secondComp);
                totalPrio += firstComp.stream().map(this::getPrio).reduce(0, Integer::sum);
                //System.out.println(lineNum++ + firstComp.toString() + secondComp.toString());
            }
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
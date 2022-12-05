import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Puzzle2 {

    public void solve(){
        List<Integer> curLeaders = Arrays.asList(0,0,0);
        String file = "./inputs/puzzle1_2.txt";
        for (int i = 0; i < 3; i++) {
            int cur = 0;
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.isEmpty()) {
                        if (cur > curLeaders.get(i) && !curLeaders.contains(cur)) {
                            curLeaders.set(i, cur);
                        }
                        cur = 0;
                    } else {
                        cur += Integer.parseInt(line);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(curLeaders.stream().reduce(0, Integer::sum));
    }
}

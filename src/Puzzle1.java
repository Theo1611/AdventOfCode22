import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Puzzle1 {

    public void solve(){
        int curMax = 0;
        int cur = 0;
        String file = "./inputs/puzzle1_2.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    curMax = Math.max(curMax, cur);
                    cur = 0;
                } else {
                    cur += Integer.parseInt(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(curMax);
    }
}

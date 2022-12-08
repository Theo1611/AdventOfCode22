import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Puzzle11 {

    public void solve() {
        String file = "./inputs/puzzle11_12.txt";
        int i;
        List<Character> marker = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            char[] allChars = line.toCharArray();
            for (i = 0; i < allChars.length; i++) {
                char current = allChars[i];
                int indexOfDup = marker.indexOf(current);
                if (indexOfDup != -1) {
                    marker = marker.subList(indexOfDup+1, marker.size());
                }
                marker.add(current);
                if (marker.size() == 4) break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(marker);
        System.out.println(i+1);

    }
}

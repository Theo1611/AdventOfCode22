import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Puzzle3 {

    public void solve(){
        String file = "./inputs/puzzle3_4.txt";
        int score = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] roundInput = line.split(" ");
                switch (roundInput[1]) {
                    case "X":
                        score += 1;
                        roundInput[1] = "A";
                        break;
                    case "Y":
                        score += 2;
                        roundInput[1] = "B";
                        break;
                    case "Z":
                        score += 3;
                        roundInput[1] = "C";
                        break;
                }
                score += scoreWinLoseDraw(roundInput);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(score);
    }

    private int scoreWinLoseDraw(String[] input) {
        if ((input[0].equals("A") && input[1].equals("B")) ||
                (input[0].equals("B") && input[1].equals("C")) ||
                (input[0].equals("C") && input[1].equals("A"))) {
            return 6;
        } else if (input[0].equals(input[1])) {
            return 3;
        }
        return 0;
    }
}


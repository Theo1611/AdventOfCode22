import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Puzzle4 {

    public void solve(){
        String file = "./inputs/puzzle3_4.txt";
        int score = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] roundInput = line.split(" ");
                switch (roundInput[1]) {
                    case "X":
                        score += 0;
                        break;
                    case "Y":
                        score += 3;
                        break;
                    case "Z":
                        score += 6;
                        break;
                }
                score += scoreShape(roundInput);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(score);
    }

    private int scoreShape(String[] input) {
        if ((input[0].equals("A") && input[1].equals("Y")) ||
                (input[0].equals("B") && input[1].equals("X")) ||
                (input[0].equals("C") && input[1].equals("Z"))) {
            return 1;
        } else if ((input[0].equals("A") && input[1].equals("Z")) ||
                (input[0].equals("B") && input[1].equals("Y")) ||
                (input[0].equals("C") && input[1].equals("X"))) {
            return 2;
        }
        return 3;
    }
}
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Puzzle9 {

    public void solve() {
        String file = "./inputs/puzzle9_10.txt";
        int totalOverlaps = 0;
        List<List<Character>> stacks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String crates = "";
            while ((line = br.readLine()).contains("[")) {
                crates += System.lineSeparator() + line;
            }
            int numStacks = Arrays.stream(line.split("   ")).map(x->Integer.parseInt(x.trim())).max(Integer::compare).get();
            for (int i = 0; i < numStacks; i++) {
                stacks.add(new ArrayList<>());
            }
            String[] cratesArray = crates.split(System.lineSeparator());
            Arrays.stream(Arrays.copyOfRange(cratesArray, 1, cratesArray.length)).forEach(x->parseCrates(x, stacks));
            br.readLine();
            while ((line = br.readLine()) != null) {
                int[] inputs = Arrays.stream(line.split(" ")).filter(x->x.chars().allMatch(Character::isDigit)).mapToInt(Integer::parseInt).toArray();
                System.out.println(Arrays.toString(inputs));
                System.out.println(stacks);
                doOperation(inputs, stacks);
                System.out.println(stacks);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stacks.forEach(x ->{
            if (x.size() >= 1) System.out.print(x.get(x.size() - 1));
        });
    }

    private void parseCrates(String cratesString, List<List<Character>> stacks) {
        char[] cratesChars = cratesString.toCharArray();
        System.out.println(cratesChars);
        int arrIndex = 0;
        for (int i = 0; i < cratesChars.length; i+=3) {
            char crate = cratesChars[i+1];
            if (crate != ' ') {
                stacks.get(arrIndex).add(0, crate);
            }
            arrIndex++;
            i++;
        }
    }

    private void doOperation(int[] inputs, List<List<Character>> stacks) {
        for (int i = 0; i < inputs[0]; i++) {
            List<Character> from = stacks.get(inputs[1] - 1);
            List<Character> to = stacks.get(inputs[2] - 1);
            to.add(from.remove(from.size() - 1));
        }
    }
}

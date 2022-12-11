import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Puzzle21 {

    int ROUNDS = 20;
    int MONKEYNUM = 8;

    Monkey[] allMonkeys = new Monkey[MONKEYNUM];
    int[] inspectCounts = new int[MONKEYNUM];

    public void solve() {
        for (int i = 0; i < MONKEYNUM; i++) {
            allMonkeys[i] = new Monkey();
        }
        Arrays.fill(inspectCounts, 0);
        //String file = "./inputs/puzzle21_22.txt";
        String file = "./inputs/test.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int monkeyNum = 0;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Monkey")) {
                    String[] inputs = line.replace(":", "").split(" ");
                    monkeyNum = Integer.parseInt(inputs[1]);
                } else if (line.contains("Starting items")) {
                    String[] allItems = line.trim().replace("Starting items: ", "").split(",");
                    Monkey currentMonkey = allMonkeys[monkeyNum];
                    Arrays.stream(allItems).map(String::trim).mapToInt(Integer::parseInt).forEach(currentMonkey::addItem);
                } else if (line.contains("Operation: new = old ")) {
                    String[] ops = line.trim().replace("Operation: new = old ", "").split(" ");
                    if (ops[1].equals("old")) allMonkeys[monkeyNum].getNewWorry = (Integer oldWorry) -> oldWorry * oldWorry;
                    else if (ops[0].equals("+")) allMonkeys[monkeyNum].getNewWorry = (Integer oldWorry) -> oldWorry + Integer.parseInt(ops[1]);
                    else if (ops[0].equals("*")) allMonkeys[monkeyNum].getNewWorry = (Integer oldWorry) -> oldWorry * Integer.parseInt(ops[1]);
                    else throw new RuntimeException();
                } else if (line.contains("Test: divisible by ")) {
                    allMonkeys[monkeyNum].cond = Integer.parseInt(line.trim().replace("Test: divisible by ", ""));
                } else if (line.contains("If true: throw to monkey")) {
                    allMonkeys[monkeyNum].trueMonkey = Integer.parseInt(line.trim().replace("If true: throw to monkey ", ""));
                } else if (line.contains("If false: throw to monkey")) {
                    allMonkeys[monkeyNum].falseMonkey = Integer.parseInt(line.trim().replace("If false: throw to monkey ", ""));
                } else System.out.println("wtf: " + line);
            }
            for (int i = 0; i < ROUNDS; i++) {
                for (monkeyNum = 0; monkeyNum < MONKEYNUM; monkeyNum++) {
                    Monkey currentMonkey = allMonkeys[monkeyNum];
                    List<Integer> items = currentMonkey.items;
                    for (Integer item : items) {
                        inspectCounts[monkeyNum]++;
                        int newItem = (currentMonkey.getNewWorry.apply(item) / 3);
                        int monkeyToThrowTo = currentMonkey.test(newItem) ? currentMonkey.trueMonkey : currentMonkey.falseMonkey;
                        currentMonkey.throwTo(newItem, allMonkeys[monkeyToThrowTo]);
                    }
                    currentMonkey.clearItems();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Arrays.toString(inspectCounts));
        Arrays.sort(inspectCounts);
        System.out.println(inspectCounts[MONKEYNUM-1] * inspectCounts[MONKEYNUM-2]);
    }

    class Monkey {
        List<Integer> items = new ArrayList<>();
        int cond;
        int trueMonkey;
        int falseMonkey;
        Function<Integer, Integer> getNewWorry;

        boolean test(int item) {
            return item % cond == 0;
        }

        void addItem(int item) {
            items.add(item);
        }

        void throwTo(int item, Monkey otherMonkey) {
            otherMonkey.addItem(item);
        }

        void clearItems() {
            items.clear();
        }
    }
}


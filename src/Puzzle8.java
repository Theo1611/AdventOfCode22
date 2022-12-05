import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Puzzle8 {

    public void solve() {
        String file = "./inputs/puzzle7_8.txt";
        int totalOverlaps = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] bothAss = line.split(",");
                Assignment ass1 = new Assignment(bothAss[0]);
                Assignment ass2 = new Assignment(bothAss[1]);
                if (ass1.overlaps(ass2)) {
                    totalOverlaps++;
                }
                System.out.println(line);
                System.out.println(totalOverlaps);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(totalOverlaps);
    }

    private class Assignment {
        private int begin;
        private int end;
        Assignment(String assignmentString) {
            String[] assignments = assignmentString.split("-");
            begin = Integer.parseInt(assignments[0]);
            end = Integer.parseInt(assignments[1]);
        }

        protected int getBegin() {return begin;}
        protected int getEnd() {return end;}
        protected boolean overlaps(Assignment otherAssignment) {
            return begin <= otherAssignment.end && end >= otherAssignment.begin;
        }
    }
}

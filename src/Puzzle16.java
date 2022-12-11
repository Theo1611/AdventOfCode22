import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Puzzle16 {

    Tree[][] treeGrid = new Tree[99][];
    public void solve(){
        String file = "./inputs/puzzle15_16.txt";
        //String file = "./inputs/test.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                Tree[] inputRow = Arrays.stream(line.split("")).mapToInt(Integer::parseInt).mapToObj(Tree::new).toArray(Tree[]::new);
                treeGrid[lineNum] = inputRow;
                lineNum++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        updateNorthAndWest();
        updateSouthAndEast();
        //System.out.println(Arrays.deepToString(treeGrid));
        System.out.println(findMaxScenic());
        //printTreeDetails();
    }

    private int findMaxScenic() {
        int maxScenic = 0;
        int colLength = treeGrid[0].length;
        for (int row = 1; row < treeGrid.length - 1; row++) {
            for (int col = 1; col < colLength - 1; col++) {
                Tree curTree = treeGrid[row][col];
                curTree.visionWest = curTree.nextHighestWest >= curTree.height ? curTree.visionWest + 1 : curTree.visionWest;
                curTree.visionNorth = curTree.nextHighestNorth >= curTree.height ? curTree.visionNorth + 1 : curTree.visionNorth;
                curTree.visionSouth = curTree.nextHighestSouth >= curTree.height ? curTree.visionSouth + 1 : curTree.visionSouth;
                curTree.visionEast = curTree.nextHighestEast >= curTree.height ? curTree.visionEast + 1 : curTree.visionEast;
                int scenic = curTree.visionWest * curTree.visionNorth * curTree.visionEast * curTree.visionSouth;
                maxScenic = Math.max(maxScenic, scenic);
            }
        }
        return maxScenic;
    }

    private void updateNorthAndWest() {
        int colLength = treeGrid[0].length;
        for (int row = 1; row < treeGrid.length - 1; row++) {
            for (int col = 1; col < colLength - 1; col++) {
                Tree curTree = treeGrid[row][col];
                getVisionScoreWest(curTree, row, col);
                getVisionScoreNorth(curTree, row, col);
            }
        }
    }

    void getVisionScoreWest(Tree curTree, int row, int col) {
        int colCurNextTree = col - 1;
        Tree curNextTree = treeGrid[row][colCurNextTree];
        while (curNextTree.height < curTree.height) {
            curTree.visionWest++;
            curTree.visionWest += curNextTree.visionWest;
            colCurNextTree = col - 1 - curTree.visionWest;
            if (colCurNextTree < 0) {
                break;
            }
            curNextTree = treeGrid[row][colCurNextTree];
        }
        curTree.nextHighestWest = curNextTree.height;
    }

    void getVisionScoreNorth(Tree curTree, int row, int col) {
        int rowCurNextTree = row - 1;
        Tree curNextTree = treeGrid[rowCurNextTree][col];
        while (curNextTree.height < curTree.height) {
            curTree.visionNorth++;
            curTree.visionNorth += curNextTree.visionNorth;
            rowCurNextTree = row - 1 - curTree.visionNorth;
            if (rowCurNextTree < 0) {
                break;
            }
            curNextTree = treeGrid[rowCurNextTree][col];
        }
        curTree.nextHighestNorth = curNextTree.height;
    }

    void getVisionScoreSouth(Tree curTree, int row, int col) {
        int rowCurNextTree = row + 1;
        Tree curNextTree = treeGrid[rowCurNextTree][col];
        while (curNextTree.height < curTree.height) {
            curTree.visionSouth++;
            curTree.visionSouth = curTree.visionSouth + curNextTree.visionSouth;
            rowCurNextTree = row + 1 + curTree.visionSouth;
            if (rowCurNextTree >= treeGrid.length) {
                break;
            }
            curNextTree = treeGrid[rowCurNextTree][col];
        }
        curTree.nextHighestSouth = curNextTree.height;
    }

    void getVisionScoreEast(Tree curTree, int row, int col) {
        int colCurNextTree = col + 1;
        Tree curNextTree = treeGrid[row][colCurNextTree];
        while (curNextTree.height < curTree.height) {
            curTree.visionEast++;
            curTree.visionEast += curNextTree.visionEast;
            colCurNextTree = col + 1 + curTree.visionEast;
            if (colCurNextTree >= treeGrid.length) {
                break;
            }
            curNextTree = treeGrid[row][colCurNextTree];
        }
        curTree.nextHighestEast = curNextTree.height;
    }
    private void updateSouthAndEast() {
        int colLength = treeGrid[0].length;
        for (int row = treeGrid.length - 2; row > 0; row--) {
            for (int col = colLength - 2; col > 0; col--) {
                Tree curTree = treeGrid[row][col];
                getVisionScoreEast(curTree, row, col);
                getVisionScoreSouth(curTree, row, col);
            }
        }
    }

    void printTreeDetails(){
        int colLength = treeGrid[0].length;
        for (int row = 0; row < treeGrid.length; row++) {
            for (int col = 0; col < colLength; col++) {
                Tree curTree = treeGrid[row][col];
                System.out.println("Height: " + curTree.height + " | North = " + curTree.nextHighestNorth +
                        " | West = " + curTree.nextHighestWest + " | South = " + curTree.nextHighestSouth + " | East = " + curTree.nextHighestEast +
                        " || visionNorth = " + curTree.visionNorth + " | visionWest = " + curTree.visionWest +
                        " | visionSouth = " + curTree.visionSouth + " | visionEast = " + curTree.visionEast);
            }
        }
    }

    class Tree {
        int nextHighestNorth = -1;
        int visionNorth = 0;
        int nextHighestSouth = -1;
        int visionSouth = 0;
        int nextHighestWest = -1;
        int visionWest = 0;
        int nextHighestEast = -1;
        int visionEast = 0;
        int height;
        Tree(int height) {
            this.height = height;
        }
        public String toString() {
            return String.valueOf(height);
        }
    }
}


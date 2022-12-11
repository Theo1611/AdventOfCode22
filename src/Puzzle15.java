import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Puzzle15 {

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
        System.out.println(Arrays.deepToString(treeGrid));
        printTreeDetails();
        System.out.println(findNumVisible());
    }

    private int findNumVisible() {
        int numVis = treeGrid.length * 4 - 4;
        int colLength = treeGrid[0].length;
        for (int row = 1; row < treeGrid.length - 1; row++) {
            for (int col = 1; col < colLength - 1; col++) {
                Tree curTree = treeGrid[row][col];
                if (curTree.height > curTree.highestNorth ||
                        curTree.height > curTree.highestSouth ||
                        curTree.height > curTree.highestWest ||
                        curTree.height > curTree.highestEast) {
                    numVis++;
                }
            }
        }
        return numVis;
    }

    private void updateNorthAndWest(){
        int colLength = treeGrid[0].length;
        for (int row = 1; row < treeGrid.length - 1; row++) {
            for (int col = 1; col < colLength - 1; col++) {
                Tree curTree = treeGrid[row][col];
                Tree westTree = treeGrid[row][col-1];
                Tree northTree = treeGrid[row-1][col];
                curTree.highestWest = Math.max(westTree.height, westTree.highestWest);
                curTree.highestNorth = Math.max(northTree.height, northTree.highestNorth);
            }
        }
    }

    private void updateSouthAndEast(){
        int colLength = treeGrid[0].length;
        for (int row = treeGrid.length - 2; row > 0; row--) {
            for (int col = colLength - 2; col > 0; col--) {
                Tree curTree = treeGrid[row][col];
                Tree eastTree = treeGrid[row][col+1];
                Tree southTree = treeGrid[row+1][col];
                curTree.highestEast = Math.max(eastTree.height, eastTree.highestEast);
                curTree.highestSouth = Math.max(southTree.height, southTree.highestSouth);
            }
        }
    }

    void printTreeDetails(){
        int colLength = treeGrid[0].length;
        for (int row = 0; row < treeGrid.length; row++) {
            for (int col = 0; col < colLength; col++) {
                Tree curTree = treeGrid[row][col];
                System.out.println("Height: " + curTree.height + " | North = " + curTree.highestNorth +
                        " | West = " + curTree.highestWest + " | South = " + curTree.highestSouth + " | East = " + curTree.highestEast);
            }
        }
    }

    class Tree {
        int highestNorth = -1;
        int highestSouth = -1;
        int highestWest = -1;
        int highestEast = -1;
        int height;
        Tree(int height) {
            this.height = height;
        }
        public String toString() {
            return String.valueOf(height);
        }
    }
}


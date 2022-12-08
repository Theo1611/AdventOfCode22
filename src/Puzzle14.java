import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Stream;
import java.lang.RuntimeException;

public class Puzzle14 {
    private BigInteger freeSpace = new BigInteger("70000000").subtract(new BigInteger("43562874"));
    private BigInteger spaceNeeded = new BigInteger("30000000").subtract(freeSpace);
    private String dir2Del;
    private BigInteger leastSizeFreed = BigInteger.valueOf(Integer.MAX_VALUE);

    public void solve() {
        System.out.println(spaceNeeded);
        String file = "./inputs/puzzle13_14.txt";
        BigInteger totalSize = new BigInteger("0");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line;
            FileAoc root = new FileAoc("/", null);
            FileAoc currentNode = root;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                String[] inputs = line.split(" ");
                if (inputs[0].equals("dir")) {
                    currentNode.addChild(new FileAoc(inputs[1], currentNode));
                } else if (Character.isDigit(inputs[0].charAt(0))) {
                    currentNode.addSize(new BigInteger(inputs[0]));
                    currentNode.addChild(new FileAoc(inputs[1], currentNode));
                } else if (inputs[1].equals("cd")) {
                    if (inputs[2].equals("..")) {
                        currentNode.calcSize();
                        currentNode = currentNode.getParent();
                    } else {
                        currentNode = currentNode.goToChild(inputs[2]);
                    }
                }
                lineNum++;
            }
            while (currentNode != root) {
                currentNode.calcSize();
                currentNode = currentNode.getParent();
            }
            root.calcSize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(leastSizeFreed);
        System.out.println(dir2Del);
    }

    private class FileAoc {
        private BigInteger size = new BigInteger("0");
        private String name;
        private FileAoc parent;
        private boolean childSizeFound = false;
        FileAoc(String name, FileAoc parent) {
            this.name = name;
            this.parent = parent;
        }
        private Map<String, FileAoc> childFiles = new HashMap<>();
        protected void addSize(BigInteger addedSize) {
            size = size.add(addedSize);
        }
        protected void calcSize() {
            for (FileAoc child: childFiles.values()) {
                size = size.add(child.getSize());
            }
            if (this.size.compareTo(spaceNeeded) > 0 && this.size.compareTo(leastSizeFreed) < 0) {
                dir2Del = this.name;
                leastSizeFreed = this.size;
            }
        }

        protected BigInteger getSize() {
            return size;
        }

        protected FileAoc getParent() {
            return parent;
        }

        protected FileAoc goToChild(String childName) {
            FileAoc child = childFiles.get(childName);
            if (child == null) throw new RuntimeException();
            return childFiles.get(childName);
        }

        protected void addChild(FileAoc child){
            childFiles.put(child.name, child);
        }

        protected void print() {
            System.out.println("Self: " + this.name);
            for (FileAoc child: childFiles.values()) {
                child.print();
            };
        }
    }
}


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Stream;
import java.lang.RuntimeException;

public class Puzzle13 {

    public void solve() {
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
                        BigInteger childNodeSize = currentNode.getSize();
                        System.out.println(totalSize);
                        if (childNodeSize.compareTo(new BigInteger("100000")) <= 0) {
                            totalSize = totalSize.add(childNodeSize);
                        }
                        currentNode = currentNode.getParent();
                    } else {
                        currentNode = currentNode.goToChild(inputs[2]);
                    }
                }
                lineNum++;
            }
            while (currentNode != root) {
                currentNode.calcSize();
                BigInteger childNodeSize = currentNode.getSize();
                System.out.println(totalSize);
                if (childNodeSize.compareTo(new BigInteger("100000")) <= 0) {
                    totalSize = totalSize.add(childNodeSize);
                }
                currentNode = currentNode.getParent();
            }
            root.calcSize();
            if (root.getSize().compareTo(new BigInteger("100000")) <= 0) {
                totalSize = totalSize.add(root.getSize());
            }
            System.out.println(root.getSize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(totalSize);
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

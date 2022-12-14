import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Puzzle26 {
    char BEGIN = '[';
    char END = ']';
    char COMMA = ',';
    public void solve() {
        String file = "./inputs/puzzle25_26.txt";
        //String file = "./inputs/test.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            Element code1;
            Element code2;
            int total = 1;
            List<Element> allElements = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue;
                char[] codedSignal1 = line.toCharArray();
                line = br.readLine();
                char[] codedSignal2 = line.toCharArray();
                code1 = parseElement(codedSignal1);
                code2 = parseElement(codedSignal2);
                allElements.add(code1);
                allElements.add(code2);
            }
            Element div1 = parseElement("[[2]]".toCharArray());
            Element div2 = parseElement("[[6]]".toCharArray());
            allElements.add(div1);
            allElements.add(div2);
            allElements.sort(new CustomComparator());
            for (int i = 0; i < allElements.size(); i++) {
                if (allElements.get(i) == div1 || allElements.get(i) == div2) {
                    total *= (i+1);
                    System.out.println(allElements.get(i));
                    System.out.println(i+1);
                }
            }
            System.out.println(total);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Element parseElement(char[] signal) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Element> map = new HashMap<>();
        char key = 'a';
        Element curElement = null;
        for (int i = 0; i < signal.length; i++) {
            char curChar = signal[i];
            if (curChar == END) {
                curElement = new Element();
                char prevChar = ',';
                while (stack.peek() != BEGIN) {
                    char temp = stack.pop();
                    if (temp != COMMA) {
                        if (Character.isDigit(temp)) {
                            if (Character.isDigit(prevChar)) {
                                curElement.add(0, new Element(10));
                            }
                            else curElement.add(0, new Element(Character.getNumericValue(temp)));
                        } else {
                            curElement.add(0, map.get(temp));
                        }
                    }
                    prevChar = temp;
                }
                map.put(key, curElement);
                stack.pop();
                stack.push(key);
                key = (char) (key + 1);
            } else {
                stack.push(curChar);
            }
        }
        return curElement;
    }

    private int isInOrder(Element el1, Element el2) {
        int i;
        for (i = 0; i < el1.elements.size(); i++) {
            Element e1 = el1.elements.get(i);
            if (i >= el2.elements.size()) return 1;
            Element e2 = el2.elements.get(i);
            if (e1.value != null && e2.value != null) {
                if (e1.value - e2.value < 0) return -1;
                else if (e1.value - e2.value > 0) return 1;
            } else if (e1.value == null && e2.value != null) {
                Element temp = new Element();
                temp.add(e2);
                int inOrder = isInOrder(e1, temp);
                if (inOrder < 0) return -1;
                else if (inOrder > 0) return 1;
            } else if (e1.value != null && e2.value == null) {
                Element temp = new Element();
                temp.add(e1);
                int inOrder = isInOrder(temp, e2);
                if (inOrder < 0) return -1;
                else if (inOrder > 0) return 1;
            } else if (e1.value == null && e2.value == null) {
                int inOrder = isInOrder(e1, e2);
                if (inOrder < 0) return -1;
                else if (inOrder > 0) return 1;
            }
        }
        return i < el2.elements.size() ? -1 : 0;
    }

    class Element {
        List<Element> elements = new ArrayList<>();
        Integer value = null;
        void add(int index, Element element) {elements.add(index, element);}
        void add(Element element) {elements.add(element);}
        Element(Integer value) {
            this.value = value;
        }

        Element() {};

        @Override
        public String toString() {
            //return System.lineSeparator() + "value: " + value + " | Elements: " + elements.toString();
            return value == null ? elements.toString() : value.toString();
        }
    }

    public class CustomComparator implements Comparator<Element> {
        @Override
        public int compare(Element el1, Element el2) {
            return isInOrder(el1, el2);
        }
    }
}




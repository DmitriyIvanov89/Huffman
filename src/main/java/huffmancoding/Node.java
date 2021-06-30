package huffmancoding;

import java.util.HashMap;
import java.util.Map;

public class Node implements Comparable<Node> {

    private final int frequency;
    private final Character symbol;
    private Map<Character, Integer> frequencies;
    private Node leftChild;
    private Node rightChild;

    //child
    public Node(Character symbol, int frequency) {
        this.symbol = symbol;
        this.frequency = frequency;
        this.frequencies = new HashMap<>();
    }

    //parent
    public Node(Character symbol, int frequency, Node leftChild, Node rightChild) {
        this.frequency = leftChild.frequency + rightChild.frequency;
        this.symbol = symbol;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.frequencies = new HashMap<>();
    }

    public int getFrequency() {
        return frequency;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public int compareTo(Node o) {
        return o.frequency - frequency;
    }

    @Override
    public String toString() {
        return String.format("Node: %s, frequency: %s", this.getSymbol(), this.getFrequency());
    }
}

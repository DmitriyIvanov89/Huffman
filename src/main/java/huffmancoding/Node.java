package huffmancoding;

public class Node implements Comparable<Node> {

    private final int frequency;
    private final Character symbol;
    private Node leftChild;
    private Node rightChild;


    public Node(char symbol, int weight) {
        this.frequency = weight;
        this.symbol = symbol;
    }

    public Node(Character symbol, int frequency, Node leftChild, Node rightChild) {
        this.frequency = leftChild.frequency + rightChild.frequency;
        this.symbol = symbol;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
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
}

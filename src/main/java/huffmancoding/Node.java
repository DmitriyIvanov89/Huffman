package huffmancoding;

public class Node implements Comparable<Node> {

    private final Character symbol;
    private final int frequency;
    private Node left;
    private Node right;

    public Node(Character symbol, int frequency) {
        this.symbol = symbol;
        this.frequency = frequency;
    }

    public Node(Character symbol, int frequency, Node left, Node right) {
        this.symbol = symbol;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public int getFrequency() {
        return frequency;
    }

    public Character getSymbol() {
        return symbol;
    }

    @Override
    public int compareTo(Node o) {
        return o.frequency - frequency;
    }
}
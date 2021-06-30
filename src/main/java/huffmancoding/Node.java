package huffmancoding;

public class Node implements Comparable<Node> {

    private final int frequency;
    private final Character symbol;
    private Node leftChild;
    private Node rightChild;

    public Node(Character symbol, int frequency) {
        this.symbol = symbol;
        this.frequency = frequency;
    }

    public Node(Character symbol, int frequency, Node leftChild, Node rightChild) {
        this.symbol = symbol;
        this.frequency = leftChild.frequency + rightChild.frequency;
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

    public String encode(Node root, String path) {
        if (root == null) {
            return path;
        }
        if (leftChild != null) {
            path = leftChild.encode(root, path + "0");
            if (path != null) {
                return path;
            }
        }
        if (rightChild != null) {
            path = rightChild.encode(root, path + "1");
            if (path != null) {
                return path;
            }
        }
        return null;
    }
}

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

    public String encode(Character currChar, String parentPath) {
        if (symbol == currChar) {
            return parentPath;
        }
        if (left != null) {
            String path = left.encode(currChar, parentPath + 0);
            if (path != null) {
                return path;
            }
        }
        if (right != null) {
            String path = right.encode(currChar, parentPath + 1);
            if (path != null) {
                return path;
            }
        }
        return null;
    }
}

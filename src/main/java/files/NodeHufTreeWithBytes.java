package files;

public class NodeHufTreeWithBytes implements Comparable<NodeHufTreeWithBytes> {

    private final Byte symbolByte;
    private final int frequency;
    private NodeHufTreeWithBytes left;
    private NodeHufTreeWithBytes right;

    public NodeHufTreeWithBytes(Byte symbolByte, int frequency) {
        this.symbolByte = symbolByte;
        this.frequency = frequency;
    }

    public NodeHufTreeWithBytes(Byte symbolByte, int frequency, NodeHufTreeWithBytes left, NodeHufTreeWithBytes right) {
        this.symbolByte = symbolByte;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public Byte getSymbolByte() {
        return symbolByte;
    }

    public int getFrequency() {
        return frequency;
    }

    public NodeHufTreeWithBytes getLeft() {
        return left;
    }

    public NodeHufTreeWithBytes getRight() {
        return right;
    }

    @Override
    public int compareTo(NodeHufTreeWithBytes o) {
        return o.frequency - frequency;
    }
}

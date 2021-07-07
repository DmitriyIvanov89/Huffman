package files;

public class HuffmanTreeNode {

    private final Byte nodeByte;
    private final int frequency;
    private HuffmanTreeNode left;
    private HuffmanTreeNode right;

    public HuffmanTreeNode(Byte nodeByte, int frequency) {
        this.nodeByte = nodeByte;
        this.frequency = frequency;
    }

    public HuffmanTreeNode(Byte nodeByte, int frequency, HuffmanTreeNode left, HuffmanTreeNode right) {
        this.nodeByte = nodeByte;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public Byte getNodeByte() {
        return nodeByte;
    }

    public int getFrequency() {
        return frequency;
    }

    public HuffmanTreeNode getLeft() {
        return left;
    }

    public HuffmanTreeNode getRight() {
        return right;
    }
}

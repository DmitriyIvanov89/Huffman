package huffmanarchiver;

public class HuffmanNode implements Comparable<HuffmanNode> {

    private final Byte nodeByte;
    private final int frequency;
    private HuffmanNode left;
    private HuffmanNode right;

    public HuffmanNode(Byte nodeByte, int frequency) {
        this.nodeByte = nodeByte;
        this.frequency = frequency;
    }

    public HuffmanNode(Byte nodeByte, int frequency, HuffmanNode left, HuffmanNode right) {
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

    public HuffmanNode getLeft() {
        return left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return o.frequency - frequency;
    }
}
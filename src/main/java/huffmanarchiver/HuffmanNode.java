package huffmanarchiver;

public class HuffmanNode implements Comparable<HuffmanNode> {

    private Short value;
    private final int frequency;
    private HuffmanNode left;
    private HuffmanNode right;

    public HuffmanNode(Short value, int frequency) {
        this.value = value;
        this.frequency = frequency;
    }

    public HuffmanNode(Short value, int frequency, HuffmanNode left, HuffmanNode right) {
        this.value = value;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public Short getValue() {
        return value;
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

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    public void setValue(short value) {
        this.value = value;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return o.frequency - frequency;
    }
}
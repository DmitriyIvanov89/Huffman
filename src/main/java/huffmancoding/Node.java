package huffmancoding;

public class Node {

    private final int frequency;
    private final char symbol;
    private Node leftChild;
    private Node rightChild;


    public Node(char symbol, int weight) {
        this.frequency = weight;
        this.symbol = symbol;
    }

    public Node(int weight, char symbol, Node leftChild, Node rightChild) {
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

}

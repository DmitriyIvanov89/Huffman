import huffmancoding.Frequency;
import huffmancoding.Tree;

public class Huffman {
    public static void main(String[] args) {

        String text = "abacabad";

        Frequency frequency = new Frequency(text);

        Tree huffman = new Tree(frequency.countFrequency());
        System.out.println(huffman.createHuffmanTree());


    }
}

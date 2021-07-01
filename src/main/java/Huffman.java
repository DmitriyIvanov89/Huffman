import huffmancoding.Coding;
import huffmancoding.HuffmanTree;

import java.util.Map;

public class Huffman {
    public static void main(String[] args) {

        String text = "abacabad";

        HuffmanTree tree = new HuffmanTree(text);
        tree.buildHuffmanTree();
        Coding coding = new Coding(text, tree);
        coding.generateCodesTable();
        System.out.println(coding.encodedText().toString());

    }
}

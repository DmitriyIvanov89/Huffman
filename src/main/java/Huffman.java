import huffmancoding.HuffmanTree;

import java.util.HashMap;
import java.util.Map;

public class Huffman {
    public static void main(String[] args) {

        String text = "abacabad";

        HuffmanTree tree = new HuffmanTree(text);
        tree.buildHuffmanTree();

    }
}

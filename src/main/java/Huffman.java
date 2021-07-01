import huffmancoding.HuffmanTree;

import java.util.Map;

public class Huffman {
    public static void main(String[] args) {

        String text = "abacabad";

        HuffmanTree tree = new HuffmanTree(text);
        tree.buildHuffmanTree();
        for (Map.Entry<Character, String> entry : tree.generateCodesTable().entrySet()) {
            System.out.println(entry.toString());
        }
    }
}

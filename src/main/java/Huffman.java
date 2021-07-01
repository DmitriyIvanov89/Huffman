import huffmancoding.Coding;
import huffmancoding.HuffmanTree;

import java.util.Map;

public class Huffman {
    public static void main(String[] args) {

        String text = "My name is Dmitriy";

        HuffmanTree tree = new HuffmanTree(text);
        tree.buildHuffmanTree();

        Coding coding = new Coding(text, tree);

        System.out.println("Generated codes table:");

        for (Map.Entry<Character, String> entry : coding.generateCodesTable().entrySet()) {
            System.out.println(String.format("symbol: %s, code: %s", entry.getKey(), entry.getValue()));
        }

        System.out.println(String.format("Coded text: %s" + "\n", coding.encodeText()));

    }
}

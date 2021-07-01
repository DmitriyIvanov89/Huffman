import huffmancoding.Archiver;
import huffmancoding.HuffmanTree;

import java.util.Map;

public class Huffman {
    public static void main(String[] args) {

        String text = "My name is Dmitriy";

        HuffmanTree tree = new HuffmanTree(text);
        tree.buildHuffmanTree();

        Archiver archiver = new Archiver(text, tree);

        System.out.println("Generated codes table:");

        for (Map.Entry<Character, String> entry : archiver.generateCodesTable().entrySet()) {
            System.out.printf("symbol: %s, code: %s%n", entry.getKey(), entry.getValue());
        }

        System.out.printf("Coded text: %s" + "\n", archiver.encodeText());

        System.out.printf("Decoded text: %s" + "\n", archiver.decodeCodes(archiver.encodeText(), tree));

    }
}

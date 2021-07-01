package huffmancoding;

import java.util.HashMap;
import java.util.Map;

public class Archiver {

    public static CompressedFile compress(String text) {
        HuffmanTree tree = new HuffmanTree(text);
        Map<Character, String> codes = new HashMap<>();
        fillCodesTable(tree.getRoot(), "", codes);
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            encoded.append(codes.get(text.charAt(i)));
        }
        System.out.println(encoded.toString());
        return new CompressedFile(encoded.toString(), tree.getFrequencies());
    }

    public static String decompress(CompressedFile compressedFile) {
        StringBuilder decoded = new StringBuilder();
        HuffmanTree tree = new HuffmanTree(compressedFile.getFrequencies());
        Node currNode = tree.getRoot();
        for (int i = 0; i < compressedFile.getEncoded().length(); i++) {
            if (compressedFile.getEncoded().charAt(i) == '0') {
                currNode = currNode.getLeft();
            } else {
                currNode = currNode.getRight();
            }
            if (currNode.getSymbol() != null) {
                decoded.append(currNode.getSymbol());
                currNode = tree.getRoot();
            }
        }
        System.out.println(decoded.toString());
        return decoded.toString();
    }

    private static void fillCodesTable(Node node, String path, Map<Character, String> codes) {
        if (node.getLeft() != null) {
            fillCodesTable(node.getLeft(), path + 0, codes);
        }
        if (node.getRight() != null) {
            fillCodesTable(node.getRight(), path + 1, codes);
        }
        codes.put(node.getSymbol(), path);
    }
}

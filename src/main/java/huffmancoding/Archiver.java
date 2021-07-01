package huffmancoding;

import java.util.HashMap;
import java.util.Map;

public class Archiver {

    private final String text;
    private final HuffmanTree tree;
    private Map<Character, String> codes;

    public Archiver(String text, HuffmanTree tree) {
        this.text = text;
        this.tree = tree;
    }

    public Map<Character, String> generateCodesTable() {
        codes = new HashMap<>();
        Node root = tree.getNodes().get(0);
        for (Character c : tree.getFrequencies().keySet()) {
            codes.put(c, root.encode(c, ""));
        }
        return codes;
    }

    public String encodeText() {
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            encoded.append(codes.get(text.charAt(i)));
        }
        return encoded.toString();
    }

    public String decodeCodes(String encoded, HuffmanTree tree) {
        StringBuilder decoded = new StringBuilder();
        Node root = tree.getNodes().get(0);
        Node currNode = root;
        for (int i = 0; i < encoded.length(); i++) {
            if (encoded.charAt(i) == '0') {
                currNode = currNode.getLeft();
            } else {
                currNode = currNode.getRight();
            }
            if (currNode.getSymbol() != null) {
                decoded.append(currNode.getSymbol());
                currNode = root;
            }
        }
        return decoded.toString();
    }
}

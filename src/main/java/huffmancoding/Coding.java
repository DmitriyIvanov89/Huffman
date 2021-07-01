package huffmancoding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Coding {

    private String text;
    private HuffmanTree tree;
    private List<Node> nodes;
    private Map<Character, Integer> frequencies;
    private Map<Character, String> codes;

    public Coding(String text, HuffmanTree tree) {
        this.text = text;
        this.frequencies = new HashMap<>();
        this.nodes = new ArrayList<>();
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

    public StringBuilder encodedText() {
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            encoded.append(codes.get(text.charAt(i)));
        }
        return encoded;
    }
}

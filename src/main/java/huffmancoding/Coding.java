package huffmancoding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Coding {

    private final String text;
    private final HuffmanTree tree;
    private Map<Character, Integer> frequencies;
    private List<Node> nodes;
    private Map<Character, String> codes;

    public Coding(String text, HuffmanTree tree) {
        this.text = text;
        this.tree = tree;
        this.frequencies = new HashMap<>();
        this.nodes = new ArrayList<>();
    }

    public Map<Character, String> generateCodesTable() {
        codes = new HashMap<>();
        Node root = tree.getNodes().get(0);
        for (Character c : tree.getFrequencies().keySet()) {
            codes.put(c, root.encode(c, ""));
        }
        return codes;
    }

    public String encodingText() {
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            encoded.append(codes.get(text.charAt(i)));
        }
        return encoded.toString();
    }

//    public String decodingCodes(String encoded, HuffmanTree tree) {
//        StringBuilder decoded = new StringBuilder();
//        Node node = tree.getNodes().get(0);
//        for (int i = 0; i < encoded.length(); i++) {
//
//        }
//    }
}

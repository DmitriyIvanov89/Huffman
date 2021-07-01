package huffmancoding;

import java.util.*;

public class HuffmanTree {

    private final String text;
    private Map<Character, Integer> frequencies;
    private List<Node> nodes;

    public HuffmanTree(String text) {
        this.text = text;
    }

    public Node buildHuffmanTree() {
        frequencies = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);
            if (frequencies.containsKey(symbol)) {
                frequencies.put(symbol, frequencies.get(symbol) + 1);
            } else {
                frequencies.put(symbol, 1);
            }
        }

        nodes = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            nodes.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (nodes.size() > 1) {
            Collections.sort(nodes);
            Node right = nodes.remove(nodes.size() - 1);
            Node left = nodes.remove(nodes.size() - 1);
            Node parent = new Node(null, left.getFrequency() + right.getFrequency(), left, right);
            nodes.add(parent);
        }
        return nodes.get(0);
    }

    public Map<Character, Integer> getFrequencies() {
        return frequencies;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    //    public Map<Character, String> generateCodesTable() {
//        Map<Character, String> codes = new HashMap<>();
//        Node root = nodes.get(0);
//        for (Character c : frequencies.keySet()) {
//            codes.put(c, root.encode(c, ""));
//        }
//        return codes;
//    }
}

package huffmancoding;

import java.util.*;

public class HuffmanTree {

    private final String text;

    public HuffmanTree(String text) {
        this.text = text;
    }

    public Node buildHuffmanTree() {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);
            if (frequencies.containsKey(symbol)) {
                frequencies.put(symbol, frequencies.get(symbol) + 1);
            } else {
                frequencies.put(symbol, 1);
            }
        }

        List<Node> nodes = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            nodes.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (nodes.size() > 1) {
            Collections.sort(nodes);
            Node left = nodes.remove(nodes.size() - 1);
            Node right = nodes.remove(nodes.size() - 1);
            Node parent = new Node(null, left.getFrequency() + right.getFrequency(), left, right);
            nodes.add(parent);
        }
        return nodes.get(0);
    }
}

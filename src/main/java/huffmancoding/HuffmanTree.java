package huffmancoding;

import java.util.*;

public class HuffmanTree {

    private Node root;
    private Map<Character, Integer> frequencies;

    public HuffmanTree(Map<Character, Integer> frequencies) {
        init(frequencies);
    }

    public HuffmanTree(String text) {
        init(countFrequencies(text));
    }

    private void init(Map<Character, Integer> frequencies) {
        this.frequencies = frequencies;
        List<Node> nodes = new ArrayList<>();
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

        this.root = nodes.get(0);
    }

    public Node getRoot() {
        return root;
    }

    public Map<Character, Integer> getFrequencies() {
        return frequencies;
    }

    private Map<Character, Integer> countFrequencies(String text) {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);
            if (frequencies.containsKey(symbol)) {
                frequencies.put(symbol, frequencies.get(symbol) + 1);
            } else {
                frequencies.put(symbol, 1);
            }
        }
        return frequencies;
    }
}
package huffmancoding;

import java.util.*;

public class Tree {

    private String text;
    private Node node;
    private Map<Character, Integer> frequencies;

    public Map<Character, Integer> countFrequency(String text) {
        frequencies = new HashMap<>();
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

    public Node buildHuffmanTree(String text) {
        List<Node> nodes = new ArrayList<>();
        for (Character c : frequencies.keySet()) {
            nodes.add(new Node(c, frequencies.get(c)));
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

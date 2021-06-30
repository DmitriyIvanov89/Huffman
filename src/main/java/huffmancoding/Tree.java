package huffmancoding;

import java.util.*;

public class Tree {

    private Node node;
    private Map<Character, Integer> frequencies;

    public Tree(Map<Character, Integer> frequencies) {
        this.frequencies = new HashMap<>();
    }

    public Node createHuffmanTree() {
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

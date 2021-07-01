package huffmancoding;

import java.util.*;

public class Archiver {

    public CompressedFile compress(String text) {
        Map<Character, Integer> frequencies = countFrequencies(text);
        Node root = generateCodesTree(frequencies);
        Map<Character, String> codes = new HashMap<>();
        fillCodesTable(root, "", codes);
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            encoded.append(codes.get(text.charAt(i)));
        }
        System.out.println(encoded);
        return new CompressedFile(encoded.toString(), frequencies);
    }

    public String decompress(CompressedFile compressedFile) {
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
        System.out.println(decoded);
        return decoded.toString();
    }

    private void fillCodesTable(Node node, String path, Map<Character, String> codes) {
        if (node.getLeft() != null) {
            fillCodesTable(node.getLeft(), path + 0, codes);
        }
        if (node.getRight() != null) {
            fillCodesTable(node.getRight(), path + 1, codes);
        }
        codes.put(node.getSymbol(), path);
    }

    private Node generateCodesTree(Map<Character, Integer> frequencies) {
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

        return nodes.get(0);
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
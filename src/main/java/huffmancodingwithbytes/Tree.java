package huffmancodingwithbytes;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.*;

public class Tree {

    private HuffmanNode root;
    private Map<Byte, Integer> frequencies;

    public void init(Map<Byte, Integer> frequencies) {
        List<HuffmanNode> nodes = new ArrayList<>();
        for (Map.Entry<Byte, Integer> entry : frequencies.entrySet()) {
            nodes.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (nodes.size() > 1) {
            Collections.sort(nodes);
            HuffmanNode right = nodes.remove(nodes.size() - 1);
            HuffmanNode left = nodes.remove(nodes.size() - 1);
            HuffmanNode parent = new HuffmanNode(null, left.getFrequency() + right.getFrequency(), left, right);
            nodes.add(parent);
        }

        this.root = nodes.get(0);
    }

    private Map<Byte, Integer> countFrequencies(DataInputStream inputStream) throws IOException {
        Map<Byte, Integer> frequencies = new HashMap<>();
        while (inputStream.available() > 0) {
            byte symbol = inputStream.readByte();
            if (frequencies.containsKey(symbol)) {
                frequencies.put(symbol, frequencies.get(symbol) + 1);
            } else {
                frequencies.put(symbol, 1);
            }
        }
        return frequencies;
    }

    public HuffmanNode getRoot() {
        return root;
    }

    public Map<Byte, Integer> getFrequencies() {
        return frequencies;
    }
}

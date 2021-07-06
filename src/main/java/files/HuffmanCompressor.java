package files;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class HuffmanCompressor {

    private String path;
    private FileHuffman fileReaderHuffman;

    public void compress(File file) throws IOException {
        FileHuffman fileReaderHuffman = new FileHuffman(path);
        Map<Byte, Integer> frequencies = countFrequencies(path);
        NodeHufTreeWithBytes root = generateCodesTree(frequencies);
        Map<Byte, String> codes = new HashMap<>();

    }

    private void fillCodesTable(NodeHufTreeWithBytes nodeHufTreeWithBytes, String path, Map<Byte, String> codes) {
        if (nodeHufTreeWithBytes.getLeft() != null) {
            fillCodesTable(nodeHufTreeWithBytes.getLeft(), path + 0, codes);
        }
        if (nodeHufTreeWithBytes.getRight() != null) {
            fillCodesTable(nodeHufTreeWithBytes.getRight(), path + 1, codes);
        }
        codes.put(nodeHufTreeWithBytes.getSymbolByte(), path);
    }

    private NodeHufTreeWithBytes generateCodesTree(Map<Byte, Integer> frequencies) {
        List<NodeHufTreeWithBytes> nodes = new ArrayList<>();
        for (Map.Entry<Byte, Integer> entry : frequencies.entrySet()) {
            nodes.add(new NodeHufTreeWithBytes(entry.getKey(), entry.getValue()));
        }

        while (nodes.size() > 1) {
            Collections.sort(nodes);
            NodeHufTreeWithBytes right = nodes.remove(nodes.size() - 1);
            NodeHufTreeWithBytes left = nodes.remove(nodes.size() - 1);
            NodeHufTreeWithBytes parent = new NodeHufTreeWithBytes(null, left.getFrequency() + right.getFrequency(), left, right);
            nodes.add(parent);
        }

        return nodes.get(0);
    }

    private Map<Byte, Integer> countFrequencies(String path) throws IOException {
        FileHuffman fileReaderHuffman = new FileHuffman(path);
        byte[] arr = fileReaderHuffman.readFile();
        Map<Byte, Integer> frequencies = new HashMap<>();
        for (byte byteFromFile : arr) {
            if (frequencies.containsKey(byteFromFile)) {
                frequencies.put(byteFromFile, frequencies.get(byteFromFile) + 1);
            } else {
                frequencies.put(byteFromFile, 1);
            }
        }
        return frequencies;
    }
}

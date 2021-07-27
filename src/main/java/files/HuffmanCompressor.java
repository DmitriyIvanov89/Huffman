package files;

import java.io.*;
import java.util.*;

public class HuffmanCompressor {

    private static final String OUTPUT = ".\\src\\main\\resources\\compressed.txt";
    private static final String DECOMPRESS = ".\\src\\main\\resources\\decompress.txt";
    File outputFile = new File(OUTPUT);
    File decompressedFile = new File(DECOMPRESS);

    public File compress(File originFile) throws IOException {

        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(originFile), 4096));
             DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputFile), 4096))) {

            Map<Byte, Integer> frequencies = countFrequencies(in);
            HuffmanTreeNode root = generateCodesTree(frequencies);
            Map<Byte, String> codes = new HashMap<>();
            fillCodesTable(root, "", codes);

            out.write(codes.size());

            for (Map.Entry<Byte, String> entry : codes.entrySet()) {
                out.write(entry.getKey());
                out.writeBytes(entry.getValue());
            }
        }
        return outputFile;
    }

    public File decompress(File outputFile) throws IOException {

        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(outputFile), 4096));
             DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(decompressedFile), 4096))) {

            
        }

        return null;

    }

    private Map<Byte, Integer> countFrequencies(DataInputStream in) throws IOException {
        Map<Byte, Integer> frequencies = new HashMap<>();
        while (in.available() > 0) {
            byte symbol = in.readByte();
            if (frequencies.containsKey(symbol)) {
                frequencies.put(symbol, frequencies.get(symbol) + 1);
            } else {
                frequencies.put(symbol, 1);
            }
        }
        return frequencies;
    }

    private HuffmanTreeNode generateCodesTree(Map<Byte, Integer> frequencies) {
        List<HuffmanTreeNode> nodes = new ArrayList<>();
        for (Map.Entry<Byte, Integer> entry : frequencies.entrySet()) {
            nodes.add(new HuffmanTreeNode(entry.getKey(), entry.getValue()));
        }

        while (nodes.size() > 1) {
            Collections.sort(nodes);
            HuffmanTreeNode right = nodes.remove(nodes.size() - 1);
            HuffmanTreeNode left = nodes.remove(nodes.size() - 1);
            HuffmanTreeNode parent = new HuffmanTreeNode(null, left.getFrequency() + right.getFrequency(), left, right);
            nodes.add(parent);
        }

        return nodes.get(0);
    }

    private void fillCodesTable(HuffmanTreeNode node, String path, Map<Byte, String> codes) {
        if (node.getLeft() != null) {
            fillCodesTable(node.getLeft(), path + 0, codes);
        }
        if (node.getRight() != null) {
            fillCodesTable(node.getRight(), path + 1, codes);
        }
        if (node.getLeft() == null && node.getRight() == null) {
            codes.put(node.getNodeByte(), path);
        }
    }

}
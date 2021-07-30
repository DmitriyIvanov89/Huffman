package huffmancodingwithbytes;

import java.io.*;
import java.util.*;

public class HuffmanCompressor {

    private static final String OUTPUT = ".\\src\\main\\resources\\compressed.txt";
    private static final String DECOMPRESS = ".\\src\\main\\resources\\decompress.txt";
    private final File outputFile = new File(OUTPUT);
    private final File decompressedFile = new File(DECOMPRESS);

    public File compress(File originFile) throws IOException {

        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(originFile), 4096));
             DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputFile), 4096))) {

            in.mark(0);

            Map<Byte, Integer> frequencies = countFrequencies(in);
            HuffmanTreeNode root = generateCodesTree(frequencies);
            Map<Byte, String> codes = new HashMap<>();
            fillCodesTable(root, "", codes);

            out.write(codes.size());

            for (Map.Entry<Byte, String> entry : codes.entrySet()) {
                out.writeByte(entry.getKey());
                out.writeUTF(codes.get(entry.getKey()));
            }

            in.reset();

            StringBuilder encodedData = new StringBuilder();
            while (in.available() > 0) {
                encodedData.append(codes.get(in.readByte()));
            }
            out.writeBytes(encodedData.toString());
        }

        return outputFile;
    }

    public File decompress(File outputFile) throws IOException {

        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(outputFile), 4096));
             DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(decompressedFile), 4096))) {

            Map<Byte, String> codes = new HashMap<>();

            int codesTableSize = in.readByte();
            for (int i = 0; i < codesTableSize; i++) {
                codes.put(in.readByte(), in.readUTF());
            }

            List<String> allCodes = new ArrayList<>();
            while (in.available() > 0) {
                allCodes.add(in.readUTF());
            }



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
            fillCodesTable(node.getLeft(), path + "0", codes);
        }
        if (node.getRight() != null) {
            fillCodesTable(node.getRight(), path + "1", codes);
        }
        if (node.getLeft() == null && node.getRight() == null) {
            codes.put(node.getNodeByte(), path);
        }
    }
}
package huffmanarchiver;

import java.io.*;
import java.util.*;

public class HuffmanCompressor {

    private final File outputFile = new File(".\\src\\main\\resources\\compressed");
    private final File decompressedFile = new File(".\\src\\main\\resources\\decompress");

    public File compress(File originFile) throws IOException {

        try (DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(originFile)));
             DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputFile)))) {

            Map<Byte, Integer> frequencies = countFrequencies(dataInputStream);
            HuffmanNode root = generateCodesTree(frequencies);
            Map<Byte, String> codes = new HashMap<>();
            fillCodesTable(root, "", codes);

            dataOutputStream.write(codes.size());

            for (Map.Entry<Byte, String> entry : codes.entrySet()) {
                dataOutputStream.writeByte(entry.getKey());
                dataOutputStream.writeUTF(codes.get(entry.getKey()));
            }
            // add EOF symbol ?
            try (DataInputStream secondInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(originFile)))) {

                while (secondInputStream.available() > 0) {
                    dataOutputStream.writeBytes(codes.get(secondInputStream.readByte()));
                }
            }
        }

        return outputFile;
    }

    public File decompress(File outputFile) throws IOException {

        try (DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(outputFile)));
             DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(decompressedFile)))) {

            Map<Byte, String> codes = new HashMap<>();

            int codesTableSize = dataInputStream.readByte();
            for (int i = 0; i < codesTableSize; i++) {
                codes.put(dataInputStream.readByte(), dataInputStream.readUTF());
            }

            StringBuilder encodedFromFile = new StringBuilder();

            while (dataInputStream.available() > 0) {
                encodedFromFile.append(dataInputStream.readLine());
            }

            // refactor this method
            HuffmanNode root = new HuffmanNode(null, 0);
            for (Map.Entry<Byte, String> entry : codes.entrySet()) {
                HuffmanNode currNode = root;
                for (int i = 0; i < entry.getValue().length(); i++) {
                    if (entry.getValue().charAt(i) == '1') {
                        if (currNode.getRight() == null) {
                            HuffmanNode newNode = new HuffmanNode(i == entry.getValue().length() - 1 ? entry.getKey() : null, 0);
                            currNode.setRight(newNode);
                            currNode = newNode;
                        } else {
                            currNode = currNode.getRight();
                            currNode.setNodeByte(i == entry.getValue().length() - 1 ? entry.getKey() : null);
                        }
                    } else if (entry.getValue().charAt(i) == '0') {
                        if (currNode.getLeft() == null) {
                            HuffmanNode newNode = new HuffmanNode(i == entry.getValue().length() - 1 ? entry.getKey() : null, 0);
                            currNode.setLeft(newNode);
                            currNode = newNode;
                        } else {
                            currNode = currNode.getLeft();
                            currNode.setNodeByte(i == entry.getValue().length() - 1 ? entry.getKey() : null);
                        }
                    }
                }
            }

            HuffmanNode currNode = root;
            for (int i = 0; i < encodedFromFile.length(); i++) {
                if (encodedFromFile.charAt(i) == '0') {
                    currNode = currNode.getLeft();
                } else {
                    currNode = currNode.getRight();
                }
                if (currNode.getNodeByte() != null) {
                    dataOutputStream.writeByte(currNode.getNodeByte());
                    currNode = root;
                }
            }


            return decompressedFile;
        }
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

    private HuffmanNode generateCodesTree(Map<Byte, Integer> frequencies) {
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

        return nodes.get(0);
    }

    private void fillCodesTable(HuffmanNode node, String path, Map<Byte, String> codes) {
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
package huffmanarchiver;

import java.io.*;
import java.util.*;

public class HuffmanCompressor {

    private final File outputFile = new File(".\\src\\main\\resources\\compressed");
    private final File decompressedFile = new File(".\\src\\main\\resources\\decompress");

    public File compress(File originFile) throws IOException {

        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        DataInputStream dataInputStream = null;
        OutputStream outputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        DataOutputStream dataOutputStream = null;

        try {

            inputStream = new FileInputStream(originFile);
            bufferedInputStream = new BufferedInputStream(inputStream);
            dataInputStream = new DataInputStream(bufferedInputStream);

            outputStream = new FileOutputStream(outputFile);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            dataOutputStream = new DataOutputStream(bufferedOutputStream);

            Map<Byte, Integer> frequencies = countFrequencies(dataInputStream);
            HuffmanNode root = generateCodesTree(frequencies);
            Map<Byte, String> codes = new HashMap<>();
            fillCodesTable(root, "", codes);

            bufferedOutputStream.write(codes.size());

            for (Map.Entry<Byte, String> entry : codes.entrySet()) {
                dataOutputStream.writeByte(entry.getKey());
                dataOutputStream.writeUTF(codes.get(entry.getKey()));
            }

            /*
             * add EOF symbol for fill last byte
             * open second stream for read bits from file
             */
            try (DataInputStream secondInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(originFile), 4096))) {
                StringBuilder encodedData = new StringBuilder();

                while (secondInputStream.available() > 0) {
                    encodedData.append(codes.get(secondInputStream.readByte()));
                }

                dataOutputStream.writeUTF(encodedData.toString());
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        } finally {
            assert dataOutputStream != null;
            dataOutputStream.close();
            bufferedInputStream.close();
            outputStream.close();
            dataInputStream.close();
            bufferedInputStream.close();
            inputStream.close();
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

            StringBuilder encodedFromFile = new StringBuilder();
            while (in.available() > 0) {
                encodedFromFile.append(in.readUTF());
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
                    out.writeByte(currNode.getNodeByte());
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
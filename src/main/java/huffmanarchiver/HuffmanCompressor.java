package huffmanarchiver;

import java.io.*;
import java.util.*;

public class HuffmanCompressor {

    public void archive(String pathOriginFile, String pathToArchivedFile) throws IOException {

        Map<Short, Integer> frequencies = countFrequencies(pathOriginFile);
        HuffmanNode root = generateCodesTree(frequencies);
        Map<Short, String> codes = new HashMap<>();
        fillCodesTable(root, "", codes);
        StringBuilder encodedData = encodeData(pathOriginFile, codes);
        writeToFile(pathToArchivedFile, encodedData, codes);

    }

    public void unzip(String pathToArchiveFile, String pathToUnzippedFile) throws IOException {
        /*
        Refactor method
        * 1. Read codes table size -- readDataFromArchivedFile
        * 2. Read codes table(on size from 1 step) -- readDataFromArchivedFile
        * 3. Read decoded data from file -- readDataFromArchivedFile
        * 4. Create Huffman tree from codes table --
        * 5. Walking the tree with decryption of data
        */

        decodeData(pathToArchiveFile);


//        try (DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(archivedFile)));
//             DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(unzippedFile)))) {
//
//            Map<Byte, String> codes = new HashMap<>();
//
//            int codesTableSize = dataInputStream.readInt();
//            for (int i = 0; i < codesTableSize; i++) {
//                codes.put(dataInputStream.readByte(), dataInputStream.readUTF());
//            }
//
//            StringBuilder encodedFromFile = new StringBuilder();
//
//            while (dataInputStream.available() > 0) {
//                encodedFromFile.append(dataInputStream.readLine());
//            }
//
//            // refactor this method
//        HuffmanNode root = new HuffmanNode(null, 0);
//        for (Map.Entry<Byte, String> entry : codes.entrySet()) {
//            HuffmanNode currNode = root;
//            for (int i = 0; i < entry.getValue().length(); i++) {
//                if (entry.getValue().charAt(i) == '1') {
//                    if (currNode.getRight() == null) {
//                        HuffmanNode newNode = new HuffmanNode(i == entry.getValue().length() - 1 ? entry.getKey() : null, 0);
//                        currNode.setRight(newNode);
//                        currNode = newNode;
//                    } else {
//                        currNode = currNode.getRight();
//                        currNode.setNodeByte(i == entry.getValue().length() - 1 ? entry.getKey() : null);
//                    }
//                } else if (entry.getValue().charAt(i) == '0') {
//                    if (currNode.getLeft() == null) {
//                        HuffmanNode newNode = new HuffmanNode(i == entry.getValue().length() - 1 ? entry.getKey() : null, 0);
//                        currNode.setLeft(newNode);
//                        currNode = newNode;
//                    } else {
//                        currNode = currNode.getLeft();
//                        currNode.setNodeByte(i == entry.getValue().length() - 1 ? entry.getKey() : null);
//                    }
//                }
//            }
//        }
//
//            HuffmanNode currNode = root;
//            for (int i = 0; i < encodedFromFile.length(); i++) {
//                if (encodedFromFile.charAt(i) == '0') {
//                    currNode = currNode.getLeft();
//                } else {
//                    currNode = currNode.getRight();
//                }
//                if (currNode.getNodeByte() != null) {
//                    dataOutputStream.writeByte(currNode.getNodeByte());
//                    currNode = root;
//                }
//            }
//
//            return unzippedFile;
//        }
    }

    private Map<Short, Integer> countFrequencies(String pathOriginFile) throws IOException {
        Map<Short, Integer> frequencies = new HashMap<>();
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(pathOriginFile))) {
            while (dataInputStream.available() > 0) {
                short symbol = dataInputStream.readByte();
                if (frequencies.containsKey(symbol)) {
                    frequencies.put(symbol, frequencies.get(symbol) + 1);
                } else {
                    frequencies.put(symbol, 1);
                }
            }
        }

        return frequencies;
    }

    private HuffmanNode generateCodesTree(Map<Short, Integer> frequencies) {
        List<HuffmanNode> nodes = new ArrayList<>();
        for (Map.Entry<Short, Integer> entry : frequencies.entrySet()) {
            nodes.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }
        HuffmanNode eof = new HuffmanNode(Short.MAX_VALUE, -1);
        nodes.add(eof);

        while (nodes.size() > 1) {
            Collections.sort(nodes);
            HuffmanNode right = nodes.remove(nodes.size() - 1);
            HuffmanNode left = nodes.remove(nodes.size() - 1);
            HuffmanNode parent = new HuffmanNode(null, left.getFrequency() + right.getFrequency(), left, right);
            nodes.add(parent);
        }

        return nodes.get(0);
    }

    private void fillCodesTable(HuffmanNode node, String path, Map<Short, String> codes) {
        if (node.getLeft() != null) {
            fillCodesTable(node.getLeft(), path + "0", codes);
        }
        if (node.getRight() != null) {
            fillCodesTable(node.getRight(), path + "1", codes);
        }
        if (node.getLeft() == null && node.getRight() == null) {
            codes.put(node.getValue(), path);
        }
    }

    private StringBuilder encodeData(String pathOriginFile, Map<Short, String> codes) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(pathOriginFile))) {
            while (dataInputStream.available() > 1) {
                short symbol = dataInputStream.readByte();
                stringBuilder.append(codes.get(symbol));
            }
        }
        stringBuilder.append(codes.get(Short.MAX_VALUE));
        return stringBuilder;
    }

    private void writeToFile(String pathToArchivedFile, StringBuilder encodedData, Map<Short, String> codes) throws IOException {

        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(pathToArchivedFile))) {

            dataOutputStream.writeByte(codes.size());
            // associate a short with a number of bytes
            for (Map.Entry<Short, String> entry : codes.entrySet()) {
                dataOutputStream.writeByte(entry.getKey());
                dataOutputStream.write(entry.getValue().getBytes());
            }

            StringBuilder bits = new StringBuilder();
            int countBits = 0;
            for (int i = 0; i < encodedData.length(); i++) {
                countBits++;
                bits.append(encodedData.charAt(i));
                if (countBits % 8 == 0) {
                    byte currByte = (byte) Short.parseShort(bits.toString(), 2);
                    dataOutputStream.writeByte(currByte);
                    bits.delete(0, bits.length());
                    countBits = 0;
                }
            }
        }
    }

    private void decodeData(String pathToArchiveFile) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(pathToArchiveFile))) {
            Map<Byte, String> codes = new HashMap<>();
            int codesTableSize = dataInputStream.readByte();

//            for (int i = 0; i < codesTableSize; i++) {
//                codes.put(dataInputStream.readByte(), new String(dataInputStream.read()));
//            }

//            StringBuilder stringBuilder = new StringBuilder();
//            while (dataInputStream.available() > 0) {
//                stringBuilder.append(dataInputStream.read());
//            }

//            HuffmanNode root = new HuffmanNode(null, 0);
//            for (Map.Entry<Byte, Byte> entry : codes.entrySet()) {
//                HuffmanNode currNode = root;
//                for (int i = 0; i < entry.getValue(); i++) {
//                    if (entry.getValue().charAt(i) == '1') {
//                        if (currNode.getRight() == null) {
//                            HuffmanNode newNode = new HuffmanNode(i == entry.getValue().length() - 1 ? entry.getKey() : null, 0);
//                            currNode.setRight(newNode);
//                            currNode = newNode;
//                        } else {
//                            currNode = currNode.getRight();
//                            currNode.setNodeByte(i == entry.getValue().length() - 1 ? entry.getKey() : null);
//                        }
//                    } else if (entry.getValue().charAt(i) == '0') {
//                        if (currNode.getLeft() == null) {
//                            HuffmanNode newNode = new HuffmanNode(i == entry.getValue().length() - 1 ? entry.getKey() : null, 0);
//                            currNode.setLeft(newNode);
//                            currNode = newNode;
//                        } else {
//                            currNode = currNode.getLeft();
//                            currNode.setNodeByte(i == entry.getValue().length() - 1 ? entry.getKey() : null);
//                        }
//                    }
//                }
//            }
//        }
        }
    }

    private void writeToUnzippedFile(String pathToUnzippedFile) {
    }
}
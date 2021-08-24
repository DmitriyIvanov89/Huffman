package huffmanarchiver;

import java.io.*;
import java.util.*;

public class HuffmanCompressor {

    public void archive(String pathOriginFile) throws IOException {

        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(".\\src\\main\\resources\\archive"))) {

            Map<Short, Integer> frequencies = countFrequencies(pathOriginFile);
            // add EOF Node to Huffman Tree
            HuffmanNode root = generateCodesTree(frequencies);
            insertEofSymbol(root);
            Map<Short, String> codes = new HashMap<>();
            fillCodesTable(root, "", codes);

            dataOutputStream.write(codes.size());

            // change this method (bit record with blocking queue)
            for (Map.Entry<Short, String> entry : codes.entrySet()) {
                dataOutputStream.write(entry.getKey());
                dataOutputStream.writeBytes(entry.getValue());
            }
            // change this method (bit record with blocking queue)
            writeAllDataFromFile(pathOriginFile, dataOutputStream, codes);
        }
    }

//    public File unzip(File archivedFile) throws IOException {
//
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
//            HuffmanNode root = new HuffmanNode(null, 0);
//            for (Map.Entry<Byte, String> entry : codes.entrySet()) {
//                HuffmanNode currNode = root;
//                for (int i = 0; i < entry.getValue().length(); i++) {
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
//    }

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

    private void writeAllDataFromFile(String pathOriginFile, DataOutputStream dataOutputStream, Map<Short, String> codes) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(pathOriginFile))) {
            while (dataInputStream.available() > 0) {
                dataOutputStream.writeBytes(codes.get(dataInputStream.readByte()));
            }
        }
    }

    private HuffmanNode insertEofSymbol(HuffmanNode root) {
        HuffmanNode eof = new HuffmanNode(null, 0);
        if (root.getLeft() != null) {
            insertEofSymbol(root.getLeft());
        } else if (root.getRight() != null) {
            insertEofSymbol(root.getRight());
        } else if (root.getLeft() == null && root.getRight() == null) {
            root.setLeft(eof);
        }
        return root;
    }
}
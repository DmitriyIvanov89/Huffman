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
        writeEncodedDataToFile(pathToArchivedFile, encodedData, codes);

    }

    public void unzip(String pathToArchiveFile, String pathToUnzippedFile) throws IOException {

        decodeData(pathToArchiveFile);

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
            while (dataInputStream.available() > 0) {
                short symbol = dataInputStream.readByte();
                stringBuilder.append(codes.get(symbol));
            }
            int counter = 0;
            for (int length = stringBuilder.length(), delta = 8 - stringBuilder.length() % 8; counter < delta; counter++) {
                stringBuilder.append("0");
            }
        }
        return stringBuilder;
    }

    private void writeEncodedDataToFile(String pathToArchivedFile, StringBuilder encodedData, Map<Short, String> codes) throws IOException {

        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(pathToArchivedFile))) {

            dataOutputStream.writeByte(codes.size());

            for (Map.Entry<Short, String> entry : codes.entrySet()) {
                dataOutputStream.write(entry.getKey());
                byte[] codesBytes = entry.getValue().getBytes();
                dataOutputStream.writeByte(codesBytes.length);
                for (byte byteStr : codesBytes) {
                    dataOutputStream.write(byteStr);
                }
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
            Map<Short, String> codes = new HashMap<>();
            int codesTableSize = dataInputStream.readByte();
            for (int i = 0; i < codesTableSize; i++) {
                short symbol = dataInputStream.readByte();
                byte codesSize = dataInputStream.readByte();
                byte[] arr = new byte[codesSize];
                for (int j = 0; j < codesSize; j++) {
                    arr[j] = dataInputStream.readByte();
                }
                String code = new String(arr);
                codes.put(symbol, code);
            }

            StringBuilder encoded = new StringBuilder();
            while (dataInputStream.available() > 0) {
                encoded.append(Integer.toBinaryString(dataInputStream.read())); // & 0xff
            }

            HuffmanNode root = new HuffmanNode(null, 0);
            for (Map.Entry<Short, String> entry : codes.entrySet()) {
                HuffmanNode currNode = root;
                for (int i = 0; i < entry.getValue().length(); i++) {
                    if (entry.getValue().charAt(i) == '1') {
                        if (currNode.getRight() == null) {
                            HuffmanNode newNode = new HuffmanNode(i == entry.getValue().length() - 1 ? entry.getKey() : null, 0);
                            currNode.setRight(newNode);
                            currNode = newNode;
                        } else {
                            currNode = currNode.getRight();
                            currNode.setValue(i == entry.getValue().length() - 1 ? entry.getKey() : null);
                        }
                    } else if (entry.getValue().charAt(i) == '0') {
                        if (currNode.getLeft() == null) {
                            HuffmanNode newNode = new HuffmanNode(i == entry.getValue().length() - 1 ? entry.getKey() : null, 0);
                            currNode.setLeft(newNode);
                            currNode = newNode;
                        } else {
                            currNode = currNode.getLeft();
                            currNode.setValue(i == entry.getValue().length() - 1 ? entry.getKey() : null);
                        }
                    }
                }
            }
        }
    }

//    private void writeDecodedDataToFile(String pathToUnzippedFile, StringBuilder encoded, Map<Short, String> codes) throws IOException {
//
//        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(pathToUnzippedFile))) {
//
//            HuffmanNode root = new HuffmanNode(null, 0);
//            HuffmanNode currNode = root;
//            for (int i = 0; i < encoded.length(); i++) {
//                if (encoded.charAt(i) == '0') {
//                    currNode = currNode.getLeft();
//                } else {
//                    currNode = currNode.getRight();
//                }
//                if (currNode.getValue() != null) {
//                    dataOutputStream.writeByte(currNode.getValue());
//                    currNode = root;
//                }
//            }
//        }

}
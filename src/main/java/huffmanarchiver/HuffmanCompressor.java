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

        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(pathToArchiveFile))) {
            int addedZero = getAddedZeroNumber(dataInputStream);
            Map<Short, String> codes = new HashMap<>();
            fillCodesTableFromFile(dataInputStream, codes);
            int codesTableSize = codes.size();
            StringBuilder encodedData = fillEncodedData(dataInputStream, codesTableSize);
            HuffmanNode root = generateTreeFromCodes(codes);
            writeUnzipDataToFile(pathToUnzippedFile, root, addedZero, encodedData);

        }
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
        }
        return stringBuilder;
    }

    private void writeEncodedDataToFile(String pathToArchivedFile, StringBuilder encodedData, Map<Short, String> codes) throws IOException {

        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(pathToArchivedFile))) {
            int zeroCount = 0;
            for (int length = encodedData.length(), delta = 8 - encodedData.length() % 8; zeroCount < delta; zeroCount++) {
                encodedData.append("0");
            }

            dataOutputStream.writeByte(zeroCount);
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

    private int getAddedZeroNumber(DataInputStream dataInputStream) throws IOException {
        return dataInputStream.readByte();
    }

    private void fillCodesTableFromFile(DataInputStream dataInputStream, Map<Short, String> codes) throws IOException {
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
    }

    private StringBuilder fillEncodedData(DataInputStream dataInputStream, int codesTableSize) throws IOException {
        StringBuilder encodedData = new StringBuilder();
        while (dataInputStream.available() > 0) {
            String binary = String.format("%8s", Integer.toBinaryString(dataInputStream.read())).replace(' ', '0');
            encodedData.append(binary);
        }
        return encodedData;
    }

    private HuffmanNode generateTreeFromCodes(Map<Short, String> codes) {
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

        return root;
    }

    private void writeUnzipDataToFile(String pathToUnzippedFile, HuffmanNode root, int addedZero, StringBuilder encodedData) throws IOException {

        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(pathToUnzippedFile))) {
            HuffmanNode currNode = root;
            for (int i = 0; i < encodedData.length() - addedZero; i++) {
                if (encodedData.charAt(i) == '0') {
                    currNode = currNode.getLeft();
                } else {
                    currNode = currNode.getRight();
                }
                if (currNode.getValue() != null) {
                    dataOutputStream.writeByte(currNode.getValue());
                    currNode = root;
                }
            }
        }
    }
}
import huffmanarchiver.HuffmanCompressor;

import java.io.*;


public class Huffman {

    public static void main(String[] args) throws IOException {

        new HuffmanCompressor().archive(".\\src\\main\\resources\\War.txt", ".\\src\\main\\resources\\archived.huf");

    }
}

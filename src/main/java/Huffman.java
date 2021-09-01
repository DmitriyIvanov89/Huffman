import huffmanarchiver.HuffmanCompressor;

import java.io.*;

public class Huffman {

    public static void main(String[] args) throws IOException {

        new HuffmanCompressor().archive(".\\src\\main\\resources\\test", ".\\src\\main\\resources\\archived.huf");
//        new HuffmanCompressor().unzip(".\\src\\main\\resources\\archived.huf", ".\\src\\main\\resources\\unzippedFile.txt");

//        String str = "0010100100101111010110011000101111101111001001110000010111101010000000110110000101001010000110100110110110";
//        System.out.println(str.length() % 8);
//        int delta = 8 - str.length() % 8;
//        System.out.println(delta);

    }
}

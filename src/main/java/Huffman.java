import files.HuffmanCompressor;

import java.io.*;

public class Huffman {

    private static final String PATH = ".\\src\\main\\resources\\test.txt";
    private static final File file = new File(PATH);

    public static void main(String[] args) throws IOException {

//        String text = "All cats are beautiful!";
//
//        CompressedFile compressedFile = new Archiver().compress(text);
//        String decompressResult = new Archiver().decompress(compressedFile);

        HuffmanCompressor huffmanCompressor = new HuffmanCompressor();
        File compress = huffmanCompressor.compress(file);

    }
}

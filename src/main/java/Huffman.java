import huffmanarchiver.HuffmanCompressor;

import java.io.*;

public class Huffman {

    private static final String PATH = ".\\src\\main\\resources\\test2";
    private static final File file = new File(PATH);

    public static void main(String[] args) throws IOException {

        File compress = new HuffmanCompressor().compress(file);
//        File decompress = new HuffmanCompressor().decompress(compress);

    }
}

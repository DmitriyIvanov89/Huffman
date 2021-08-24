import huffmanarchiver.HuffmanCompressor;

import java.io.*;

public class Huffman {

    private static final String PATH = ".\\src\\main\\resources\\test";
    private static final File originFile = new File(PATH);

    public static void main(String[] args) throws IOException {

        new HuffmanCompressor().archive(PATH);
        File unzipped = new HuffmanCompressor().unzip(archived);

    }
}

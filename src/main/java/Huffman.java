import huffmanarchiver.HuffmanCompressor;

import java.io.*;

public class Huffman {

    private static final String PATH = ".\\src\\main\\resources\\test2";
    private static final File originFile = new File(PATH);

    public static void main(String[] args) throws IOException {

        File archived = new HuffmanCompressor().archive(originFile);
        File unzipped = new HuffmanCompressor().unzip(archived);

    }
}

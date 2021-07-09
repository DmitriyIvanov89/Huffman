import huffmancoding.Archiver;
import huffmancoding.CompressedFile;

import java.io.File;

public class Huffman {

    private static final String PATH = ".\\src\\main\\resources\\test.txt";
    private static final File file = new File(PATH);

    public static void main(String[] args) {

        String text = "All cats are beautiful!";

        CompressedFile compressedFile = new Archiver().compress(text);
        String decompressResult = new Archiver().decompress(compressedFile);

    }
}

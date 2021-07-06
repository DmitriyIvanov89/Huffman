import files.FileReaderHuffman;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Huffman {

    private static final String PATH = ".\\src\\main\\resources\\test.txt";

    public static void main(String[] args) throws IOException {

        /*
        String text = "All cats are beautiful!";

        CompressedFile compressedFile = new Archiver().compress(text);
        String decompressResult = new Archiver().decompress(compressedFile);
         */

        File file = new File(PATH);
        FileReaderHuffman byteReader = new FileReaderHuffman(file);
        byteReader.readFile();
        System.out.println(Arrays.toString(byteReader.readFile()));

    }
}

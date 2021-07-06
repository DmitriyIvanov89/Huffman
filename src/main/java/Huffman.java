import files.FileHuffman;
import huffmancoding.Archiver;
import huffmancoding.CompressedFile;
import java.io.IOException;
import java.util.Arrays;

public class Huffman {

    private static final String PATH = ".\\src\\main\\resources\\test.txt";

    public static void main(String[] args) throws IOException {


//        String text = "All cats are beautiful!";
//
//        CompressedFile compressedFile = new Archiver().compress(text);
//        String decompressResult = new Archiver().decompress(compressedFile);

        FileHuffman byteReader = new FileHuffman(PATH);
        byteReader.readFile();
        System.out.println(Arrays.toString(byteReader.readFile()));

    }
}

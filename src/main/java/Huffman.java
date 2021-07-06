import huffmancoding.Archiver;
import huffmancoding.CompressedFile;
import java.io.IOException;

public class Huffman {

    private static final String PATH = ".\\src\\main\\resources\\test.txt";

    public static void main(String[] args) throws IOException {


        String text = "All cats are beautiful!";

        CompressedFile compressedFile = new Archiver().compress(text);
        String decompressResult = new Archiver().decompress(compressedFile);

    }
}

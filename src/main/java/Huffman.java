import huffmancoding.Archiver;
import huffmancoding.CompressedFile;

public class Huffman {
    public static void main(String[] args) {

        String text = "All cats are beautiful!";

        CompressedFile compressedFile = new Archiver().compress(text);
        String decompressResult = new Archiver().decompress(compressedFile);

    }
}

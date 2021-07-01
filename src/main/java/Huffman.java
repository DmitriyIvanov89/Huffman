import huffmancoding.Archiver;
import huffmancoding.CompressedFile;

public class Huffman {
    public static void main(String[] args) {

        String text = "All cats are beautiful!";


        CompressedFile compressedFile = Archiver.compress(text);
        String decompressResult = Archiver.decompress(compressedFile);


//        System.out.println("Generated codes table:");
//
//        for (Map.Entry<Character, String> entry : archiver.generateCodesTable().entrySet()) {
//            System.out.printf("symbol: %s, code: %s%n", entry.getKey(), entry.getValue());
//        }
//
//        System.out.printf("Coded text: %s" + "\n", archiver.compress());
//
//        System.out.printf("Decoded text: %s" + "\n", archiver.decompress(archiver.compress(), tree));

    }
}

import huffmancoding.HuffmanTree;

import java.util.HashMap;
import java.util.Map;

import java.util.Map;

public class Huffman {
    public static void main(String[] args) {

        String text = "abacabad";

<<<<<<< HEAD
        Frequency frequency = new Frequency(text);

=======
        HuffmanTree tree = new HuffmanTree(text);
        tree.buildHuffmanTree();
        Map<Character, String> codes = new HashMap<>();
        
>>>>>>> 8e1cd023e27c0415b14b60c65105a771a83348c9

    }
}

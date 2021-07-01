import huffmancoding.Coding;
import huffmancoding.HuffmanTree;

public class Huffman {
    public static void main(String[] args) {

        String text = "My name is Dmitriy";

        HuffmanTree tree = new HuffmanTree(text);
        tree.buildHuffmanTree();
        Coding coding = new Coding(text, tree);
        coding.generateCodesTable();
        System.out.println(coding.encodedText().toString());

    }
}

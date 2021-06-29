import huffmancoding.Frequency;

public class Huffman {
    public static void main(String[] args) {

        String text = "abacabad";

        Frequency frequency = new Frequency(text);
        System.out.println(frequency.countFrequency());

    }
}

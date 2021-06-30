import huffmancoding.Frequency;

import java.util.Map;

public class Huffman {
    public static void main(String[] args) {

        String text = "abacabad";

        Frequency frequency = new Frequency(text);

        for (Map.Entry<Character, Integer> entry : frequency.countFrequency().entrySet()) {
            System.out.println(String.format("symbol: %s, frequency: %s", entry.getKey(), entry.getValue()));
        }



    }
}

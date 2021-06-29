package huffmancoding;

import java.util.HashMap;
import java.util.Map;

public class Frequency {

    private final String text;

    public Frequency(String text) {
        this.text = text;
    }

    public Map<Character, Integer> countFrequency() {
        Map<Character, Integer> count = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);
            if (count.containsKey(symbol)) {
                count.put(symbol, count.get(symbol) + 1);
            } else {
                count.put(symbol, 1);
            }
        }
        return count;
    }
}

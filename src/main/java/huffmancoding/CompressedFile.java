package huffmancoding;

import java.util.Map;

public class CompressedFile {

    private final String encoded;
    private final Map<Character, Integer> frequencies;

    public CompressedFile(String encoded, Map<Character, Integer> frequencies) {
        this.encoded = encoded;
        this.frequencies = frequencies;
    }

    public String getEncoded() {
        return encoded;
    }

    public Map<Character, Integer> getFrequencies() {
        return frequencies;
    }
}

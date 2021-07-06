package files;

import java.io.*;

public class FileHuffman {

    private final String path;

    public FileHuffman(String path) {
        this.path = path;
    }

    public byte[] readFile() throws IOException {
        byte[] bytesArray = new byte[(int) new File(path).length()];

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(path), 4096)) {
            inputStream.read(bytesArray);
        }

        return bytesArray;
    }

    public void writeToFile() throws IOException {

    }

    public void loadCompressInfoFromFile() {

    }
}

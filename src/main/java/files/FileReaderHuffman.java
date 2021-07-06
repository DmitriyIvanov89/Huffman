package files;

import java.io.*;

public class FileReaderHuffman {

    private final String path;
    private final File file;

    public FileReaderHuffman(File file) {
        this.file = file;
        this.path = file.getPath();
    }

    public byte[] readFile() throws IOException {
        byte[] bytesArray = new byte[(int) file.length()];

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(path), 4096)) {
            inputStream.read(bytesArray);
        }

        return bytesArray;
    }
}

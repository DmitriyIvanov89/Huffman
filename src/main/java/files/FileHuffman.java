package files;

import java.io.*;
import java.util.Map;

public class FileHuffman {

    private String inputPath;

//    public FileHuffman(String path) {
//        this.path = path;
//    }

    public byte[] readFile() throws IOException {
        byte[] bytesArray = new byte[(int) new File(inputPath).length()];

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(inputPath), 4096)) {
            inputStream.read(bytesArray);
        }

        return bytesArray;
    }

    public void saveToFile(Map<Byte, String> frequencies, byte[] arr) throws IOException {
        String outputPath = ".\\src\\main\\resources\\";
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputPath))) {
            byte[] buffer = new byte[4096];
            outputStream.write(frequencies.size());
            for (Map.Entry<Byte, String> entry : frequencies.entrySet()) {
                
            }
        }
    }

    public void loadCompressInfoFromFile() {

    }
}

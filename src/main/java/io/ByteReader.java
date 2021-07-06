package io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteReader {

    private final String path;

    public ByteReader(String path) {
        this.path = path;
    }

    public void readToBytes() throws IOException {
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(path),4096)) {
            int i = -1;
            while ((i = inputStream.read()) != -1) {
                //System.out.print((char) i);
                System.out.print(i);
            }
        }
    }

}

package com.oscar.c.pozas.github.io;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;

public final class TryRandomAccessFile {

    public static void main(String[] args) {
        AccessFile accessFile = new AccessFile("");
    }

    static class AccessFile {

        private final String path;

        public AccessFile(String path) {
            this.path = path;
        }

        /**
         * Rewrite file deleting first line using pointers
         */
        public void removeFirstLine() throws IOException {
            RandomAccessFile raf = new RandomAccessFile(path, "rw");

            long writePointer = raf.getFilePointer();
            raf.readLine(); // Move pointer to second line
            long readPointer = raf.getFilePointer();

            byte[] buffer = new byte[1024]; // Create a 1MB buffer for bytes of file
            int bytesOnBuffer;

            while ((bytesOnBuffer = raf.read(buffer, 0, buffer.length)) != 1) {
                raf.seek(writePointer);
                raf.write(buffer, 0, bytesOnBuffer);

                writePointer += bytesOnBuffer;
                readPointer += bytesOnBuffer;

                raf.seek(readPointer);
            }

            raf.setLength(writePointer);
            raf.close();
        }

        /**
         * Decode Base64 string optimized
         */
        public void optimizeBase64Decoding(String base64, String resultPath) {
            try {
                InputStream in = new ByteArrayInputStream(base64.getBytes());
                FileOutputStream out = new FileOutputStream(resultPath);
                while (in.available() > 0) {
                    byte[] buffer = new byte[1024];
                    in.read(buffer);

                    byte[] decoded = Base64.decode(Arrays.toString(buffer));
                    out.write(decoded, 0, decoded.length);
                }
            } catch (FileNotFoundException e) {
                // Exception
            } catch (IOException e) {
                // Exception
            }
        }
    }
}

package com.flink.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CharToBMP {
    public static void main(String[] args) {
        int width = 600;
        int height = 600;
        char[] imageData = new char[width * height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                imageData[i * width + j] = (char) (i + j);
            }
        }
        String path = "D:\\down\\image2.bmp";
        File f=new File(path);
        try (FileOutputStream fos = new FileOutputStream(f)) {
            // Write the BMP header
            fos.write(new byte[]{'B', 'M'});
            fos.write(intToByteArray(54 + imageData.length * 2)); // file size
            fos.write(new byte[]{0, 0, 0, 0}); // reserved
            fos.write(intToByteArray(54)); // offset to image data

            // Write the DIB header
            fos.write(intToByteArray(40)); // header size
            fos.write(intToByteArray(width)); // image width
            fos.write(intToByteArray(height)); // image height
            fos.write(new byte[]{1, 0, 16, 0}); // number of color planes and bits per pixel
            fos.write(new byte[]{0, 0, 0, 0}); // compression method
            fos.write(intToByteArray(width * height * 2)); // image size
            fos.write(new byte[]{0, 0, 0, 0}); // horizontal resolution
            fos.write(new byte[]{0, 0, 0, 0}); // vertical resolution
            fos.write(intToByteArray(0)); // number of colors in the palette
            fos.write(intToByteArray(0)); // number of important colors

            // Write the image data
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    fos.write(charToByteArray(imageData[(height - i - 1) * width + j]));
                }
                for (int j = 0; j < (4 - (width * 2) % 4) % 4; j++) {
                    fos.write(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] intToByteArray(int value) {
        return new byte[]{
                (byte) (value & 0xff),
                (byte) ((value >> 8) & 0xff),
                (byte) ((value >> 16) & 0xff),
                (byte) ((value >> 24) & 0xff)
        };
    }

    private static byte[] charToByteArray(char value) {
        return new byte[]{
                (byte) (value & 0xff),
                (byte) ((value >> 8) & 0xff)
        };

    }

}

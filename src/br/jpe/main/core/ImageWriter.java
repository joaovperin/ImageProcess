/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jpe.main.core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Perin
 */
public class ImageWriter {

    public static void save(String filename, Image inputImg) throws IOException {
        File outputfile = new File(filename);

        // Delete if exists
        if (outputfile.exists()) {
            if (!outputfile.delete()) {
                throw new IOException("Couldn't delete file ".concat(filename));
            }
        }

        // Create a new file
        if (!outputfile.createNewFile()) {
            throw new IOException("Couldn't create file ".concat(filename));
        }

        BufferedImage image = new BufferedImage(inputImg.getWidth(), inputImg.getHeight(), BufferedImage.TYPE_INT_RGB);

        int iLen = inputImg.getWidth();
        int jLen = inputImg.getHeight();

        double[][][] mtz = inputImg.getMatrix();

        for (int i = 0; i < iLen; i++) {
            for (int j = 0; j < jLen; j++) {
                Color c = new Color(toPixel(mtz[i][j][0]), toPixel(mtz[i][j][1]), toPixel(mtz[i][j][2]));
                image.setRGB(i, j, c.getRGB());
            }
        }

        ImageIO.write(image, getExtension(outputfile), outputfile);
    }

    private static float toPixel(double rgb) {
        return ((float) rgb / 255);
    }

    private static String getExtension(File file) {
        String name = file.getName();
        return name.substring(name.lastIndexOf(".") + 1, name.length());
    }

}

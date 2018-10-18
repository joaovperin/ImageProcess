/*
 * Copyright (C) 2018 Perin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.jpe.main.core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A helper to write images on disk
 *
 * @author joaovperin
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

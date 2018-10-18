/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jpe.main;

import br.jpe.main.core.Image;
import br.jpe.main.core.ImageBuilder;
import br.jpe.main.core.ImageLoader;
import br.jpe.main.core.ImageProcessor;
import br.jpe.main.core.ImageWriter;
import br.jpe.main.core.scripts.ImageScript;
import br.jpe.main.core.scripts.PixelScript;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

/**
 * Main program entry point.
 *
 * It's just a sample for showing how to use the framework. It's not part of
 * ...the program ;D
 *
 * @author joaovperin
 */
public class Main {

    /**
     * Main entry point of the program
     *
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // Load an image from the disk
        final String imgName = "raposa.jpg";
        final Image original = ImageLoader.fromFile(new File("D:/Samples/" + imgName)).asOriginal();

        // An ImageScript
        ImageScript is1 = (double[][][] mtz) -> {
            ImageProcessor.process(mtz, (dest, c, i, j) -> {
                dest[i][j][0] = Math.min(c.getRed() + 87, 255);
                dest[i][j][1] = Math.min(c.getGreen() + 68, 255);
                dest[i][j][2] = Math.min(c.getBlue() + 32, 255);
            });
        };

        // A PixelScript
        PixelScript ps1 = (double[][][] mtz, Color c, int i, int j) -> {
            int contrast = 12;
            mtz[i][j][0] = Math.min(c.getRed() + contrast, 255);
            mtz[i][j][1] = Math.min(c.getGreen() + contrast, 255);
            mtz[i][j][2] = Math.min(c.getBlue() + contrast, 255);
        };

        // Another ImageScript
        ImageScript is2 = (double[][][] mtz) -> {
            ImageProcessor.process(mtz, (dest, c, i, j) -> {
                dest[i][j][0] = Math.max(c.getRed() + -27, 0);
                dest[i][j][1] = Math.min(c.getGreen() + 28, 255);
                dest[i][j][2] = Math.max((int) 177 + 0.113f * c.getBlue(), 255);
            });
        };

        // Another PixelScript
        PixelScript ps2 = (double[][][] mtz, Color c, int i, int j) -> {
            int contrast = -33;
            mtz[i][j][0] = Math.min(c.getRed() + contrast, 255);
            mtz[i][j][1] = Math.min(c.getGreen() + contrast, 255);
            mtz[i][j][2] = Math.min(c.getBlue() + contrast, 255);
        };

        // Apply various filters in sequence and build an image
        Image newImage = ImageBuilder.create(original).
                applyScript(is1).
                applyScript(ps1).
                applyScript(ps1).
                applyScript(is2).
                applyScript(ps2).
                build();

        // Write the built image on the disk
        ImageWriter.save("D:/Samples/results/prc_".concat(imgName), newImage);
    }

}

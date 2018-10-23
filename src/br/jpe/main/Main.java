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
import br.jpe.main.core.scripts.image.GeometricTransformScript;
import br.jpe.main.core.scripts.ImageScript;
import br.jpe.main.core.scripts.PixelScript;
import br.jpe.main.core.scripts.image.convolution.GaussianBlurFilterScript;
import br.jpe.main.core.scripts.image.convolution.MedianBlurFilterScript;
import br.jpe.main.core.scripts.image.convolution.ModeBlurFilterScript;
import br.jpe.main.core.scripts.image.convolution.RobertsBorderDetectionScript;
import br.jpe.main.core.scripts.image.geometric.RotationTransformScript;
import br.jpe.main.core.scripts.image.geometric.TranslationTransformScript;
import br.jpe.main.core.scripts.pixel.ThresholdPixelScript;
import java.awt.Color;
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
        borderDetectionExamples();
    }

    private static void chainedFiltersExample() throws IOException {
        // Load an image from the disk (resources folder)
        final String imgName = "fox.jpg";
        final Image original = ImageLoader.fromResources(imgName).asOriginal();

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
        ImageWriter.save(getOutputDirectory() + "prc_".concat(imgName), newImage);
    }

    private static void geometricTransformationExampleCustom() throws IOException {
        // Load an image from the disk
        final String imgName = "fox.jpg";
        final Image original = ImageLoader.fromResources(imgName).asOriginal();

        // Geometric transformation script
        ImageScript geom = new GeometricTransformScript() {
            @Override
            public double[][] getTransformMatrix(double[][][] mtz, int i, int j) {
                return new double[][]{
                    new double[]{1, 0, 0},
                    new double[]{0, -1, 0},
                    new double[]{0, 0, 1}
                };
            }
        };

        Image newImage = ImageBuilder.create(original).
                applyScript(new RotationTransformScript(30)).
                applyScript(new RotationTransformScript(30)).
                applyScript(new RotationTransformScript(60)).
                applyScript(new RotationTransformScript(30)).
                applyScript(new RotationTransformScript(30)).
                applyScript(geom).
                build();
        ImageWriter.save(getOutputDirectory() + "/prc_geom_".concat(imgName), newImage);
    }

    private static void geometricTransformationExampleTranslate() throws IOException {
        // Load an image from the disk
        final String imgName = "coffin.png";
        final Image original = ImageLoader.fromResources("images/" + imgName).asOriginal();

        Image newImage = ImageBuilder.create(original).
                applyScript(new RotationTransformScript(-30)).
                applyScript(new TranslationTransformScript(40, 80, 0)).
                build();
        ImageWriter.save(getOutputDirectory() + "prc_geom_".concat(imgName), newImage);
    }

    private static void convolutionMasksExample() throws IOException {
        // Load an image from the disk
        final String imgName = "lena.png";
        final Image original = ImageLoader.fromResources("images/" + imgName).asAverageGreyscale();

        Image newImage = ImageBuilder.create(original).
                applyScript(12, new GaussianBlurFilterScript()).
                applyScript(new ModeBlurFilterScript(), new MedianBlurFilterScript()).
                applyScript(new ThresholdPixelScript(120)).
                build();
        ImageWriter.save(getOutputDirectory() + "prc_convx_".concat(imgName), newImage);
    }

    private static void borderDetectionExamples() throws IOException {
        final String imgName = "feevale_logo.jpg";
        final Image original = ImageLoader.fromResources("images/" + imgName).asAverageGreyscale();

        Image newImage = ImageBuilder.create(original).
                applyScript(new RobertsBorderDetectionScript(12)).
                //                applyScript(new ThresholdPixelScript(120)).
                build();
        ImageWriter.save(getOutputDirectory() + "prc_roberts_".concat(imgName), newImage);

        // Load an image from the disk
        final String coinsImgName = "coins.jpg";
        final Image coinsOriginal = ImageLoader.fromResources("images/" + coinsImgName).asAverageGreyscale();

        Image coinsNewImage = ImageBuilder.create(coinsOriginal).
                applyScript(6, new GaussianBlurFilterScript()).
                applyScript(new RobertsBorderDetectionScript(2)).
                build();
        ImageWriter.save(getOutputDirectory() + "prc_roberts_".concat(coinsImgName), coinsNewImage);
    }

    private static String getOutputDirectory() {
        String javaHome = System.getProperty("java.home");
        if (javaHome != null && javaHome.contains("C:\\RECH")) {
            return "T:/TMP/perinfeevale/img/";
        }
        return "D:/Samples/results/";
    }
}

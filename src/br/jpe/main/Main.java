/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jpe.main;

import br.jpe.main.core.Image;
import br.jpe.main.core.ImageBuilder;
import br.jpe.main.core.ImageColor;
import br.jpe.main.core.ImageInfo;
import static br.jpe.main.core.ImageInfoConstants.PIXEL_COUNT;
import br.jpe.main.core.ImageInfoExtractor;
import br.jpe.main.core.ImageLoader;
import br.jpe.main.core.ImagePoint;
import br.jpe.main.core.ImageProcessor;
import br.jpe.main.core.ImageSlicer;
import br.jpe.main.core.ImageWriter;
import br.jpe.main.core.scripts.image.GeometricTransformScript;
import br.jpe.main.core.scripts.ImageScript;
import br.jpe.main.core.scripts.PixelScript;
import br.jpe.main.core.scripts.image.BinaryLabelingScript;
import br.jpe.main.core.scripts.image.PaintAllScript;
import br.jpe.main.core.scripts.image.convolution.DilationMorphScript;
import br.jpe.main.core.scripts.image.convolution.ErosionMorphScript;
import br.jpe.main.core.scripts.image.convolution.GaussianBlurFilterScript;
import br.jpe.main.core.scripts.image.convolution.MarrAndHildrethBorderDetectionScript;
import br.jpe.main.core.scripts.image.convolution.MedianBlurFilterScript;
import br.jpe.main.core.scripts.image.convolution.ModeBlurFilterScript;
import br.jpe.main.core.scripts.image.convolution.RobertsBorderDetectionScript;
import br.jpe.main.core.scripts.image.convolution.RobinsonBorderDetectionScript;
import br.jpe.main.core.scripts.image.convolution.SobelBorderDetectionScript;
import br.jpe.main.core.scripts.image.extraction.PixelCountExtractionScript;
import br.jpe.main.core.scripts.image.geometric.RotationTransformScript;
import br.jpe.main.core.scripts.image.geometric.TranslationTransformScript;
import br.jpe.main.core.scripts.image.skeletonization.HoltSkeletonizationScript;
import br.jpe.main.core.scripts.image.skeletonization.StentifordSkeletonizationScript;
import br.jpe.main.core.scripts.pixel.GreyscaleThresholdPixelScript;
import br.jpe.main.core.scripts.pixel.InvertColorPixelScript;
import br.jpe.main.core.scripts.pixel.ThresholdPixelScript;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        binaryLabelingWithLeaves();
//        Runtime.getRuntime().exec("explorer ".concat(getOutputDirectory()));
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
        PixelScript ps1 = (double[][][] mtz, ImageColor c, int i, int j) -> {
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
        PixelScript ps2 = (double[][][] mtz, ImageColor c, int i, int j) -> {
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
                return new double[][] {
                    new double[] { 1, 0, 0 },
                    new double[] { 0, -1, 0 },
                    new double[] { 0, 0, 1 }
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

        // Load an image from the disk
        final String photographerImgName = "photographer.png";
        final Image photographerOriginal = ImageLoader.fromResources("images/" + photographerImgName).
                asAverageGreyscale();

        Image photographerNewImage = ImageBuilder.create(photographerOriginal).
                applyScript(6, new GaussianBlurFilterScript()).
                applyScript(new RobertsBorderDetectionScript(20)).
                build();
        ImageWriter.save(getOutputDirectory() + "prc_roberts_".concat(photographerImgName), photographerNewImage);

        // Load an image from the disk
        final String sobelPhotographerImgName = "photographer.png";
        final Image sobelPhotographerOriginal = ImageLoader.fromResources("images/" + sobelPhotographerImgName).
                asAverageGreyscale();

        Image sobelPhotographerNewImage = ImageBuilder.create(sobelPhotographerOriginal).
                applyScript(6, new GaussianBlurFilterScript()).
                applyScript(new SobelBorderDetectionScript(69)).
                build();
        ImageWriter.
                save(getOutputDirectory() + "prc_sobel_".concat(sobelPhotographerImgName), sobelPhotographerNewImage);
    }

    private static void testExamples() throws IOException {
        // Load an image from the disk
        final String claudiomiroImgName = "claudiomiro.png";
        final Image claudiomiroOriginal = ImageLoader.fromResources("images/" + claudiomiroImgName).
                asAverageGreyscale();

        Image claudiomiroMedianaNewImage = ImageBuilder.create(claudiomiroOriginal).
                applyScript(new GaussianBlurFilterScript()).
                build();
        ImageWriter.
                save(getOutputDirectory() + "prc_claudiomiro_gauss_".concat(claudiomiroImgName), claudiomiroMedianaNewImage);

        Image sobelPhotographerNewImage = ImageBuilder.create(claudiomiroOriginal).
                applyScript(new MedianBlurFilterScript()).
                build();
        ImageWriter.
                save(getOutputDirectory() + "prc_claudiomiro_mediana_".concat(claudiomiroImgName), sobelPhotographerNewImage);
    }

    private static void otherBorderDetectionExample() throws IOException {
        // Load an image from the disk
        final String coinsImgName = "lena.png";
        final Image coinsOriginal = ImageLoader.fromResources("images/" + coinsImgName).asAverageGreyscale();

        Image coinsNewImage = ImageBuilder.create(coinsOriginal).
                //                applyScript(new KirschBorderDetectionScript(255)).
                applyScript(new GaussianBlurFilterScript()).
                applyScript(new GaussianBlurFilterScript()).
                //                applyScript(new RobinsonBorderDetectionScript(255)).
                applyScript(new RobinsonBorderDetectionScript(120)).
                //                applyScript(new RobinsonBorderDetectionScript(120)).
                //                applyScript(new SobelBorderDetectionScript(120)).
                build();
        ImageWriter.save(getOutputDirectory() + "prc_lenarobin_".concat(coinsImgName), coinsNewImage);
    }

    private static void comparativeBorderDetectionExample() throws IOException {
        // Load an image from the disk
        final String houseImgName = "house.png";
        final Image coinsOriginal = ImageLoader.fromResources("images/" + houseImgName).asAverageGreyscale();

        Image houseNewImage = ImageBuilder.create(coinsOriginal).
                applyScript(new MarrAndHildrethBorderDetectionScript(150)).
                build();
        ImageWriter.save(getOutputDirectory() + "prc_house_mar_".concat(houseImgName), houseNewImage);
    }

    private static void erosionDilationFilterExample() throws IOException {
        final String imgName = "test_img.png";
        final Image imgOriginal = ImageLoader.fromResources("images/" + imgName).asAverageGreyscale();

        Image newImage = ImageBuilder.create(imgOriginal).
                applyScript(2, new ErosionMorphScript()).
                applyScript(new SobelBorderDetectionScript(180)).
                applyScript(2, new DilationMorphScript()).
                build();
        ImageWriter.save(getOutputDirectory() + "prc_dilation".concat(imgName), newImage);
    }

    private static void skeletonizationTransformExample() throws IOException {
        final String imgName = "x_pic.png";
        final Image imgOriginal = ImageLoader.fromResources("images/" + imgName).asAverageGreyscale();

        Image newImage = ImageBuilder.create(imgOriginal).
                applyScript(new ThresholdPixelScript(100)).
                applyScript(new StentifordSkeletonizationScript()).
                build();

        Image newImage2 = ImageBuilder.create(imgOriginal).
                applyScript(new ThresholdPixelScript(150)).
                applyScript(new InvertColorPixelScript()).
                applyScript(new HoltSkeletonizationScript()).
                build();
        ImageWriter.save(getOutputDirectory() + "prc_skeleton_x_".concat(imgName), newImage);
        ImageWriter.save(getOutputDirectory() + "prc_skeleton_x2_".concat(imgName), newImage2);
    }

    private static void sandbox() throws IOException {
        final String imgName = "forms_sample.png";
        final Image imgOriginal = ImageLoader.fromResources("images/" + imgName).asOriginal();

        Image newImage = ImageBuilder.create(
                ImageSlicer.create(imgOriginal).slice(new ImagePoint(35, 45), 250, 160)
        ).
                //                applyScript(3, new DilationMorphScript()).
                //                applyScript(3, new ErosionMorphScript()).
                applyScript(new GreyscaleThresholdPixelScript(120)).
                applyScript(new HoltSkeletonizationScript()).
                applyScript(new PaintAllScript(ImageColor.black(), ImageColor.green())).
                applyScript(new PaintAllScript(ImageColor.white(), ImageColor.black())).
                applyScript(new PaintAllScript(ImageColor.green(), ImageColor.white())).
                build();

        ImageWriter.save(getOutputDirectory() + "prc_sandbox_forms_p_".concat(imgName), newImage);

        ImageInfo info = ImageInfoExtractor.create(newImage.getMatrix()).
                applyScript(new PixelCountExtractionScript(ImageColor.white(), "P_RIMETER")).
                //                applyScript(new PerimeterFloodfillExtractionScript(new ImagePoint(99, 67), "P_RIN")).
                extract();
        ImageWriter.save(getOutputDirectory() + "prc_sandbox_forms_px_".concat(imgName), newImage);

        int perimiter = info.getInt("P_RIMETER");
//        int perin = info.getInt("P_RIN");
        System.out.println("***P Count: " + perimiter);
//        System.out.println("***P rin: " + perin);
//        ImageInfo info = ImageInfoExtractor.create(newImage.getMatrix()).
//                applyScript(new PixelCountExtractionScript(ImageColor.red(), "P_RED")).
//                applyScript(new PixelCountExtractionScript(ImageColor.black(), "P_BLACK")).
//                applyScript(new AreaExtractionScript(new ImagePoint(160, 130), PIXEL_AREA)).
//                extract();
//
//        System.out.println("***Black Count: " + info.get("P_BLACK"));
//        System.out.println("***AREA: " + areaCount);
    }

    private static void binaryLabeling() throws IOException {
        final String imgName = "forms_sample.png";
        final Image imgOriginal = ImageLoader.fromResources("images/" + imgName).asAverageGreyscale();

        BinaryLabelingScript binaryLabeler = new BinaryLabelingScript(ImageColor.black(), true);
        Image newImage = ImageBuilder.create(imgOriginal).
                //                applyScript(new InvertColorPixelScript()).
                //                applyScript(new GreyscaleThresholdPixelScript(95)).
                //                applyScript(new InvertColorPixelScript()).
                //                applyScript(new HoltSkeletonizationScript()).
                applyScript(binaryLabeler).
                build();

        ImageWriter.save(getOutputDirectory() + "prc_binaryLabeling_".concat(imgName), newImage);

        Map<ImageColor, ImagePoint> colors = binaryLabeler.getColors();
        colors.forEach((k, v) -> {
            System.out.println(v + "=" + k);
        });

    }

    private static boolean isBetween(int pValue, int target, int tolerance) {
        int tValue = (int) ((double) target * (double) tolerance) / 100;
        int min = target - tValue;
        int max = target + tValue;
        return (pValue >= min && pValue <= max);
    }

    private static void binaryLabelingWithLeaves() throws IOException {

        Arrays.asList("leaf_1.png", "leaf_2.png", "leaf_3.png", "leaf_4.png").forEach(imgName -> {

            System.out.println("****** PROCESSING: " + imgName);

            Image imgOriginal = null;
            try {
                imgOriginal = ImageLoader.fromResources("images/" + imgName).asOriginal();
            } catch (IOException ex) {
            }

            final int MIN_AREA = 400;

            BinaryLabelingScript binaryLabeler = new BinaryLabelingScript(ImageColor.black(), MIN_AREA, true);
            Image newImage = ImageBuilder.create(imgOriginal).
                    applyScript((mtz, c, i, j) -> {
                        int r = c.getRed();
                        int g = c.getGreen();
                        int b = c.getBlue();

                        int v = 0; // defaults black
//                    if (isBetween(r, 229, 6) && isBetween(g, 210, 8) && isBetween(b, 70, 18)) { // Leaf Green (body)
//                        v = 255;
//                    }
//                    if (isBetween(r, 133, 15) && isBetween(g, 117, 15) && isBetween(b, 95, 15)) { // LightBrown (body)
//                        v = 255;
//                    }
//                    if (isBetween(r, 100, 35) && isBetween(g, 55, 25) && isBetween(b, 40, 25)) { // DarkBrown (body) - NOT GOOD YET
//                        v = 255;
//                    }
//                    if (isBetween(r, 208, 70) && isBetween(g, 211, 50) && isBetween(b, 70, 50)) { // Leaf Green (body)
//                        v = 0;
//                    }
                        if (isBetween(r, 114, 72) && isBetween(g, 36, 43) && isBetween(b, 32, 45) || // Wine
                                isBetween(r, 216, 71) && isBetween(g, 109, 43) && isBetween(b, 93, 45)) { // Copper red
                            v = 255;
                        } else {
//                        if (isBetween(r, 150, 70) && isBetween(g, 155, 60) && isBetween(b, 58, 35)) { // Dark Green (veins)
//                            v = 0;
//                        }
                        }
                        if (isBetween(r, 142, 30) && isBetween(g, 132, 22) && isBetween(b, 96, 22)) { // LightBrown
                            v = 0;
                        }
                        if (isBetween(r, 115, 30) && isBetween(g, 89, 22) && isBetween(b, 76, 22)) { // DarkBrown
                            v = 0;
                        }
//
//                    final int tB = 100;
//                    final int min = 15;
//                    if (isBetween(r, min, tB) || isBetween(g, min, tB) || isBetween(b, min, tB)) { // Gray
//                        v = 0;
//                    }

                        for (int k = 0; k < 3; k++) {
                            mtz[i][j][k] = v;
                        }
                    }).
                    applyScript(binaryLabeler).
                    build();

            try {
                ImageWriter.save(getOutputDirectory() + "a_prc_leaves_".concat(imgName), newImage);
            } catch (IOException ex) {
            }

            Map<ImageColor, ImagePoint> colors = binaryLabeler.getColors();
            colors.forEach((ImageColor k, ImagePoint v) -> {
                final int pCnt = ImageInfoExtractor.create(newImage.getMatrix()).
                        applyScript(new PixelCountExtractionScript(k)).
                        extract().getInt(PIXEL_COUNT);

                System.out.println(v + "=" + k + " --> " + pCnt);
            });
            System.out.println("****** ...................... ");
        });
    }

    private static String getOutputDirectory() {
        String javaHome = System.getProperty("java.home");
        if (javaHome != null && javaHome.contains("C:\\RECH")) {
            return "T:\\TMP\\perinfeevale\\img\\";
        }
        return "D:\\Samples\\results\\";
    }
}

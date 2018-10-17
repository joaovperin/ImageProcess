/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jpe.main.core;

import br.jpe.main.core.scripts.ColoredProcessScript;
import br.jpe.main.core.scripts.DecompositionProcessScript;
import br.jpe.main.core.scripts.DesaturationProcessScript;
import br.jpe.main.core.scripts.GrayscaleProcessScript;
import br.jpe.main.core.scripts.SingleColorProcessScript;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 *
 * @author Perin
 */
public class ImageFactory {

    // http://www.tannerhelland.com/3643/grayscale-image-algorithm-vb6/
    private final BufferedImage read;

    public ImageFactory(BufferedImage read) {
        this.read = read;
    }

    public Image asOriginal() {
        return runScript(read, new ColoredProcessScript());
    }

    public Image asAverageGreyscale() {
        return runScript(read, new GrayscaleProcessScript(1, 1, 1));
    }

    public Image asLumaGreyscale() {
        return runScript(read, new GrayscaleProcessScript(0.3, 0.59, 0.11));
    }

    public Image asLumaGreyscale2() {
        return runScript(read, new GrayscaleProcessScript(0.2126, 0.7152, 0.0722));
    }

    public Image asLumaGreyscale3() {
        return runScript(read, new GrayscaleProcessScript(0.299, 0.587, 0.114));
    }

    public Image asDesaturationGreyscale() {
        return runScript(read, new DesaturationProcessScript());
    }

    public Image asMaxDecompositionGreyscale() {
        return runScript(read, DecompositionProcessScript.max());
    }

    public Image asMinDecompositionGreyscale() {
        return runScript(read, DecompositionProcessScript.min());
    }

    public Image asRedSingleColorGreyscale() {
        return runScript(read, SingleColorProcessScript.red());
    }

    public Image asGreenSingleColorGreyscale() {
        return runScript(read, SingleColorProcessScript.green());
    }

    public Image asBlueSingleColorGreyscale() {
        return runScript(read, SingleColorProcessScript.blue());
    }

    private static Image runScript(BufferedImage read, PixelScript script) {
        WritableRaster raster = read.getRaster();

        int iLen = raster.getWidth();
        int jLen = raster.getHeight();
        int cLen = raster.getNumBands();

        double[][][] mtz = new double[iLen][jLen][cLen];
        for (int i = 0; i < iLen; i++) {
            for (int j = 0; j < jLen; j++) {
                script.run(mtz, read, i, j);
            }
        }
        return new Image(mtz);
    }

}

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

import br.jpe.main.core.load.scripts.ColoredProcessScript;
import br.jpe.main.core.load.scripts.DecompositionProcessScript;
import br.jpe.main.core.load.scripts.DesaturationProcessScript;
import br.jpe.main.core.load.scripts.GrayscaleProcessScript;
import br.jpe.main.core.load.scripts.SingleColorProcessScript;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import br.jpe.main.core.load.PixelLoadScript;

/**
 * A Factory for Image objects
 * Reference: http://www.tannerhelland.com/3643/grayscale-image-algorithm-vb6/
 *
 * @author joaovperin
 */
public class ImageFactory {

    private static final int DEFAULT_NUM_CHANNELS = 3;

    private final BufferedImage read;

    public ImageFactory(BufferedImage read) {
        this.read = read;
    }

    public Image empty(int width, int height) {
        return new Image(new double[width][height][DEFAULT_NUM_CHANNELS]);
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

    private static Image runScript(BufferedImage read, PixelLoadScript script) {
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

/*
 * Copyright (C) 2018 joaovperin
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
package br.jpe.main.core.scripts.image.convolution;

import br.jpe.main.core.scripts.image.ConvolutionTransformScript;

/**
 * A Border detection algorithm as described by Sobel
 *
 * // UNDONE YET
 * // UNDONE YET
 * // UNDONE YET
 * // UNDONE YET
 *
 *
 * @author joaovperin
 */
public class SobelBorderDetectionScript extends ConvolutionTransformScript {

    private final int thresholdValue;

    private double gX;
    private double gY;

    public SobelBorderDetectionScript(int thresholdValue) {
        super(new double[][] {
            new double[] { -1, 0, 1 },
            new double[] { -2, 0, 2 },
            new double[] { -1, 0, 1 }
        }, new double[][] {
            new double[] { 1, 2, 1 },
            new double[] { 0, 0, 0 },
            new double[] { -1, -2, -1 }
        });
        this.thresholdValue = thresholdValue;
    }

    @Override
    protected void forEachColorStart(double[][][] src, int i, int j, int c) {
        gX = 0;
        gY = 0;
    }

    @Override
    protected void forEachConvolutedPixel(double[][][] mtz, int i, int j, int c, int kI, int kJ) {
        double pixelValue = mtz[i + kI - 1][j + kJ - 1][c];
        gX += pixelValue * kernel[0][kI][kJ];
        gY += pixelValue * kernel[1][kI][kJ];
    }

    @Override
    protected void forEachColorEnd(double[][][] mtz, int i, int j, int c) {
        double pixelValue = Math.sqrt(Math.pow(gX, 2) + Math.pow(gY, 2));
        if (pixelValue > thresholdValue) {
            mtz[i][j][c] = 255;
        } else {
            mtz[i][j][c] = 0;
        }
    }

}

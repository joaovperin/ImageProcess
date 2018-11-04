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
package br.jpe.main.core.scripts.image;

import br.jpe.main.core.ImageUtils;
import br.jpe.main.core.scripts.ImageScript;

/**
 * An script able to make convolutions
 *
 * @author joaovperin
 */
public abstract class ConvolutionTransformScript implements ImageScript {

    protected final double[][][] kernel;

    protected final int kiLen;
    protected final int kjLen;
    protected final int kernelSize;

    public ConvolutionTransformScript(double[][]... kernel) {
        this.kernel = kernel;
        this.kiLen = kernel[0].length;
        this.kjLen = kernel[0][0].length;
        this.kernelSize = kiLen * kjLen;
    }

    @Override
    public final void run(double[][][] src) {
        double[][][] mtz = ImageUtils.copy(src);

        int iLen = mtz.length;
        int jLen = mtz[0].length;
        int cLen = mtz[0][0].length;

        // Matrix loop
        for (int i = 1; i < iLen; i++) {
            for (int j = 1; j < jLen; j++) {
                // Color loop
                for (int c = 0; c < cLen; c++) {
                    // ForEachColor callback
                    forEachColorStart(mtz, i, j, c);
                    // Kernel loop
                    for (int kI = 0; kI < kiLen && (i + kI) < iLen; kI++) {
                        for (int kJ = 0; kJ < kjLen && (j + kJ) < jLen; kJ++) {
                            forEachConvolutedPixel(mtz, i, j, c, kI, kJ);
                        }
                    }
                    forEachColorEnd(src, i, j, c);
                }
            }
        }
    }

    protected abstract void forEachColorStart(double[][][] mtz, int i, int j, int c);

    protected abstract void forEachConvolutedPixel(double[][][] mtz, int i, int j, int c, int kI, int kJ);

    protected abstract void forEachColorEnd(double[][][] mtz, int i, int j, int c);

}

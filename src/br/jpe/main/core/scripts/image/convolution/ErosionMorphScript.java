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
 * Apply an erosion morph script
 *
 * @author joaovperin
 */
public class ErosionMorphScript extends ConvolutionTransformScript {

    private double[][] neighbours = new double[3][3];

    public ErosionMorphScript() {
        this(new double[][] {
            new double[] { 0, 1, 0 },
            new double[] { 1, 1, 1 },
            new double[] { 0, 1, 0 }
        });
    }

    public ErosionMorphScript(double[][] dilationShape) {
        super(dilationShape);
    }

    @Override
    protected void forEachColorStart(double[][][] src, int i, int j, int c) {
        neighbours = new double[3][3];
    }

    @Override
    protected void forEachConvolutedPixel(double[][][] mtz, int i, int j, int c, int kI, int kJ) {
        neighbours[kI][kJ] = mtz[i + kI - 1][j + kJ - 1][c];
    }

    @Override
    protected void forEachColorEnd(double[][][] mtz, int i, int j, int c) {
        double largest = 0;
        for (double[] neighbour : neighbours) {
            for (int y = 0; y < neighbour.length; y++) {
                if (neighbour[y] > largest) {
                    largest = neighbour[y];
                }
            }
        }
        mtz[i][j][c] = largest;
    }

}

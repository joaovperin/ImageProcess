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
 * An script able to make skeletonizations or edge thinnings
 *
 * @author joaovperin
 */
public abstract class SkeletonizationTransformScript implements ImageScript {

    protected static final int STEP_1 = 1;
    protected static final int STEP_2 = 2;
    protected static final int STEP_3 = 3;
    protected static final int STEP_4 = 4;

    protected boolean change;
    protected int step;

    protected abstract double calc(double[][] pixels, int step);

    protected abstract void checkStep();

    @Override
    public final void run(double[][][] src) {
        double[][][] mtz = ImageUtils.copy(src);

        int iLen = mtz.length;
        int jLen = mtz[0].length;
        int cLen = mtz[0][0].length;

        step = 0;
        change = true;

        while (change) {
            change = false;
            step++;
            // Matrix and Color loop
            for (int i = 1; i < iLen - 1; i++) {
                for (int j = 1; j < jLen - 1; j++) {
                    for (int c = 0; c < cLen; c++) {
                        // Check if any modification
                        if (isHigher(mtz[i][j][0])) {
                            double[][] pixels = pixels(mtz, i, j, 0);
                            double v = Math.max(Math.min(calc(pixels, step), 255), 0);
                            if (v != mtz[i][j][0]) {
                                change = true;
                            }
                            src[i][j][c] = v;
                        }
                    }
                }
            }
            // The final step
            mtz = ImageUtils.copy(src);
            checkStep();
        }
    }

    /**
     * Returns neighborhood
     *
     * @param pixels
     * @return int[]
     */
    protected static double[] getNeighborhood(double[][] pixels) {
        final double higherValue = 255;
        double p2 = pixels[1][0] / higherValue;
        double p3 = pixels[2][0] / higherValue;
        double p4 = pixels[2][1] / higherValue;
        double p5 = pixels[2][2] / higherValue;
        double p6 = pixels[1][2] / higherValue;
        double p7 = pixels[0][2] / higherValue;
        double p8 = pixels[0][1] / higherValue;
        double p9 = pixels[0][0] / higherValue;
        return new double[] { p2, p3, p4, p5, p6, p7, p8, p9 };
    }

    /**
     * Returns true if it is an edge
     *
     * @param neighborhood
     * @return boolean
     */
    protected static boolean isEdge(double[] neighborhood) {
        double np = neighborhood[0] + neighborhood[1] + neighborhood[2]
                + neighborhood[3] + neighborhood[4] + neighborhood[5]
                + neighborhood[6] + neighborhood[7];
        return (np >= 2 && np <= 6) && isConnected(neighborhood);
    }

    /**
     * Returns true if the neighborhood is connected
     *
     * @param neighborhood
     * @return boolean
     */
    protected static boolean isConnected(double[] neighborhood) {
        int sp = (neighborhood[0] < neighborhood[1] ? 1 : 0)
                + (neighborhood[1] < neighborhood[2] ? 1 : 0)
                + (neighborhood[2] < neighborhood[3] ? 1 : 0)
                + (neighborhood[3] < neighborhood[4] ? 1 : 0)
                + (neighborhood[4] < neighborhood[5] ? 1 : 0)
                + (neighborhood[5] < neighborhood[6] ? 1 : 0)
                + (neighborhood[6] < neighborhood[7] ? 1 : 0)
                + (neighborhood[7] < neighborhood[0] ? 1 : 0);
        return sp == 1;
    }

    /**
     * Returns the pixel matrix
     *
     * @param mtz
     * @param i
     * @param j
     * @param c
     * @return {@code int[][]}
     */
    protected static double[][] pixels(double[][][] mtz, int i, int j, int c) {
        double[][] pixels = new double[3][3];
        for (int x2 = 0; x2 < 3; x2++) {
            for (int y2 = 0; y2 < 3; y2++) {
                pixels[x2][y2] = mtz[i + x2 - 1][j + y2 - 1][0];
            }
        }
        return pixels;
    }

    protected static boolean isHigher(double n) {
        return n == 255;
    }

}

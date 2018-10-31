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
package br.jpe.main.core.scripts.image.skeletonization;

import br.jpe.main.core.ImageUtils;
import br.jpe.main.core.scripts.image.SkeletonizationTransformScript;

/**
 * Apply a dilation morph script
 *
 * @author joaovperin
 */
public class StentifordSkeletonizationScript extends SkeletonizationTransformScript {

    private final int STEP_1 = 1;
    private final int STEP_2 = 2;
    private final int STEP_3 = 3;
    private final int STEP_4 = 4;

    @Override
    public void run(double[][][] src) {
        double[][][] mtz = ImageUtils.copy(src);

        int iLen = mtz.length;
        int jLen = mtz[0].length;
        int cLen = mtz[0][0].length;

        boolean change = true;
        int step = 0;

        while (change) {
            change = false;
            step++;
            // Matrix and Color loop
            for (int i = 1; i < iLen - 1; i++) {
                for (int j = 1; j < jLen - 1; j++) {
//                    for (int c = 0; c < cLen; c++) {
                    // Checks...
                    if (isHigher(mtz[i][j][0])) {
                        double[][] pixels = pixels(mtz, i, j, 0);
                        double v = Math.max(Math.min(calc(pixels, step), 255), 0);
                        if (v != mtz[i][j][0]) {
                            change = true;
                        }
                        for (int c = 0; c < cLen; c++) {
                            src[i][j][c] = v;
                        }
                    }
//                    }
                }
            }
            // The final step
            mtz = ImageUtils.copy(src);
            if (step == 4) {
                step = 0;
            }
        }

    }

    /**
     * calculate the pixel value
     *
     * @param pixels
     * @param step
     * @return int
     */
    @Override
    protected final double calc(double[][] pixels, int step) {
        double[] neighborhood = getNeighborhood(pixels);
        if (!isConnected(neighborhood)) {
            return pixels[1][1];
        }
        double n = pixels[1][0];
        double l = pixels[2][1];
        double s = pixels[1][2];
        double o = pixels[0][1];
        double no = pixels[0][0];
        if (step == STEP_1) {
            if (!(!isHigher(n) && isHigher(s))) {
                return pixels[1][1];
            }
        }
        if (step == STEP_2) {
            if (!(!isHigher(no) && isHigher(l))) {
                return pixels[1][1];
            }
        }
        if (step == STEP_3) {
            if (!(!isHigher(s) & isHigher(n))) {
                return pixels[1][1];
            }
        }
        if (step == STEP_4) {
            if (!(!isHigher(l) && isHigher(o))) {
                return pixels[1][1];
            }
        }
        return 0;
    }

}

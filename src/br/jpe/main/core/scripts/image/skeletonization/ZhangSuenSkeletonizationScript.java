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

import br.jpe.main.core.scripts.image.SkeletonizationTransformScript;

/**
 * Apply a skeletonization script using ZhangSuen's technique
 *
 * @author joaovperin
 */
public class ZhangSuenSkeletonizationScript extends SkeletonizationTransformScript {

    /**
     * Calculate the pixel value
     *
     * @param pixels
     * @param step
     * @return double
     */
    @Override
    protected final double calcValue(double[][] pixels, int step) {
        double[] n = getNeighborhood(pixels);
        if (isEdge(n)) {
            if (step == STEP_1) {
                if ((n[0] * n[2] * n[4] == 0) && (n[2] * n[4] * n[6] == 0)) {
                    return 0;
                }
            } else {
                if ((n[4] * n[0] * n[2] == 0) && (n[2] * n[4] * n[6] == 0)) {
                    return 0;
                }
            }
        }
        return pixels[1][1];
    }

    @Override
    protected final void checkStep() {
        if (step == STEP_2) {
            step = 0;
        }
    }
}

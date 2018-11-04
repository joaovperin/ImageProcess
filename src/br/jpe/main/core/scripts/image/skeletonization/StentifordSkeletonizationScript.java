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
 * Apply a skeletonization script using the Stentiford technique
 *
 * @author joaovperin
 */
public class StentifordSkeletonizationScript extends SkeletonizationTransformScript {

    /**
     * calculate the pixel value
     *
     * @param pixels
     * @param step
     * @return int
     */
    @Override
    protected final double calcValue(double[][] pixels, int step) {
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

    @Override
    protected final void checkStep() {
        if (step == STEP_4) {
            step = 0;
        }
    }

}

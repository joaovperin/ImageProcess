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
package br.jpe.main.core.scripts.pixel;

import br.jpe.main.core.ImageColor;
import br.jpe.main.core.scripts.PixelScript;

/**
 * A threshold pixel script that turns images into grayscale
 *
 * @author joaovperin
 */
public class GreyscaleThresholdPixelScript implements PixelScript {

    private final int thresholdValue;

    public GreyscaleThresholdPixelScript(int thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    @Override
    public void run(double[][][] mtz, ImageColor color, int i, int j) {
        double newPixelValue = (color.getRed() + color.getGreen() + color.getBlue() / 3);
        int v = applyThreshold(newPixelValue, thresholdValue);
        for (int c = 0; c < mtz[i][j].length; c++) {
            mtz[i][j][c] = v;
        }
    }

    private static int applyThreshold(double p, int value) {
        return p > value ? 255 : 0;
    }

}

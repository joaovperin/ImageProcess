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

import java.awt.Color;
import br.jpe.main.core.scripts.PixelScript;

/**
 * A threshold pixel script, or the populary 'if' in Digital Image Processing :P
 *
 * // UNTESTED
 * // UNTESTED
 * // UNTESTED
 *
 * @author joaovperin
 */
public class ThresholdPixelScript implements PixelScript {

    private final int thresholdValue;

    public ThresholdPixelScript(int thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    @Override
    public void run(double[][][] mtz, Color color, int i, int j) {
        mtz[i][j][0] = applyThreshold(color.getRed(), thresholdValue);
        mtz[i][j][1] = applyThreshold(color.getGreen(), thresholdValue);
        mtz[i][j][2] = applyThreshold(color.getBlue(), thresholdValue);
    }

    private static int applyThreshold(int p, int value) {
        return p > value ? 255 : 0;
    }

}

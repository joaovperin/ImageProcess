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
 * A Greyscale pixel script using Desaturation method
 *
 * @author joaovperin
 */
public class GreyscaleDesaturationPixelScript implements PixelScript {

    @Override
    public void run(double[][][] mtz, ImageColor color, int i, int j) {

        int max = Math.max(color.getRed(), Math.max(color.getGreen(), color.getBlue()));
        int min = Math.min(color.getRed(), Math.min(color.getGreen(), color.getBlue()));
        int median = (int) ((max + min) / 2);

        int numBands = mtz[0][0].length;
        for (int n = 0; n < numBands; n++) {
            mtz[i][j][n] = median;
        }
    }

}

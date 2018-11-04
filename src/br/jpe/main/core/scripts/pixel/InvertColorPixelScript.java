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
 * A pixel script to invert the colors in an image
 *
 * @author joaovperin
 */
public class InvertColorPixelScript implements PixelScript {

    @Override
    public void run(double[][][] mtz, Color color, int i, int j) {
        mtz[i][j][0] = applyInversion(color.getRed());
        mtz[i][j][1] = applyInversion(color.getGreen());
        mtz[i][j][2] = applyInversion(color.getBlue());
    }

    private static int applyInversion(int p) {
        int pixelValue = 255 - p;
        return Math.min(Math.max(pixelValue, 0), 255);
    }

}

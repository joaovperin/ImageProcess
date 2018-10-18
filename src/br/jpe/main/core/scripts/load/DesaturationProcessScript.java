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
package br.jpe.main.core.scripts.load;

import java.awt.Color;
import java.awt.image.BufferedImage;
import br.jpe.main.core.scripts.LoadPixelScript;

/**
 * A pixel script to load images as grayscale using desaturatin method
 *
 * @author joaovperin
 */
public class DesaturationProcessScript implements LoadPixelScript {

    @Override
    public void run(double[][][] mtz, BufferedImage img, int i, int j) {
        Color color = new Color(img.getRGB(i, j));

        int max = maxValue(color.getRed(), color.getGreen(), color.getBlue());
        int min = minValue(color.getRed(), color.getGreen(), color.getBlue());
        int median = (int) ((max + min) / 2);

        int numBands = mtz[0][0].length;
        for (int n = 0; n < numBands; n++) {
            mtz[i][j][n] = median;
        }
    }

    protected static final int maxValue(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

    protected static final int minValue(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

}

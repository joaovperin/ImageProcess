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
package br.jpe.main.core.mid;

import java.awt.Color;

/**
 * A pixel script to load images as grayscale using single color picking method
 *
 * @author joaovperin
 */
public class TestMidScript implements MidScript {

    @Override
    public double[][][] run(double[][][] src) {

        int iLen = src.length;
        int jLen = src[0].length;
        int cLen = src[0][0].length;

        double[][][] mtz = new double[iLen][jLen][cLen];

        for (int i = 0; i < iLen; i++) {
            for (int j = 0; j < jLen; j++) {
                Color color = getColor(src, i, j);
                setColor(mtz, i, j, color);
            }
        }
        return mtz;
    }

    private static Color getColor(double[][][] src, int i, int j) {
        return new Color((int) src[i][j][0], (int) src[i][j][1], (int) src[i][j][2]);
    }

    private static void setColor(double[][][] dest, int i, int j, Color c) {
        dest[i][j][0] = c.getRed();
        dest[i][j][1] = c.getGreen();
        dest[i][j][2] = c.getBlue();
    }

}

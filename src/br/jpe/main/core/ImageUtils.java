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
package br.jpe.main.core;

/**
 * A helper class with utilities for images and matrixes
 *
 * @author joaovperin
 */
public class ImageUtils {

    public static final double[][][] copy(double[][][] source) {
        int iLen = source.length;
        int jLen = source[0].length;
        int cLen = source[0][0].length;
        double[][][] mtz = new double[iLen][jLen][cLen];
        for (int i = 0; i < iLen; i++) {
            for (int j = 0; j < jLen; j++) {
                System.arraycopy(source[i][j], 0, mtz[i][j], 0, cLen);
            }
        }
        return mtz;
    }

    public static final double cos(int angle) {
        return Math.cos(Math.PI * angle / 180);
    }

    public static final double sin(int angle) {
        return Math.sin(Math.PI * angle / 180);
    }

}

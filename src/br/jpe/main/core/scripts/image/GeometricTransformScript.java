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
package br.jpe.main.core.scripts.image;

import br.jpe.main.core.ImageUtils;
import br.jpe.main.core.scripts.ImageScript;

/**
 * An script able to make geometric transformations
 *
 * @author joaovperin
 */
public abstract class GeometricTransformScript implements ImageScript {

    private static final int DEF_BG_COLOR = 255;

    public abstract double[][] getTransformMatrix(double[][][] mtz, int i, int j);

    public int getBackgroundColor() {
        return DEF_BG_COLOR;
    }

    @Override
    public final void run(double[][][] mtz) {

        double[][][] src = ImageUtils.copy(mtz);

        int iLen = mtz.length;
        int jLen = mtz[0].length;
        int cLen = mtz[0][0].length;

        int halfX = iLen / 2;
        int halfY = jLen / 2;

        for (int i = 0; i < iLen; i++) {
            for (int j = 0; j < jLen; j++) {

                double[][] tMtz = getTransformMatrix(src, i, j);

                int tI = i - halfX;
                int tJ = j - halfY;

                int nI = (int) Math.round(tI * tMtz[0][0] + tJ * tMtz[0][1] + 1 * tMtz[0][2]);
                int nJ = (int) Math.round(tI * tMtz[1][0] + tJ * tMtz[1][1] + 1 * tMtz[1][2]);

                nI += halfX;
                nJ += halfY;

                // If it fits the image, sets  the pixels
                if (nI < iLen && nJ < jLen && nI >= 0 && nJ >= 0) {
                    System.arraycopy(src[nI][nJ], 0, mtz[i][j], 0, cLen);
                } else {
                    for (int c = 0; c < cLen; c++) {
                        final int bgColor = getBackgroundColor();
                        mtz[i][j][c] = bgColor;
                    }
                }
            }
        }

    }

}

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
package br.jpe.main.core.scripts.image.geometric;

import br.jpe.main.core.scripts.image.GeometricTransformScript;

/**
 * Translate (move) the image on the plan
 *
 * @author joaovperin
 */
public class TranslationTransformScript extends GeometricTransformScript {

    private final double xDelta;
    private final double yDelta;
    private final int bgColor;

    public TranslationTransformScript(double xDelta, double yDelta) {
        this(xDelta, yDelta, 255);
    }

    public TranslationTransformScript(double xDelta, double yDelta, int bgColor) {
        this.xDelta = xDelta;
        this.yDelta = yDelta;
        this.bgColor = bgColor;
    }

    @Override
    public double[][] getTransformMatrix(double[][][] mtz, int i, int j) {
        return new double[][]{
            new double[]{1, 0, i - xDelta},
            new double[]{0, 1, j - yDelta},
            new double[]{0, 0, 1}
        };
    }

    @Override
    public int getBackgroundColor() {
        return bgColor;
    }

}

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
 * A pixel script to transform an image, pixel by pixel, by Vinicperin.
 *
 * @author joaovperin
 */
public class VinicperinPixelScript implements PixelScript {

    @Override
    public void run(double[][][] mtz, Color color, int i, int j) {
        int contrast = 120;

        mtz[i][j][0] = brightnessOrContrast(calc(color.getRed(), 102), contrast * 0.66f);
        mtz[i][j][1] = brightnessOrContrast(calc(color.getGreen(), 244), contrast * 0.426f);
        mtz[i][j][2] = brightnessOrContrast(calc(color.getBlue(), 255), contrast * 0.32f);
    }

    private static int calc(int c, double p) {
        return (int) (c * p / 255);
    }

    private static int brightnessOrContrast(int p, float b) {
        float calc = (p + b);
        return Math.max(Math.min((int) calc, 255), 0);
    }

}

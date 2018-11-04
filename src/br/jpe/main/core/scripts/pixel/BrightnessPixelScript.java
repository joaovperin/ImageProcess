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
import java.awt.Color;
import br.jpe.main.core.scripts.PixelScript;

/**
 * A pixel script to change brightness in an image
 *
 * @author joaovperin
 */
public class BrightnessPixelScript implements PixelScript {

    private final int bright;

    public BrightnessPixelScript(int bright) {
        this.bright = bright;
    }

    @Override
    public void run(double[][][] mtz, ImageColor color, int i, int j) {
        mtz[i][j][0] = applyBright(color.getRed(), bright);
        mtz[i][j][1] = applyBright(color.getGreen(), bright);
        mtz[i][j][2] = applyBright(color.getBlue(), bright);
    }

    private static int applyBright(int p, int bright) {
        return Math.min(Math.max(bright + p, 0), 255);
    }

}

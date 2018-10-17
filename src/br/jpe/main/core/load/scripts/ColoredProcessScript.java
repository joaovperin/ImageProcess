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
package br.jpe.main.core.load.scripts;

import java.awt.Color;
import java.awt.image.BufferedImage;
import br.jpe.main.core.load.PixelLoadScript;

/**
 * A pixel script to load colored images
 *
 * @author joaovperin
 */
public class ColoredProcessScript implements PixelLoadScript {

    @Override
    public void run(double[][][] mtz, BufferedImage img, int i, int j) {
        Color color = new Color(img.getRGB(i, j));
        mtz[i][j][0] = color.getRed();
        mtz[i][j][1] = color.getGreen();
        mtz[i][j][2] = color.getBlue();
    }
}

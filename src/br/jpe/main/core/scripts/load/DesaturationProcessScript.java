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

import br.jpe.main.core.ImageColor;
import java.awt.Color;
import java.awt.image.BufferedImage;
import br.jpe.main.core.scripts.LoadPixelScript;
import br.jpe.main.core.scripts.pixel.GreyscaleDesaturationPixelScript;

/**
 * A pixel script to load images as grayscale using desaturatin method
 *
 * @author joaovperin
 */
public class DesaturationProcessScript implements LoadPixelScript {

    private final GreyscaleDesaturationPixelScript s = new GreyscaleDesaturationPixelScript();

    @Override
    public void run(double[][][] mtz, BufferedImage img, int i, int j) {
        Color color = new Color(img.getRGB(i, j));
        s.run(mtz, new ImageColor(color.getRed(), color.getGreen(), color.getBlue()), i, j);
    }

    protected static final int maxValue(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

    protected static final int minValue(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

}

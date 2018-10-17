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
 * A pixel script to load images as grayscale using single color picking method
 *
 * @author joaovperin
 */
public class SingleColorProcessScript extends DesaturationProcessScript implements PixelLoadScript {

    private static final int CHANNEL_RED = 1;
    private static final int CHANNEL_GREEN = 2;
    private static final int CHANNEL_BLUE = 3;

    private final int channel;

    private SingleColorProcessScript(int channel) {
        this.channel = channel;
    }

    public static final SingleColorProcessScript red() {
        return new SingleColorProcessScript(CHANNEL_RED);
    }

    public static final SingleColorProcessScript green() {
        return new SingleColorProcessScript(CHANNEL_GREEN);
    }

    public static final SingleColorProcessScript blue() {
        return new SingleColorProcessScript(CHANNEL_BLUE);
    }

    @Override
    public void run(double[][][] mtz, BufferedImage img, int i, int j) {
        Color color = new Color(img.getRGB(i, j));

        int value = getValue(color);
        int numBands = mtz[0][0].length;
        for (int n = 0; n < numBands; n++) {
            mtz[i][j][n] = value;
        }
    }

    private final int getValue(Color color) {
        if (channel == CHANNEL_RED) {
            return color.getRed();
        }
        if (channel == CHANNEL_GREEN) {
            return color.getGreen();
        }
        if (channel == CHANNEL_BLUE) {
            return color.getBlue();
        }
        return 0;
    }

}

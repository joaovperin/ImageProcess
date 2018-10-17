/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jpe.main.core.scripts;

import br.jpe.main.core.PixelScript;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Perin
 */
public class SingleColorProcessScript extends DesaturationProcessScript implements PixelScript {

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

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
public class DecompositionProcessScript extends DesaturationProcessScript implements PixelScript {

    private final boolean max;

    private DecompositionProcessScript(boolean max) {
        this.max = max;
    }

    public static final DecompositionProcessScript max() {
        return new DecompositionProcessScript(true);
    }

    public static final DecompositionProcessScript min() {
        return new DecompositionProcessScript(false);
    }

    @Override
    public void run(double[][][] mtz, BufferedImage img, int i, int j) {
        Color color = new Color(img.getRGB(i, j));

        int value = (int) (max
                ? maxValue(color.getRed(), color.getGreen(), color.getBlue())
                : minValue(color.getRed(), color.getGreen(), color.getBlue()));

        int numBands = mtz[0][0].length;
        for (int n = 0; n < numBands; n++) {
            mtz[i][j][n] = value;
        }
    }

}

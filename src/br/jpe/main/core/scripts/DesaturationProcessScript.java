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
public class DesaturationProcessScript implements PixelScript {

    @Override
    public void run(double[][][] mtz, BufferedImage img, int i, int j) {
        Color color = new Color(img.getRGB(i, j));

        int max = maxValue(color.getRed(), color.getGreen(), color.getBlue());
        int min = minValue(color.getRed(), color.getGreen(), color.getBlue());
        int median = (int) ((max + min) / 2);

        int numBands = mtz[0][0].length;
        for (int n = 0; n < numBands; n++) {
            mtz[i][j][n] = median;
        }
    }

    protected static final int maxValue(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

    protected static final int minValue(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

}

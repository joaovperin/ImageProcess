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
public class GrayscaleProcessScript implements PixelScript {

    private final double pR;
    private final double pG;
    private final double pB;

    public GrayscaleProcessScript(double pR, double pG, double pB) {
        this.pR = pR;
        this.pG = pG;
        this.pB = pB;
    }

    @Override
    public void run(double[][][] mtz, BufferedImage img, int i, int j) {
        Color color = new Color(img.getRGB(i, j));
        int median = (int) ((color.getRed() * pR + color.getGreen() * pG + color.getBlue() * pB) / (pR + pG + pB));
        int numBands = mtz[0][0].length;
        for (int n = 0; n < numBands; n++) {
            mtz[i][j][n] = median;
        }
    }
}

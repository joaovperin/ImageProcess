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
public class ColoredProcessScript implements PixelScript {

    @Override
    public void run(double[][][] mtz, BufferedImage img, int i, int j) {
        Color color = new Color(img.getRGB(i, j));
        mtz[i][j][0] = color.getRed();
        mtz[i][j][1] = color.getGreen();
        mtz[i][j][2] = color.getBlue();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jpe.main.core;

import java.awt.image.BufferedImage;

/**
 *
 * @author Perin
 */
@FunctionalInterface
public interface PixelScript {
    
    public void run(double[][][] mtz, BufferedImage read, int i, int j);
    
}

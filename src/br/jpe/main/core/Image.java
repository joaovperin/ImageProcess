/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jpe.main.core;

/**
 *
 * @author Perin
 */
public class Image {

    private final double[][][] mtz;
    private final int width;
    private final int height;
    private final int bands;

    public Image(double[][][] mtz) {
        this.mtz = mtz;
        this.width = mtz.length;
        this.height = mtz[0].length;
        this.bands = mtz[0][0].length;
    }

    public double[][][] getMatrix() {
        return mtz;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getBands() {
        return bands;
    }

}

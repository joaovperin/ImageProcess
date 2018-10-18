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
package br.jpe.main.core;

import br.jpe.main.core.scripts.ImageScript;
import br.jpe.main.core.scripts.PixelScript;
import java.awt.Color;

/**
 * A helper class to process an image
 *
 * @author joaovperin
 */
public class ImageProcessor {

    public static final void process(double[][][] src, ImageScript... scripts) {
        for (ImageScript s : scripts) {
            s.run(src);
        }
    }

    public static final void process(double[][][] src, PixelScript... pixelScripts) {
        int iLen = src.length;
        int jLen = src[0].length;

        for (int i = 0; i < iLen; i++) {
            for (int j = 0; j < jLen; j++) {
                Color color = getColor(src, i, j);
                for (PixelScript p : pixelScripts) {
                    p.run(src, color, i, j);
                }
            }
        }
    }

    public static final Color getColor(double[][][] src, int i, int j) {
        return new Color((int) src[i][j][0], (int) src[i][j][1], (int) src[i][j][2]);
    }

}

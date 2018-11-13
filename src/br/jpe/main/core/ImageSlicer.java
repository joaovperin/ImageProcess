/*
 * Copyright (C) 2018 joaovperin
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

/**
 * A helper to slice images
 *
 * @author joaovperin
 */
public class ImageSlicer {

    private final Image source;

    private ImageSlicer(Image source) {
        this.source = ImageUtils.copy(source);
    }

    public Image slice(ImagePoint p0, int width, int height) {
        double[][][] mtz = new double[width][height][source.getBands()];
        double[][][] origin = source.getMatrix();
        int numBands = source.getBands();

        int iLen = p0.x + width;
        int jLen = p0.y + height;

        if (iLen > source.getWidth()) {
            iLen = source.getWidth();
        }
        if (jLen > source.getHeight()) {
            jLen = source.getHeight();
        }

        for (int i = p0.x; i < iLen; i++) {
            for (int j = p0.y; j < jLen; j++) {
                System.arraycopy(origin[i][j], 0, mtz[i - p0.x][j - p0.y], 0, numBands);
            }
        }
        return new Image(mtz);
    }

    public static final ImageSlicer create(Image src) {
        return new ImageSlicer(src);
    }

}

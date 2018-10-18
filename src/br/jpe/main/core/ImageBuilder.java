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

/**
 * A helper class to build images by applying filters
 *
 * @author joaovperin
 */
public class ImageBuilder {

    private final double[][][] matrix;

    private ImageBuilder(double[][][] matrix) {
        this.matrix = matrix;
    }

    public Image build() {
        return new Image(matrix);
    }

    public ImageBuilder applyScript(PixelScript... scripts) {
        ImageProcessor.process(matrix, scripts);
        return this;
    }

    public ImageBuilder applyScript(ImageScript... scripts) {
        ImageProcessor.process(matrix, scripts);
        return this;
    }

    public static ImageBuilder create(double[][][] source) {
        double[][][] mtz = ImageUtils.copy(source);
        return new ImageBuilder(mtz);
    }

    public static ImageBuilder create(Image image) {
        double[][][] mtz = ImageUtils.copy(image.getMatrix());
        return new ImageBuilder(mtz);
    }

}

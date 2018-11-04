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
package br.jpe.main.core.scripts;

import br.jpe.main.core.ImageColor;

/**
 * A simple script that iterates over each pixel of a matrix, used when loading
 * images
 *
 * @author joaovperin
 */
@FunctionalInterface
public interface PixelScript {

    public void run(double[][][] mtz, ImageColor c, int i, int j);

}

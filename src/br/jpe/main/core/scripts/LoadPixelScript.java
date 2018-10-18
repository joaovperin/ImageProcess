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

import java.awt.image.BufferedImage;

/**
 * A script used to process an image while it's loading, that is called once for
 * each pixel of a matrix
 *
 * @author joaovperin
 */
@FunctionalInterface
public interface LoadPixelScript {

    public void run(double[][][] mtz, BufferedImage read, int i, int j);

}

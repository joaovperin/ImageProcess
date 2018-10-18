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
package br.jpe.main.core.scripts.image;

import br.jpe.main.core.ImageProcessor;
import br.jpe.main.core.scripts.ImageScript;

/**
 * A simple image script created to test my routines :D
 *
 * @author joaovperin
 */
public class TestImageScript implements ImageScript {

    @Override
    public void run(double[][][] mtz) {
        ImageProcessor.process(mtz, (dest, c, i, j) -> {
            dest[i][j][0] = Math.min(c.getRed() + 87, 255);
            dest[i][j][1] = Math.min(c.getGreen() + 68, 255);
            dest[i][j][2] = Math.min(c.getBlue() + 32, 255);
        });
    }

}

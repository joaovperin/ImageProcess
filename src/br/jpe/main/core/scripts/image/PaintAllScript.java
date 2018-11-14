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

import br.jpe.main.core.ImageColor;
import br.jpe.main.core.scripts.ImageScript;

/**
 * A script to replace a color with another, in the entire image
 *
 * @author joaovperin
 */
public class PaintAllScript implements ImageScript {

    private final ImageColor targetColor;
    private final ImageColor replacement;

    public PaintAllScript(ImageColor targetColor, ImageColor replacement) {
        this.targetColor = targetColor;
        this.replacement = replacement;
    }

    @Override
    public void run(double[][][] mtz) {

        int iLen = mtz.length;
        int jLen = mtz[0].length;

        for (int i = 0; i < iLen; i++) {
            for (int j = 0; j < jLen; j++) {
                ImageColor color = ImageColor.fromArray(mtz[i][j]);
                if (color.equals(targetColor)) {
                    mtz[i][j] = replacement.get();
                }
            }
        }

    }

}

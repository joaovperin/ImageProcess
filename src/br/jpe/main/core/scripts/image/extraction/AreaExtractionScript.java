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
package br.jpe.main.core.scripts.image.extraction;

import br.jpe.main.core.ImageColor;
import br.jpe.main.core.ImageInfo;
import br.jpe.main.core.ImageInfoConstants;
import br.jpe.main.core.ImagePoint;
import br.jpe.main.core.scripts.InfoExtractorScript;

/**
 * An script to calculate the area of a picture based on a color
 *
 * @author joaovperin
 */
public class AreaExtractionScript implements InfoExtractorScript, ImageInfoConstants {

    private final ImagePoint seed;
    private final String label;

    public AreaExtractionScript(ImagePoint seed) {
        this(seed, PIXEL_COUNT);
    }

    public AreaExtractionScript(ImagePoint seed, String label) {
        this.seed = seed;
        this.label = label;
    }

    @Override
    public final void run(double[][][] mtz, ImageInfo info) {
        int count = 0;
        ImageColor targetColor = ImageColor.fromArray(mtz[seed.x][seed.y]);

        int iLen = mtz.length;
        int jLen = mtz[0].length;

        for (int i = 0; i < iLen; i++) {
            for (int j = 0; j < jLen; j++) {
                ImageColor color = ImageColor.fromArray(mtz[i][j]);
                if (color.equals(targetColor)) {
                    count++;
                }
            }
        }

        info.put(label, count);
    }

}

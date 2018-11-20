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
import br.jpe.main.core.ImagePoint;
import br.jpe.main.core.ImageUtils;
import br.jpe.main.core.scripts.ImageScript;
import java.util.HashMap;
import java.util.Map;

/**
 * A script to replace a color with another, in the entire image
 *
 * @author joaovperin
 */
public class BinaryLabelingScript implements ImageScript {

    private final ImageColor bgColor;
    private final boolean fill;
    private final Map<ImageColor, ImagePoint> labels;

    public BinaryLabelingScript(ImageColor bgColor) {
        this(bgColor, false);
    }

    public BinaryLabelingScript(ImageColor bgColor, boolean fill) {
        this.bgColor = bgColor;
        this.fill = fill;
        this.labels = new HashMap<>();
    }

    @Override
    public void run(double[][][] src) {

        int iLen = src.length;
        int jLen = src[0].length;

        for (int i = 0; i < iLen; i++) {
            for (int j = 0; j < jLen; j++) {
                ImageColor color = ImageColor.fromArray(src[i][j]);
                // If the color changed
                if (!color.equals(bgColor) && !labels.containsKey(color)) {
                    // Paint with a random color
                    ImageColor newColor = ImageColor.random(labels.keySet());
                    ImagePoint p = new ImagePoint(i, j);
                    new FloodfillEightDirectionsScript(p, newColor).run(src);
                    // If have to fill, fill it all
                    if (fill) {

                        if (ImageColor.fromArray(src[p.x][p.y]).equals(bgColor) && !ImageColor.
                                fromArray(src[p.x - 1][p.y + 1]).equals(bgColor)) {
//                            new FloodfillEightDirectionsScript(p, newColor).run(src);
                            // TA DANDO EXCEPTION AQUI (OUT OF BOUNDS)
                        } else if (ImageUtils.inBounds(src, new ImagePoint(p.x - 1, p.y + 1)) &&
                                !ImageColor.fromArray(src[p.x][p.y]).equals(bgColor) &&
                                ImageColor.fromArray(src[p.x - 1][p.y + 1]).equals(bgColor)) {
                            new FloodfillEightDirectionsScript(p.southeast(), newColor).run(src);
                        }
                    }
                    // Put the new label
                    labels.put(newColor, new ImagePoint(i, j));
                }
            }
        }
    }

    public Map<ImageColor, ImagePoint> getColors() {
        return (HashMap) ((HashMap<ImageColor, ImagePoint>) labels).clone();
    }

}

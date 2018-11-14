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
import br.jpe.main.core.ImageUtils;
import br.jpe.main.core.scripts.InfoExtractorScript;
import java.util.LinkedList;
import java.util.Queue;

/**
 * An script to calculate the area of a picture based on a color
 *
 * @author joaovperin
 */
public class PerimeterFloodfillExtractionScript implements InfoExtractorScript, ImageInfoConstants {

    private final ImagePoint seed;
    private final String label;

    private final Queue<ImagePoint> queue;

    public PerimeterFloodfillExtractionScript(ImagePoint seed, String label) {
        this.seed = seed;
        this.label = label;
        this.queue = new LinkedList<>();
    }

    @Override
    public final void run(double[][][] mtz, ImageInfo info) {
        int paint = 0;
        int removed = 0;
        
        ImageColor replacement = ImageColor.blue();
        

        ImageColor targetColor = ImageColor.fromArray(mtz[seed.x][seed.y]);
        double[][][] src = ImageUtils.copy(mtz);

        queue.add(seed);
        while (!queue.isEmpty()) {
            ImagePoint point = queue.poll();

            if (!ImageUtils.inBounds(src, point)) {
                return;
            }
            if (!ImageColor.fromArray(src[point.x][point.y]).equals(targetColor)) {
                return;
            }

            ImagePoint node = point;
            ImagePoint w = node;
            ImagePoint e = node;
            while (ImageUtils.inBounds(mtz, w) && ImageColor.fromArray(mtz[w.x][w.y]).equals(targetColor)) {
                w = w.west();
            }
            while (ImageUtils.inBounds(mtz, e) && ImageColor.fromArray(mtz[e.x][e.y]).equals(targetColor)) {
                e = e.east();
            }
            for (int x = w.x + 1; x < e.x; x++) {
                mtz[x][node.y] = replacement.get();
                paint++;
            }
            for (int x = w.x + 1; x < e.x; x++) {
                ImagePoint north = new ImagePoint(x, node.y).north();
                if (!ImageUtils.inBounds(src, north)) {
                    removed++;
                } else {
                    ImageUtils.push(queue, src, targetColor, north);
                }
                ImagePoint south = new ImagePoint(x, node.y).south();
                if (!ImageUtils.inBounds(src, south)) {
                    removed++;
                } else {
                    ImageUtils.push(queue, src, targetColor, south);
                }
            }
        }

        info.put(label, removed);
    }

}

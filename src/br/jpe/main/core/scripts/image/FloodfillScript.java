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
import java.util.LinkedList;
import java.util.Queue;

/**
 * Floodfill script
 *
 * @author joaovperin
 */
public class FloodfillScript implements ImageScript {

    private final ImagePoint seed;
    private final ImageColor replacement;
    private final Queue<ImagePoint> queue;

    public FloodfillScript(ImagePoint seed, ImageColor replacement) {
        this.seed = seed;
        this.replacement = replacement;
        this.queue = new LinkedList<>();
    }

    @Override
    public void run(double[][][] mtz) {

        // https://github.com/GrupoTorax/PDI/blob/master/src/main/java/org/paim/pdi/FloodFillProcess.java
        ImageColor targetColor = ImageColor.fromArray(mtz[seed.x][seed.y]);
        if (targetColor.equals(replacement)) {
            return;
        }

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
            }
            for (int x = w.x + 1; x < e.x; x++) {
                ImageUtils.push(queue, src, targetColor, new ImagePoint(x, node.y).north());
                ImageUtils.push(queue, src, targetColor, new ImagePoint(x, node.y).south());
            }
        }
    }

}

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
package br.jpe.main.core.scripts.image.convolution;

import br.jpe.main.core.scripts.image.ConvolutionTransformScript;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Apply a filter based on the median
 *
 * @author joaovperin
 */
public class ModeFilterScript extends ConvolutionTransformScript {

    final double[][] tmp = new double[kiLen][kjLen];

    public ModeFilterScript() {
        super(new double[][] {
            new double[] { 1, 1, 1 },
            new double[] { 1, 1, 1 },
            new double[] { 1, 1, 1 }
        });
    }

    @Override
    protected void forEachColorStart(double[][][] src, int i, int j, int c) {
    }

    @Override
    protected void forEachConvolutedPixel(double[][][] mtz, int i, int j, int c, int kI, int kJ) {
        tmp[kI][kJ] = mtz[i + kI - 1][j + kJ - 1][c];
    }

    @Override
    protected void forEachColorEnd(double[][][] mtz, int i, int j, int c) {
        final double x = calcMode(tmp);
        mtz[i][j][c] = Math.round(x);
    }

    private static Map<Double, Integer> createHistogram(double[][] mtz) {
        Map<Double, Integer> histogram = new HashMap<>();
        final double iLen = mtz.length;
        for (int i = 0; i < iLen; i++) {
            final double jLen = mtz[i].length;
            for (int j = 0; j < jLen; j++) {
                final double v = mtz[i][j];
                histogram.put(v, histogram.getOrDefault(v, -1) + 1);
            }
        }
        return histogram;
    }

    public static final double calcMode(double[][] mtz) {
        Map<Double, Integer> histogram = createHistogram(mtz);
        Optional<Map.Entry<Double, Integer>> max = histogram.entrySet().stream().max((e1, e2) -> e1.getValue().
                compareTo(e2.getValue()));
        return max.isPresent() ? max.get().getKey() : -1;
    }

}

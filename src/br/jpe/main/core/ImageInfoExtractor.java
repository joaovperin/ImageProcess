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
package br.jpe.main.core;

import br.jpe.main.core.scripts.InfoExtractorScript;

/**
 * Descrição da classe.
 *
 * @author joaovperin
 */
public class ImageInfoExtractor {

    private final double[][][] mtz;
    private final ImageInfo info;

    private ImageInfoExtractor(double[][][] mtz, ImageInfo info) {
        this.mtz = mtz;
        this.info = info;
    }

    public final ImageInfoExtractor applyScript(InfoExtractorScript... scripts) {
        for (InfoExtractorScript s : scripts) {
            s.run(mtz, info);
        }
        return this;
    }

    public final ImageInfo extract() {
        return info;
    }

    public static final ImageInfoExtractor create(double[][][] mtz) {
        return new ImageInfoExtractor(mtz, ImageInfo.create());
    }

}

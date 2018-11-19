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

/**
 * An image point
 *
 * @author joaovperin
 */
public class ImagePoint {

    public final int x;
    public final int y;

    public ImagePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ImagePoint northwest() {
        return new ImagePoint(x - 1, y - 1);
    }

    public ImagePoint southwest() {
        return new ImagePoint(x - 1, y + 1);
    }

    public ImagePoint west() {
        return new ImagePoint(x - 1, y);
    }

    public ImagePoint east() {
        return new ImagePoint(x + 1, y);
    }

    public ImagePoint northeast() {
        return new ImagePoint(x + 1, y - 1);
    }

    public ImagePoint south() {
        return new ImagePoint(x, y + 1);
    }

    public ImagePoint southeast() {
        return new ImagePoint(x + 1, y + 1);
    }

    public ImagePoint north() {
        return new ImagePoint(x, y - 1);
    }

    public static ImagePoint mean(double[][][] mtz) {
        int x = mtz.length / 2;
        int y = mtz[0].length / 2;
        return new ImagePoint(x, y);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.x;
        hash = 71 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImagePoint other = (ImagePoint) obj;
        if (this.x != other.x) {
            return false;
        }
        return this.y == other.y;
    }

    @Override
    public String toString() {
        return "ImagePoint{" + "x=" + x + ", y=" + y + '}';
    }

}

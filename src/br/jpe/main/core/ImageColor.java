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
package br.jpe.main.core;

/**
 * An image color
 *
 * @author joaovperin
 */
public class ImageColor {

    private final int r;
    private final int g;
    private final int b;

    public ImageColor() {
        this(0, 0, 0);
    }

    public ImageColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getRed() {
        return r;
    }

    public int getGreen() {
        return g;
    }

    public int getBlue() {
        return b;
    }

    public double[] get() {
        return new double[] { r, g, b };
    }

    public static final ImageColor fromArray(double[] m) {
        return new ImageColor((int) m[0], (int) m[1], (int) m[2]);
    }

    public static final ImageColor black() {
        return new ImageColor(0, 0, 0);
    }

    public static final ImageColor white() {
        return new ImageColor(255, 255, 255);
    }

    public static final ImageColor red() {
        return new ImageColor(255, 0, 0);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.r;
        hash = 79 * hash + this.g;
        hash = 79 * hash + this.b;
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
        final ImageColor other = (ImageColor) obj;
        if (this.r != other.r) {
            return false;
        }
        if (this.g != other.g) {
            return false;
        }
        return this.b == other.b;
    }

    @Override
    public String toString() {
        return "ImageColor{" + "r=" + r + ", g=" + g + ", b=" + b + '}';
    }

}

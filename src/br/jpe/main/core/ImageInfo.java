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

import java.util.HashMap;
import java.util.Map;

/**
 * An image information
 *
 * @author joaovperin
 */
public class ImageInfo implements ImageInfoConstants {

    private final Map<String, Object> info;

    private ImageInfo() {
        this.info = new HashMap<>();
    }

    public void put(String key, Object value) {
        if (info.containsKey(key)) {
            throw new ImageInformationAlreadyExistsException(key);
        }
        info.put(key, value);
    }

    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public long getLong(String key) {
        return Long.parseLong(get(key));
    }

    public float getFloat(String key) {
        return Float.parseFloat(get(key));
    }

    public double getDouble(String key) {
        return Double.parseDouble(get(key));
    }

    public String get(String key) {
        return info.get(key).toString();
    }

    public <T> T get(String key, Class<T> type) {
        return type.cast(info.get(key));
    }

    public static final ImageInfo create() {
        return new ImageInfo();
    }

}

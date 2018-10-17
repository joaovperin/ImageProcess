/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jpe.main.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author Perin
 */
public class ImageLoader {

    public static ImageFactory fromResources(String name) throws IOException {
        InputStream stream = ImageLoader.class.getClassLoader().getResourceAsStream(name);
        return new ImageFactory(ImageIO.read(stream));
    }

    public static ImageFactory fromFile(File path) throws IOException {
        BufferedImage image = ImageIO.read(path);
        return new ImageFactory(image);
    }

}

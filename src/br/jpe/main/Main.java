/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jpe.main;

import br.jpe.main.core.Image;
import br.jpe.main.core.ImageLoader;
import br.jpe.main.core.ImageWriter;
import java.io.IOException;

/**
 *
 * @author Perin
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        final String imgName = "paisagem_colorful.png";
        Image img = ImageLoader.fromResources(imgName).asOriginal();
        ImageWriter.save("D:/Samples/results/prc_".concat(imgName), img);
    }

}

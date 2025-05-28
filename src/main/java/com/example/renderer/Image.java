package com.example.renderer;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



public class Image{
    private final int[] pixelBuffer;
    private  BufferedImage image;
    final int width, height;
    private WritableImage fxImage;

    public Image(int w, int h) {
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        width = w;
        height = h;
                
        pixelBuffer = new int[height*width];
    }

    public void plotPixel(int x, int y, ImageColor color) {
        int rgb = (color.getR() << 16) | (color.getG() << 8) | color.getB();
        pixelBuffer[y * width + x] = rgb;
    }
    
    public void save(File file) throws IOException {
        updateImage();
        ImageIO.write(image, "PNG", file);
    }

    public void updateImage() {
        image.setRGB(0, 0, width, height, pixelBuffer, 0, width);
    }


    public javafx.scene.image.Image toImageView() {
        if (fxImage == null || (int) fxImage.getWidth() != image.getWidth() || (int) fxImage.getHeight() != image.getHeight()) {
            fxImage = new WritableImage(image.getWidth(), image.getHeight());
        }

        SwingFXUtils.toFXImage(this.image, fxImage);
        return fxImage;
    }
    

    
}

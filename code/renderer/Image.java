package code.renderer;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;



public class Image implements Closeable{
    private final BufferedImage image;
    private final Graphics2D graphics;
    int width;
    int height;

    public Image(int w, int h) {
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        graphics = image.createGraphics();
        height = h;
        width = w;
    }

    public void plotPixel(int x, int y, ImageColor color) {
        graphics.setPaint(
                new Color(
                        color.getR(),
                        color.getG(),
                        color.getB()
                )
        );

        graphics.fillRect(x, y, 1, 1);
    }

    /*
     * @params filename filename of the file that the image will be stored at
     * 
     * stores a copy of the image to a PNG file at the given location 
     */
    public void save(String filename) throws IOException {
        ImageIO.write(image, "PNG", new File(filename));
    }


    public ImageView toImageView() {
        WritableImage fxImage = SwingFXUtils.toFXImage(image, null);
        return new ImageView(fxImage);
    }
    

    @Override
    public void close() throws IOException {
        graphics.dispose();
    }
    
}

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
    
    public void save(File file) throws IOException {
        ImageIO.write(image, "PNG", file);
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

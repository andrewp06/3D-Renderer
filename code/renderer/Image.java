package code.renderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;


public class Image implements Closeable {
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

    public void save(String filename) throws IOException {
        boolean success = ImageIO.write(image, "PNG", new File(filename));
        System.out.println(success);
    }

    @Override
    public void close() throws IOException {
        graphics.dispose();
    }

    public static void firstTest(Screen screen){
        Image image = screen.image;
        for(int i = 1; i<image.width;i++){
            for(int j = 1; j<image.height;j++){
                Vector point = Calculate.createRayPoint(i, j, screen);
                int red = Math.round(((point.x+1)/2)*255);
                int green = Math.round(((point.y+1)/2)*255);
                image.plotPixel(i, j, new ImageColor(red, green, 100));
            }
        }
    }

    public static void main(String[] args) throws IOException{
        Screen screen = new Screen();
        firstTest(screen);
        screen.image.save("code/renderer/test.png");
    }
}

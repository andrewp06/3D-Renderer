package code.renderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Screen {
    
    Image image;
    ImagePlane imagePlane;
    Vector camera;
    List<Shape> shapes;

    public Screen(){
        image = new Image(256,192);
        imagePlane = new ImagePlane();
        camera = new Vector(0, 0, -1);
        shapes = new ArrayList<>();
    }

    public Vector getCamera() {
        return camera;
    }

    public void addShape(Shape shape){
        shapes.add(shape);
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public static void main(String[] args) throws IOException{
        Screen screen = new Screen();
        screen.addShape(new Sphere(.25f,new Vector(-.5f, 0, 1),new ImageColor(0, 255, 0)));
        screen.addShape(new Sphere(1f,new Vector(1f, -.5f, 1),new ImageColor(255, 0, 255)));
        screen.addShape(new Sphere(3f,new Vector(0, .5f, 6)));

        Image.shapeTest(screen);
        screen.image.save("code/renderer/sphereTest.png");
    }
}

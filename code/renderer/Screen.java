package code.renderer;

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

    public static void main(String[] args) {
        Screen screen = new Screen();
        screen.addShape(new Sphere(.25f,new Vector(0, 0, 1)));
        screen.addShape(new Sphere(.5f,new Vector(0, .5f, 2)));

        System.out.println(screen.getShapes().toString());
    }
}

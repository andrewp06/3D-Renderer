package code.renderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Screen {
    
    Image image;
    ImagePlane imagePlane;
    Vector camera;
    List<Shape> shapes;
    List<Light> lights;
    Color ambientLight;

    public Screen(){
        image = new Image(256,192);
        imagePlane = new ImagePlane();
        camera = new Vector(0, 0, -1);
        shapes = new ArrayList<>();
        lights = new ArrayList<>();
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

    public void setAmbientLight(Color ambientLight) {
        this.ambientLight = ambientLight;
    }

    public void addLight(Light light){
        lights.add(light);
    }

    /*
     * iterates through each pixel, calculates the ray and sets each pixel to a color base on its position in the imagePlane
     */
    public void firstTest(){
        for(int i = 1; i<image.width;i++){
            for(int j = 1; j<image.height;j++){
                Vector point = Calculate.createRay(i, j, this).origin;
                int red = Math.round(((point.x+1)/2)*255);
                int green = Math.round(((point.y+.75f)/2)*255);
                image.plotPixel(i, j, new ImageColor(red, green, 100));
            }
        }
    }

    /*
     * iterates through each pixel, calculates the ray and checks for any intersections with objects
     * it then plots the pixel with the correct color on the image in screen
     */
    public void shapeTest(){
        for(int i = 1; i<image.width;i++){
            for(int j = 1; j<image.height;j++){
                Ray ray = Calculate.createRay(i, j, this);
                ImageColor color = Calculate.shapeInFront(shapes, ray);
                image.plotPixel(i, j, color);
            }
        }
    }

    public static void main(String[] args) throws IOException{
        Screen screen = new Screen();
        screen.addShape(new Sphere(.25f,new Vector(-.5f, 0, 1),new Color(0, 1, 0)));
        screen.addShape(new Sphere(1f,new Vector(1f, -.5f, 1),new Color(1, 1, 1)));
        screen.addShape(new Sphere(3f,new Vector(0, .5f, 6)));
        
        screen.addLight(new Light(new Vector(0, 2, 0),new Color(.6f, .6f, .6f),new Color(.25f, .25f, .25f)));
        screen.addLight(new Light(new Vector(-2, 0, 0),new Color(.6f, .6f, .6f),new Color(.25f, .25f, .25f)));
        screen.setAmbientLight(new Color(.6f, 0, .6f));
        
        screen.shapeTest();
        screen.image.save("code/renderer/sphereTest.png");
    }
}

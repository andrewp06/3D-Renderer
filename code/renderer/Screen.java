package code.renderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Screen {
    
    Image image;
    ImagePlane imagePlane;
    Vector camera;
    List<Sphere> spheres;
    List<Light> lights;
    Color ambientLight;

    public Screen(){
        image = new Image(1440,1080);
        imagePlane = new ImagePlane();
        camera = new Vector(0, 0, -1.25f);
        spheres = new ArrayList<>();
        lights = new ArrayList<>();
    }

    public Vector getCamera() {
        return camera;
    }

    public void addSphere(Sphere sphere){
        spheres.add(sphere);
    }

    public List<Sphere> getShperes() {
        return spheres;
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
                ImageColor color = Calculate.shapeInFront(spheres, ray,this);
                image.plotPixel(i, j, color);
            }
        }
    }

    public static void main(String[] args) throws IOException{
        Screen screen = new Screen();
        screen.addSphere(new Sphere(1f,new Vector(1.5f, -.5f, 2),new Color(0, .75f, 0)));
        screen.addSphere(new Sphere(1f,new Vector(0, 0, 3),new Color(.75f,0,0 )));
        screen.addSphere(new Sphere(1f,new Vector(-4, .5f, 6), new Color(0, 0, .75f)));
        
        Light fillLight = new Light(new Vector(-3, 2, -3), new Color(.4f),new Color(.4f));
        screen.lights.add(fillLight);

        Light keyLight = new Light(new Vector(10, 7, 3), new Color(.7f),new Color(.7f));
        screen.lights.add(keyLight);

        screen.ambientLight = new Color(.3f);

        screen.shapeTest();
        screen.image.save("code/renderer/sphereTest3.png");
    }
}

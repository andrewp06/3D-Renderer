package code.renderer;

import java.io.File;
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
        image = new Image(3456,2234);
        imagePlane = new ImagePlane();
        camera = new Vector(0, 0, -1.5f);
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
    public void shapeTest(int n, int recursion){
        for(int i = 1; i<image.width;i++){
            for(int j = 1; j<image.height;j++){
                ImageColor color = Calculate.colorToImageColor(SSAA(i,j,n,recursion));
                image.plotPixel(i, j, color);
            }
        }
    }

    private Color SSAA(int xPixel,int yPixel,int n,int recursion){
        Color finalColor = new Color(0, 0, 0);
        for (int i = 0; i<n; i++){
            for (int j = 0; j<n; j++){
                Ray ray = Calculate.createRayPoint3SubDivisions(xPixel,yPixel,this,i,j,n);
                Color sampleColor = Calculate.shapeInFront(ray, this, recursion);
                finalColor = Calculate.addColors(finalColor, sampleColor);
            }
        }
        finalColor = new Color(finalColor.getR()/(n*n),finalColor.getG()/(n*n),finalColor.getB()/(n*n));
        return finalColor;
    }


    public void sPlusA(){
        //s 
        addSphere(new Sphere(.5f,new Vector(-10, 0, 20f),new Color(1, 1f, 0)));
        addSphere(new Sphere(.5f,new Vector(-9, 0, 20f),new Color(1, 1f, 0)));
        addSphere(new Sphere(.5f,new Vector(-8, 0, 20f),new Color(1, 1f, 0)));

        addSphere(new Sphere(.5f,new Vector(-11, 1, 20f),new Color(1, 1f, 0)));
        addSphere(new Sphere(.5f,new Vector(-11, 2, 20f),new Color(1, 1f, 0)));

        addSphere(new Sphere(.5f,new Vector(-10, 3, 20f),new Color(1, 1f, 0)));
        addSphere(new Sphere(.5f,new Vector(-9, 3, 20f),new Color(1, 1f, 0)));
        addSphere(new Sphere(.5f,new Vector(-8, 3, 20f),new Color(1, 1f, 0)));

        addSphere(new Sphere(.5f,new Vector(-7, -1, 20f),new Color(1, 1f, 0)));
        addSphere(new Sphere(.5f,new Vector(-7, -2, 20f),new Color(1, 1f, 0)));

        addSphere(new Sphere(.5f,new Vector(-10, -3, 20f),new Color(1, 1f, 0)));
        addSphere(new Sphere(.5f,new Vector(-9, -3, 20f),new Color(1, 1f, 0)));
        addSphere(new Sphere(.5f,new Vector(-8, -3, 20f),new Color(1, 1f, 0)));

        //A
        addSphere(new Sphere(.5f,new Vector(10, 0, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(11, 0, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(9, 0, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(8, 0, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(12, 0, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(10, 3, 20),new Color(0f, 0f, 1f)));

        addSphere(new Sphere(.5f,new Vector(8, 1, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(9, 2, 20),new Color(0f, 0f, 1f)));

        addSphere(new Sphere(.5f,new Vector(8, -1, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(8, -2, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(8, -3, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(12, 1, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(11, 2, 20),new Color(0f, 0f, 1f)));

        addSphere(new Sphere(.5f,new Vector(12, -1, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(12, -2, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(12, -3, 20),new Color(0f, 0f, 1f)));

        //+
        addSphere(new Sphere(.5f,new Vector(0, 0, 20),new Color(.1f, .75f, .2f)));
        addSphere(new Sphere(.5f,new Vector(0, -1, 20),new Color(.1f, .75f, .2f)));
        addSphere(new Sphere(.5f,new Vector(0, 1, 20),new Color(.1f, .75f, .2f)));
        addSphere(new Sphere(.5f,new Vector(1, 0, 20),new Color(.1f, .75f, .2f)));
        addSphere(new Sphere(.5f,new Vector(-1, 0, 20),new Color(.1f, .75f, .2f)));
    }

    public void saveImage() throws IOException{
        String templetFilename = "photos/RenderedImage";
        int count = 0;
        while(true){
            File file = new File(templetFilename+count+".png");
            if (!file.exists()){
                image.save(file);
                image.close();
                return;
            }
            count++;
        }
    }

    public static void main(String[] args) throws IOException{
        Screen screen = new Screen();

        screen.addSphere(new Sphere(1f,new Vector(2f, -.75f, 2f),new Color(.1f, .75f, .1f)));
        screen.addSphere(new Sphere(1f,new Vector(-.25f, 1.5f, 2.5f),new Color(.8f,.2f,0 )));
        screen.addSphere(new Sphere(1f,new Vector(-2.5f, .5f, 3), new Color(0, .8f, .8f)));
        screen.addSphere(new Sphere(.7f,new Vector(0, -.5f, 3.5f), new Color(1f, 0, 0)));
        screen.addSphere(new Sphere(.75f,new Vector(-1.25f, -1.5f, 3), new Color(.4f, 0, .75f)));
        screen.addSphere(new Sphere(.75f,new Vector(2f, 1.25f, 2.5f),new Color(.8f,.2f,.4f )));



        
        screen.addLight(new Light(
            new Vector(2, 5, -5),
            new Color(.8f),
            new Color(.8f)
        ));
        screen.addLight(new Light(
            new Vector(-3, 2, -2),
            new Color(.5f),
            new Color(.2f)
        ));
        screen.addLight(new Light(
            new Vector(0, 5, 5),
            new Color(.3f),
            new Color(.8f)
        ));

        screen.ambientLight = new Color(.05f);

        screen.shapeTest(6,1);
        screen.saveImage();
    }
}

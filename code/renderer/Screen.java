package code.renderer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Screen {
    
    Image image;
    ImagePlane imagePlane;
    Vector camera;
    List<Sphere> spheres;
    List<Light> lights;
    Color ambientLight;
    int SSAAsamples;
    int recursionDepth;

    public Screen(int width, int height){
        image = new Image(width,height);
        imagePlane = new ImagePlane();
        camera = new Vector(0, 0, -1.5f);
        spheres = new ArrayList<>();
        lights = new ArrayList<>();
        SSAAsamples = 1;
        recursionDepth = 1;
    }

    public List<Light> getLights() {
        return lights;
    }

    public Screen(){
        this(3456,2234);
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

    private static int computeTileSize(int width, int height, int numThreads) {
        int minTile = 16;  
        int maxTile = 128;  
        int estimatedTile = (int) Math.sqrt((width * height) / (numThreads * 2));
        return Math.max(minTile, Math.min(maxTile, estimatedTile));
    }

    public void shapeTestMultiThread(){
        int numThreads = Runtime.getRuntime().availableProcessors();

        int tileSize = computeTileSize(image.width, image.height, numThreads);

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        for (int x = 0; x < image.width; x += tileSize) {
            for (int y = 0; y < image.height; y += tileSize) {
                int endX = Math.min(x + tileSize, image.width);
                int endY = Math.min(y + tileSize, image.height);
                executor.submit(new ColorCalcThread(x, endX, y, endY, SSAAsamples, recursionDepth, this));
            }
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }

    public Color SSAA(int xPixel,int yPixel,int n,int recursion){
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
        addSphere(new Sphere(.5f,new Vector(10, 0, 20f),new Color(1, 1f, 0)));
        addSphere(new Sphere(.5f,new Vector(9, 0, 20f),new Color(1, 1f, 0)));
        addSphere(new Sphere(.5f,new Vector(8, 0, 20f),new Color(1, 1f, 0)));

        addSphere(new Sphere(.5f,new Vector(7, 1, 20f),new Color(1, 1f, 0)));
        addSphere(new Sphere(.5f,new Vector(7, 2, 20f),new Color(1, 1f, 0)));

        addSphere(new Sphere(.5f,new Vector(10, 3, 20f),new Color(1, 1f, 0)));
        addSphere(new Sphere(.5f,new Vector(9, 3, 20f),new Color(1, 1f, 0)));
        addSphere(new Sphere(.5f,new Vector(8, 3, 20f),new Color(1, 1f, 0)));

        addSphere(new Sphere(.5f,new Vector(11, -1, 20f),new Color(1, 1f, 0)));
        addSphere(new Sphere(.5f,new Vector(11, -2, 20f),new Color(1, 1f, 0)));

        addSphere(new Sphere(.5f,new Vector(10, -3, 20f),new Color(1, 1f, 0)));
        addSphere(new Sphere(.5f,new Vector(9, -3, 20f),new Color(1, 1f, 0)));
        addSphere(new Sphere(.5f,new Vector(8, -3, 20f),new Color(1, 1f, 0)));

        //A
        addSphere(new Sphere(.5f,new Vector(-10, 0, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(-11, 0, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(-9, 0, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(-8, 0, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(-12, 0, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(-10, 3, 20),new Color(0f, 0f, 1f)));

        addSphere(new Sphere(.5f,new Vector(-8, 1, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(-9, 2, 20),new Color(0f, 0f, 1f)));

        addSphere(new Sphere(.5f,new Vector(-8, -1, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(-8, -2, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(-8, -3, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(-12, 1, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(-11, 2, 20),new Color(0f, 0f, 1f)));

        addSphere(new Sphere(.5f,new Vector(-12, -1, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(-12, -2, 20),new Color(0f, 0f, 1f)));
        addSphere(new Sphere(.5f,new Vector(-12, -3, 20),new Color(0f, 0f, 1f)));

        //+
        addSphere(new Sphere(.5f,new Vector(0, 0, 20),new Color(.1f, .75f, .2f)));
        addSphere(new Sphere(.5f,new Vector(0, -1, 20),new Color(.1f, .75f, .2f)));
        addSphere(new Sphere(.5f,new Vector(0, 1, 20),new Color(.1f, .75f, .2f)));
        addSphere(new Sphere(.5f,new Vector(1, 0, 20),new Color(.1f, .75f, .2f)));
        addSphere(new Sphere(.5f,new Vector(-1, 0, 20),new Color(.1f, .75f, .2f)));

        addLight(new Light(
            new Vector(2, 5, -5),
            new Color(.8f),
            new Color(.8f)
        ));
        addLight(new Light(
            new Vector(0, 10, 25),
            new Color(.5f),
            new Color(.2f)
        ));
        addLight(new Light(
            new Vector(0, 25, 18),
            new Color(.3f),
            new Color(.8f)
        ));
    }

    public void saveImage() throws IOException{
        String templetFilename = "photos/RenderedImage";
        int count = 0;
        while(true){
            File file = new File(templetFilename+count+".png");
            if (!file.exists()){
                image.save(file);
                return;
            }
            count++;
        }
    }

    @Override
    public String toString() {
        String result = "\nScreen: \n"+" Ambient Light = "+ ambientLight+"\n SSAA Sample Size = "+SSAAsamples+"\n Reflection Recursion Depth = "+recursionDepth+"\n Lights:\n";

        for (Light light : lights) {
            result+="\t"+light+"\n";
        }

        result+=" Objects:\n";

        for (Sphere sphere : spheres) {
            result+="\t"+sphere+"\n";
        }

        return result;
    }



    public static void main(String[] args) throws IOException{
        Screen screen = new Screen(16,10);

        screen.addSphere(new Sphere(1f,new Vector(2f, -.75f, 2f),new Color(.1f, .75f, .1f)));
        screen.addSphere(new Sphere(1f,new Vector(-.25f, 1.5f, 2.5f),new Color(.8f,.2f,0 )));
        
        screen.addLight(new Light(
            new Vector(2, 5, -5),
            new Color(.8f),
            new Color(.8f)
        ));

        // screen.sPlusA();

        screen.ambientLight = new Color(.05f);

        
        long startTime = System.currentTimeMillis();
        // screen.shapeTestMultiThread(6,1);
        screen.shapeTest(6,1);

        screen.saveImage();
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println(estimatedTime);

    }
}

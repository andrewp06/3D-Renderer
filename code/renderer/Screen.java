package code.renderer;



public class Screen {
    
    Image image;
    ImagePlane imagePlane;
    Vector camera;

    public Screen(){
        image = new Image(256,192);
        imagePlane = new ImagePlane();
        camera = getCamera();
    }

    public static Vector getCamera(){
        return new Vector(0, 0, -1);
    }

    public static void main(String[] args) {
        ImagePlane plane = new ImagePlane();
        System.out.println(plane.toString());
    }
}

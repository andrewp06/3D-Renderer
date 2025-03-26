package code.renderer;

public class Calculate {

    public static Vector createRayPoint(int xPixel, int yPixel, Screen screen){
        Image image  = screen.image;
        ImagePlane imagePlane = screen.imagePlane;
        float alpha = (float) xPixel / image.width;
        float beta = (float) yPixel / image.height;
        Vector t = Vector.vectorAddition(
            Vector.scalarMult(imagePlane.topLeft, (1-alpha)), 
            Vector.scalarMult(imagePlane.topRight, alpha)
            );

        Vector b = Vector.vectorAddition(
            Vector.scalarMult(imagePlane.bottomLeft, (1-alpha)), 
            Vector.scalarMult(imagePlane.bottomRight, alpha)
            );

        Vector p = Vector.vectorAddition(
            Vector.scalarMult(t, (1-beta)), 
            Vector.scalarMult(b, beta)
            );

        return p;
    }
}

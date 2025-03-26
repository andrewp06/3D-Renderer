package code.renderer;

public class ImagePlane {
    Vector topLeft;
    Vector topRight;
    Vector bottomLeft;
    Vector bottomRight;

    public ImagePlane(){
        topRight = new Vector(1, (float).75, 0);
        bottomRight = new Vector(1, (float)-.75, 0);
        topLeft = new Vector(-1, (float).75, 0);
        bottomLeft = new Vector(-1, (float)-.75, 0);
    }

    @Override
    public String toString() {
        return topLeft + "        " + topRight + "\n\n\n" + bottomLeft + "        " + bottomRight;
    }
}

package code.renderer;

public class Ray {
    Vector direction;
    Point origin;

    public Ray(Vector direction, Point origin){
        this.direction = direction;
        this.origin = origin;
    }

    public static Ray makeRay(Vector p, Vector c){
        Vector direction = Vector.vectorSubtration(p, c);
        Point origin = new Point(p);
        return new Ray(direction, origin);
    }
}

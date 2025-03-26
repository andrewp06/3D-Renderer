package code.renderer;

public class Ray {
    Vector direction;
    Vector origin;

    public Ray(Vector direction, Vector origin){
        this.direction = direction;
        this.origin = origin;
    }

    public static Ray makeRay(Vector p, Vector c){
        Vector direction = Vector.vectorSubtration(p, c);
        Vector origin = c;
        return new Ray(direction, origin);
    }
}

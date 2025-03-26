package code.renderer;

public class Sphere implements Shape{
    float radius;
    Vector center;

    public Sphere(float radius, Vector center){
        this.radius = radius;
        this.center = center;
    }

    @Override
    public String toString() {
        return "|R="+radius+"  Center="+center+"|";
    }
}

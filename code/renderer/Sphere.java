package code.renderer;

public class Sphere implements Shape{
    float radius;
    Vector center;
    ImageColor color;

    public Sphere(float radius, Vector center, ImageColor color){
        this.radius = radius;
        this.center = center;
        this.color = color;
    }
    public Sphere(float radius, Vector center){
        this(radius,center,new ImageColor(255,0,0));
    }

    @Override
    public String toString() {
        return "|R="+radius+", Center="+center+", Color="+color+"|";
    }
    @Override
    public ImageColor getColor() {
        return color;
    }
}

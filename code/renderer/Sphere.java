package code.renderer;

public class Sphere implements Shape{
    float radius;
    Vector center;
    ImageColor color;
    Material material;

    public Sphere(float radius, Vector center, ImageColor color, Material material){
        this.radius = radius;
        this.center = center;
        this.color = color;
        this.material = material;
    }
    public Sphere(float radius, Vector center, ImageColor color){
        this(radius,center,color,new Material());
    }

    public Sphere(float radius, Vector center){
        this(radius,center,new ImageColor(255,0,0),new Material());
    }

    @Override
    public String toString() {
        return "|R="+radius+", Center="+center+", Color="+color+"|";
    }
    @Override
    public ImageColor getColor() {
        return color;
    }
    @Override
    public Material getMaterial() {
        return material;
    }
}

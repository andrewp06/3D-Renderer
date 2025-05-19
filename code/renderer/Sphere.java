package code.renderer;

public class Sphere{
    float radius;
    Vector center;
    Color color;
    Material material;

    public Sphere(float radius, Vector center, Color color, Material material){
        this.radius = radius;
        this.center = center;
        this.color = color;
        this.material = material;
    }
    public Sphere(float radius, Vector center, Color color){
        this(radius,center,color,new Material());
    }

    public Sphere(float radius, Vector center){
        this(radius,center,new Color(1,0,0),new Material());
    }
    public Sphere(){
        this(1f,new Vector(0,0,0));
    }

    @Override
    public String toString() {
        return "|R="+radius+", Center="+center+", Color="+color+"|";
    }

    public Color getColor() {
        return color;
    }

    public Material getMaterial() {
        return material;
    }
}

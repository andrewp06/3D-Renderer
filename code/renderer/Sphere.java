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
        return "Sphere - |R:"+radius+". Center:"+center+". Color:"+color+". Material:"+material+"|";
    }

    public Color getColor() {
        return color;
    }

    public Material getMaterial() {
        return material;
    }
    
    public Vector getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }

    public static Sphere fromString(String input){
        String[] sphereIn = input.split("\\|");
        sphereIn = sphereIn[1].split("\\. ");

        float radius = Float.parseFloat(sphereIn[0].split(":")[1]);
        Vector center = Vector.fromString(sphereIn[1].split(":")[1]);
        Color color = Color.fromString(sphereIn[2].split(":")[1]);
        Material material = Material.fromString(sphereIn[3].split(":")[1]);

        return new Sphere(radius,center,color,material);

    }

    public static void main(String[] args) {
        System.out.println(new Sphere());
        System.out.println(fromString(new Sphere()+""));
    }
}

package code.renderer;


public class Light {
    Vector location;
    Color diffuseIntensity;
    Color specularIntensity;

    public Light(Vector location, Color diffuseIntensity, Color specularIntensity){
        this.location = location;
        this.diffuseIntensity = diffuseIntensity;
        this.specularIntensity = specularIntensity;
    }

    public Light(){
        this(new Vector(0, 1, -1), new Color(.8f), new Color(.8f));
    }

    public static Light fromString(String string){
        String[] lightIn = string.split("\\|");

        lightIn = lightIn[1].split("\\. ");

        Vector location = Vector.fromString(lightIn[0].split(":")[1]);
        Color diffIntense = Color.fromString(lightIn[1].split(":")[1]);
        Color specIntense = Color.fromString(lightIn[2].split(":")[1]);



        return new Light(location,diffIntense,specIntense);
    }

    @Override
    public String toString() {
        return "Light - |location:"+ location+". Diffuse Intensity:"+diffuseIntensity+". Specular Intensity:"+ specularIntensity+"|";
    }

    public static void main(String[] args) {
        System.out.println(new Light());
        System.out.println(fromString(""+new Light()));

    }
}

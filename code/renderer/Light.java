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

    @Override
    public String toString() {
        return "Light - |location = "+ location+", Diffuse Intensity = "+diffuseIntensity+", Specular Intensity = "+ specularIntensity+"|";
    }
}

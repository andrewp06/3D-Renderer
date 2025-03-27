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
}

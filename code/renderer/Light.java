package code.renderer;

public class Light {
    Vector location;
    ImageColor diffuseIntensity;
    ImageColor specularIntensity;

    public Light(Vector location, ImageColor diffuseIntensity, ImageColor specularIntensity){
        this.location = location;
        this.diffuseIntensity = diffuseIntensity;
        this.specularIntensity = specularIntensity;
    }
}

package code.renderer;

public class Material {
    ImageColor ambiantConstant;
    float diffuseConstant;
    float specularConstant;
    float shininess;

    public Material(ImageColor ambiantConstant, float diffuseConstant, float specularConstant, float shininess){
        this.ambiantConstant = ambiantConstant;
        this.diffuseConstant = diffuseConstant;
        this.specularConstant = specularConstant;
        this.shininess = shininess;
    }

    public Material(){
        this(new ImageColor(50, 50, 50), .5f,.25f, shininess = .25f);
    }
}

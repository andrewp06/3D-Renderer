package code.renderer;

public class Material {
    ImageColor ambiantConstant;
    ImageColor diffuseConstant;
    ImageColor specularConstant;
    float shininess;

    public Material(ImageColor ambiantConstant, ImageColor diffuseConstant, ImageColor specularConstant, float shininess){
        this.ambiantConstant = ambiantConstant;
        this.diffuseConstant = diffuseConstant;
        this.specularConstant = specularConstant;
        this.shininess = shininess;
    }

    public Material(){
        this(new ImageColor(50, 50, 50), new ImageColor(50, 50, 50), new ImageColor(50, 50, 50), shininess = .25f);
    }
}

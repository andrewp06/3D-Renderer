package code.renderer;

public class Material {
    Color ambiantConstant;
    float diffuseConstant;
    float specularConstant;
    float shininess;

    public Material(Color ambiantConstant, float diffuseConstant, float specularConstant, float shininess){
        this.ambiantConstant = ambiantConstant;
        this.diffuseConstant = diffuseConstant;
        this.specularConstant = specularConstant;
        this.shininess = shininess;
    }

    public Material(){
        this(new Color(.25f, .25f, .25f), .5f,.25f, shininess = .25f);
    }
}

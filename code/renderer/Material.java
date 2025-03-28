package code.renderer;

public class Material {
    Color ambiantConstant;
    Color reflectivity;
    float diffuseConstant;
    float specularConstant;
    float shininess;
    

    public Material(Color ambiantConstant,Color reflectivity, float diffuseConstant, float specularConstant, float shininess){
        this.ambiantConstant = ambiantConstant;
        this.diffuseConstant = diffuseConstant;
        this.specularConstant = specularConstant;
        this.shininess = shininess;
        this.reflectivity=reflectivity;
    }

    public Material(){
        this(new Color(.1f), new Color(1f), .4f,.6f, shininess = 200f);
    }
}

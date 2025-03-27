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
        this(new Color(.01f,.01f,.02f), new Color(.05f), .6f,.8f, shininess = 10f);
    }
}

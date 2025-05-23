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
        this(new Color(.1f), new Color(.4f), .4f,.2f, 20);
    }

    @Override
    public String toString() {
        return "{Ambient Constant = "+ambiantConstant+", Reflectivity = "+reflectivity+", Diffuse Constant = "+diffuseConstant+", Specular Constant = "+ specularConstant+", Shininess = "+ shininess+"}";
    }
}

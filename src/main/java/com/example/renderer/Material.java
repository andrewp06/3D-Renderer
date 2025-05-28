package com.example.renderer;


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
        return "{Ambient Constant~"+ambiantConstant+"/ Reflectivity~"+reflectivity+"/ Diffuse Constant~"+diffuseConstant+"/ Specular Constant~"+ specularConstant+"/ Shininess~"+ shininess+"}";
    }

    public static Material fromString(String input){
        String[] materialIn = input.split("\\{");
        materialIn = materialIn[1].split("\\}");
        materialIn = materialIn[0].split("/ ");

        Color ambientConstant = Color.fromString(materialIn[0].split("~")[1]);
        Color reflectivity = Color.fromString(materialIn[1].split("~")[1]);
        float diffuseConstant = Float.parseFloat(materialIn[2].split("~")[1]);
        float specularConstant = Float.parseFloat(materialIn[3].split("~")[1]);
        float shininess = Float.parseFloat(materialIn[4].split("~")[1]);

        return new Material(ambientConstant,reflectivity,diffuseConstant,specularConstant,shininess);
    }

    public static void main(String[] args) {
        System.out.println(new Material());
        System.out.println(fromString(new Material()+""));
    }
}

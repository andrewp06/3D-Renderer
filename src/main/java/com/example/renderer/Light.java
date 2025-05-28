package com.example.renderer;


public class Light {
    Vector location;
    Color diffuseIntensity;
    Color specularIntensity;
    String name;

    public Light(Vector location, Color diffuseIntensity, Color specularIntensity, String name){
        this.location = location;
        this.diffuseIntensity = diffuseIntensity;
        this.specularIntensity = specularIntensity;
        this.name = name;
    }

    public Light(Vector location, Color diffuseIntensity, Color specularIntensity){
        this(location,diffuseIntensity,specularIntensity,"Light");
    }

    public Light(){
        this(new Vector(0, 1, -1), new Color(.8f), new Color(.8f));
    }

    public static Light fromString(String string){
        String[] lightIn = string.split("\\|");

        lightIn = lightIn[1].split("\\. ");

        Vector location = Vector.fromString(lightIn[0].split(":")[1]);
        Color diffIntense = Color.fromString(lightIn[1].split(":")[1]);
        Color specIntense = Color.fromString(lightIn[2].split(":")[1]);
        String name = lightIn[3].split(":")[1];



        return new Light(location,diffIntense,specIntense,name);
    }

    @Override
    public String toString() {
        return "Light - |location:"+ location+". Diffuse Intensity:"+diffuseIntensity+". Specular Intensity:"+ specularIntensity+". Name:"+name+"|";
    }

    public static void main(String[] args) {
        System.out.println(new Light());
        System.out.println(fromString(""+new Light()));

    }
}

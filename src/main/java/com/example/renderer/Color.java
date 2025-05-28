package com.example.renderer;


import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value

public class Color {
    @NonFinal
    @Setter
    float r;
    @NonFinal
    @Setter
    float g;
    @NonFinal
    @Setter
    float b;

    public Color(float all){
        r=all;
        g=all;
        b=all;
    }

    public Color(float r, float g, float b){
        this.r = r;
        this.g = g;
        this.b = b;
    }


    public static Color fromString(String input){
        String[] colorStrings = input.split("\\(");
        colorStrings = colorStrings[1].split("\\)");
        colorStrings = colorStrings[0].split(", ");

        float r = Float.parseFloat(colorStrings[0].split("=")[1]);
        float g = Float.parseFloat(colorStrings[1].split("=")[1]);
        float b = Float.parseFloat(colorStrings[2].split("=")[1]);

        return new Color(r,g,b);

    }

    public static void main(String[] args) {
        System.out.println(new Color(1,.5f,.2f));
        System.out.println(fromString(new Color(1,.5f,.2f)+""));

    }
}

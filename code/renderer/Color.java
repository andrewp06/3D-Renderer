package code.renderer;

import lombok.Value;

@Value
public class Color {
    float r;
    float g;
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
}

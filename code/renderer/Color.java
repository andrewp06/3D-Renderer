package code.renderer;


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

    
}

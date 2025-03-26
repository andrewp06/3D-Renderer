package code.renderer;

import java.util.HashSet;
import java.util.Set;

public class Screen {
    
    public static Set<Vector> makePlane(){
        Set<Vector> vectors = new HashSet<>();
        vectors.add(new Vector(1, (float).75, 0));
        vectors.add(new Vector(1, (float)-.75, 0));
        vectors.add(new Vector(-1, (float).75, 0));
        vectors.add(new Vector(-1, (float)-.75, 0));
        return vectors;
    }

    public static Vector getCamera(){
        return new Vector(0, 0, -1);
    }

    public static void main(String[] args) {
        Set<Vector> plane = makePlane();
        System.out.println(plane.toString());
    }
}

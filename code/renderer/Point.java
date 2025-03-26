package code.renderer;

public class Point {
    float x;
    float y;
    float z;

    public Point(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(Vector vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
    }
}

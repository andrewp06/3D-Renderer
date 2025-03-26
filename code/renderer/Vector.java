package code.renderer;

public class Vector {
    float x;
    float y;
    float z;

    public Vector(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector scalarMult(Vector vector, float scalar){
        return new Vector(vector.x*scalar, vector.y*scalar, vector.z*scalar);
    }

    public static Vector vectorAddition(Vector a, Vector b){
        return new Vector(a.x+b.x, a.y+b.y, a.z+b.z);
    }

    public static Vector vectorSubtration(Vector a, Vector b){
        return new Vector(a.x-b.x, a.y-b.y, a.z-b.z);
    }

    public static float dotProduct(Vector a, Vector b){
        return (a.x*b.x)+(a.y*b.y)+(a.z*b.z);
    }


    @Override
    public String toString() {
        return "<"+x+", "+y+", "+z+">";
    }
}

package com.example.renderer;

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

    public static float magnitude(Vector a){
        return (float)Math.sqrt(Math.pow(a.x,2)+Math.pow(a.y,2)+Math.pow(a.z,2));
    }

    public static Vector fromString(String input){
        String[] vectorIn = input.split("<");
        vectorIn = vectorIn[1].split(">");
        vectorIn = vectorIn[0].split(", ");
        float x = Float.parseFloat(vectorIn[0]);
        float y = Float.parseFloat(vectorIn[1]);
        float z = Float.parseFloat(vectorIn[2]);

        return new Vector(x, y, z);
    }


    @Override
    public String toString() {
        return "<"+x+", "+y+", "+z+">";
    }


    public static void main(String[] args) {
        System.out.println(new Vector(1, 2.2f, 0));
        System.out.println(fromString(new Vector(1, 2.2f, 0)+""));

    }
}

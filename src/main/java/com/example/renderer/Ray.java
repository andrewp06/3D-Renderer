package com.example.renderer;

public class Ray {
    Vector direction;
    Vector origin;

    public Ray(Vector direction, Vector origin){
        this.direction = direction;
        this.origin = origin;
    }
}

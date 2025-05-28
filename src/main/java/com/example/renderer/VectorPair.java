package com.example.renderer;

public class VectorPair{
    Vector unitNormal;
    Vector pointOfIntersection;

    public VectorPair(Vector unitNormal, Vector pointOfIntersection){
        this.unitNormal=unitNormal;
        this.pointOfIntersection=pointOfIntersection;
    }
}
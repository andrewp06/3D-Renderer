package code.renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Calculate {

    private static Vector createRayPoint(int xPixel, int yPixel, Screen screen){
        Image image  = screen.image;
        ImagePlane imagePlane = screen.imagePlane;
        float alpha = (float) xPixel / image.width;
        float beta = (float) yPixel / image.height;
        Vector t = Vector.vectorAddition(
            Vector.scalarMult(imagePlane.topLeft, (1-alpha)), 
            Vector.scalarMult(imagePlane.topRight, alpha)
            );

        Vector b = Vector.vectorAddition(
            Vector.scalarMult(imagePlane.bottomLeft, (1-alpha)), 
            Vector.scalarMult(imagePlane.bottomRight, alpha)
            );

        Vector p = Vector.vectorAddition(
            Vector.scalarMult(t, (1-beta)), 
            Vector.scalarMult(b, beta)
            );

        return p;
    }

    public static Ray createRay(int xPixel, int yPixel, Screen screen){
        Vector p = createRayPoint(xPixel, yPixel, screen);
        Vector c = screen.getCamera();
        return new Ray(Vector.vectorSubtration(p, c),p);
    }

    public static float intersectionSphere(Sphere sphere, Ray ray){
        float quadA = (float)Math.pow(Vector.magnitude(ray.direction), 2);
        Vector cPrime = Vector.vectorSubtration(ray.origin, sphere.center);
        float quadB = 2*Vector.dotProduct(cPrime, ray.direction);
        float quadC = (float)Math.pow(Vector.magnitude(cPrime), 2)-(float)Math.pow(sphere.radius, 2);
        float discriminant = (float)Math.pow(quadB,2)-(4*quadA*quadC);
        if (discriminant<0){
            return 0;
        }
        float t1 = ((-1*quadB)-(float)Math.sqrt(discriminant))/(2*quadA);
        float t2 = ((-1*quadB)+(float)Math.sqrt(discriminant))/(2*quadA);

        if (t1>0&&t2>0){
            if (t1>t2){
                return t2;
            }
            return t1;
        }
        if (t1>0){
            return t1;
        }
        if (t2>0){
            return t2;
        }
        return 0;
    }

    public static ImageColor shapeInFront(List<Shape> shapes, Ray ray){
        List<Float> tValues = new ArrayList<>();
        for(Shape shape:shapes){
            if(shape instanceof Sphere){
                Sphere sphere = (Sphere)shape;
                tValues.add(intersectionSphere(sphere, ray));
            }
        }
        float smallest = Collections.min(tValues);
        Shape closestShape = shapes.get(tValues.indexOf(smallest));

        return closestShape.getColor();
    }
}

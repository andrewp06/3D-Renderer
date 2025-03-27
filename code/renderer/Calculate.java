package code.renderer;

import java.util.ArrayList;
import java.util.List;

public class Calculate {


    /* 
     * @params xPixel an integer representing the x possiiton of the pixel being examined
     * @params yPixel an integer representing the y possiiton of the pixel being examined
     * @params screen an instance of the Screen class that hold most of the other information needed like the image, imagePlane, and camera
     * 
     * helper function
     * uses linear and linear interpolation to find the vector position of the pixel relative to the imagePlane veticies
     * 
     * @return the vector at the pixel
     */
    private static Vector createRayPoint(int xPixel, int yPixel, Screen screen){
        Image image  = screen.image;
        ImagePlane imagePlane = screen.imagePlane;
        float alpha = (float) xPixel / image.width;
        float beta = (float) yPixel / image.height;

        //linear interpolation of the pixel across the top 2 verticies
        Vector t = Vector.vectorAddition(
            Vector.scalarMult(imagePlane.topLeft, (1-alpha)), 
            Vector.scalarMult(imagePlane.topRight, alpha)
        );

        //linear interpolation of the pixel across the bottom 2 verticies
        Vector b = Vector.vectorAddition(
            Vector.scalarMult(imagePlane.bottomLeft, (1-alpha)), 
            Vector.scalarMult(imagePlane.bottomRight, alpha)
        );

        //bilinear interpolation of the pixel across vector b and t to make the vector p at the pixel
        Vector p = Vector.vectorAddition(
            Vector.scalarMult(t, (1-beta)), 
            Vector.scalarMult(b, beta)
        );

        return p;
    }

    /*
     * @params xPixel an integer representing the x possiiton of the pixel being examined
     * @params yPixel an integer representing the y possiiton of the pixel being examined
     * @params screen an instance of the Screen class that hold most of the other information needed like the image, imagePlane, and camera 
     * 
     * uses the creatRayPoint helper function to create a vector at the pixel
     * creates and ray with origin being the vector at the pixel and the direction being the vector subtraction of that point and the camera
     * 
     * @return the computed ray
     */
    public static Ray createRay(int xPixel, int yPixel, Screen screen){
        Vector p = createRayPoint(xPixel, yPixel, screen);
        Vector c = screen.getCamera();
        return new Ray(Vector.vectorSubtration(p, c),p);
    }


    /*
     * @params sphere a sphere that could be in view
     * @params ray a ray of a pixel relative to the camera
     * 
     * uses the quadratic equation to find at what points the shpere is intersected by the ray
     * the smallest positive value for t is returned
     * 
     * @return a value of t
     */
    public static float intersectionSphere(Sphere sphere, Ray ray){
        float quadA = (float)Math.pow(Vector.magnitude(ray.direction), 2);
        Vector cPrime = Vector.vectorSubtration(ray.origin, sphere.center);
        float quadB = 2*Vector.dotProduct(cPrime, ray.direction);
        float quadC = (float)Math.pow(Vector.magnitude(cPrime), 2)-(float)Math.pow(sphere.radius, 2);
        float discriminant = (float)Math.pow(quadB,2)-(4*quadA*quadC);

        //check if the discriminate would be less than zero. if so then the objects arent in view and copmuting the number would bring up errors
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

    /*
     * @params List<Shape> shapes a list of shapes that could be in view
     * @params ray a ray of a pixel relative to the camera
     * 
     * iterates through all the possible shapes and determins which is closest ot the camera that intersects with the ray.
     * if there is no intersection the return the color black
     * if there is intersection then return the color of the closest object
     * 
     * @returns color to set the pixel to based on the provided information
     */
    public static ImageColor shapeInFront(List<Shape> shapes, Ray ray){
        List<Float> tValues = new ArrayList<>();
        for(Shape shape:shapes){
            if(shape instanceof Sphere){
                Sphere sphere = (Sphere)shape;
                tValues.add(intersectionSphere(sphere, ray));
            }
        }
        float smallest=0;
        for(float tValue: tValues){
            if (smallest==0&&tValue>0){
                smallest = tValue;
            }else if (tValue>0&&tValue<smallest){
                smallest = tValue;
            }
        }
        if (smallest==0){
            return new ImageColor(0, 0, 0);
        }
        Shape closestShape = shapes.get(tValues.indexOf(smallest));

        return closestShape.getColor();
    }
}

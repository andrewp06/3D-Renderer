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
    private static Vector createRayPoint(float xPixel, float yPixel, Screen screen){
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

    public static Ray createRayPoint3SubDivisions(int xPixel, int yPixel, Screen screen, int subX, int subY, int n){

        Image image  = screen.image;
        ImagePlane imagePlane = screen.imagePlane;
        float deltaAlpha = 1/(n*image.width);
        float deltaBeta = 1/(n*image.height);
        float alpha = ((float) xPixel + (subX+.5f)*deltaAlpha )/ image.width;
        float beta = ((float) yPixel + (subY+.5f)*deltaBeta)/ image.height;

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

        return new Ray(Vector.vectorSubtration(p, screen.getCamera()),p);
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
    public static Ray createRay(float xPixel, float yPixel, Screen screen){
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
            return -1;
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

    /**
     * @param ray a ray of a pixel relative to the camera
     * @param screen the screen that holds all of the infromation we need
     * @param recursionDepth the amount of times this funcitonn wil be recursivly called
     * 
     * iterates through all the possible shapes and determins which is closest ot the camera that intersects with the ray.
     * if there is no intersection the return the color black
     * if there is intersection then return the color of the closest object.
     * 
     * Recursivle calls the function a nunmber a times specified in the parameter to create the reflections of other objects
     * 
     * @return color to set the pixel to based on the provided information
     */
    public static Color shapeInFront( Ray ray, Screen screen, int recursionDepth){

        //finds the closest shape and gets its color value
        List<Float> tValues = new ArrayList<>();
        List<Sphere> spheres = screen.spheres;
        for(Sphere sphere:spheres){ 
            tValues.add(intersectionSphere(sphere, ray));
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
            return new Color(0);
        }
        Sphere closestShape = spheres.get(tValues.indexOf(smallest));
        Color initialColor = closestShape.getColor();

        //applies color values from the lights to the initial values
        VectorPair vectors = surfaceNormal(smallest, ray, closestShape);
        Vector unitNormal = vectors.unitNormal;
        Vector pointOfIntersection = vectors.pointOfIntersection;
        Color ambientTerm = calculateAmbientTerm(closestShape, screen);
        Color diffuseTerm = calculateDiffuseTerm(closestShape, screen, unitNormal, pointOfIntersection);
        Color finalColor = addColors(addColors(ambientTerm, diffuseTerm), initialColor);

        //create new rays from the point of intersection to the next closest object
        Vector vVector = ray.direction;
        vVector = Vector.scalarMult(vVector, -1 / Vector.magnitude(vVector));

        Color reflect = closestShape.material.reflectivity;
        //recersivly call this function with a new ray to find color of reflection
        if (recursionDepth > 0) {
            // Offset point to avoid self-intersection
            Vector reflectionOrigin = Vector.vectorAddition(pointOfIntersection, Vector.scalarMult(unitNormal, 1e-4f));
        
            // Ensure correct reflection calculation
            Vector reflectance = Vector.scalarMult(unitNormal, 2 * Vector.dotProduct(unitNormal, vVector));
            reflectance = Vector.vectorSubtration(reflectance, vVector);
            reflectance = Vector.scalarMult(reflectance, 1 / Vector.magnitude(reflectance));
        
            // Recursive reflection
            Color postReflection = shapeInFront(new Ray(reflectance, reflectionOrigin), screen, recursionDepth - 1);
            postReflection = multColors(reflect, postReflection);
            finalColor = addColors(finalColor, postReflection);
        }
        

        return clamp(finalColor);
    }


    /**
     * @param t a float for the distance the object is along the ray
     * @param ray a ray from the pixel to the camera
     * @param sphere a sphere the ray is intersecting at distance t
     * 
     * uses vector addition, subtration, and normalization to find the point of intersection and the unit normal vector at that point
     * 
     * @return a pair of vectors being the unit normal vector and the p[oint of intersection
     */
    private static VectorPair surfaceNormal(float t, Ray ray, Sphere sphere){
        Vector pointOfIntersection = Vector.vectorAddition(ray.origin, Vector.scalarMult(ray.direction, t));
        Vector normalVector = Vector.vectorSubtration(pointOfIntersection, sphere.center);
        Vector unitNormal = Vector.scalarMult(normalVector, 1/Vector.magnitude(normalVector));
        return new VectorPair(unitNormal, pointOfIntersection);
    }

    /**
     * @param sphere the shpere that the pixel sees
     * @param screen the screen holding all of the information needed
     * 
     * use the ambient constant on the shpere materal and multiply it by the ambient light of the screen
     * 
     * @return the ambient term for the light
     */
    private static Color calculateAmbientTerm(Sphere sphere, Screen screen){
        return multColors(sphere.material.ambiantConstant, screen.ambientLight);
    }

    /**
     * @param sphere the sphere that the pixel sees
     * @param screen the screen that contains all the information baoput the scene
     * @param unitNormal a normal vector on the shpere at the point of intersection
     * @param pointOfIntersection a vector to the point that the pixel's ray intersects with the shpere
     * 
     * iterates through all of the lights in the scene
     * if the light is able to see the object (not in a shadow) then it calculates the diffuse term and the specular term for the light
     * 
     * @return the color value of the specular and diffuse copmonents of the lights on the object
     */
    private static Color  calculateDiffuseTerm(Sphere sphere, Screen screen, Vector unitNormal, Vector pointOfIntersection){
        Color totalDiffuseAndSpecularComponent = new Color(0);
        for(Light light : screen.lights){

            //checking if the light contacts the shpere at the point
            boolean inShadow = false;
            Ray shadowRay = shadowRay(light.location, pointOfIntersection);
            for (Sphere otherShpere:screen.spheres){
                if(sphere!=otherShpere){

                    float tValue = intersectionSphere(otherShpere, shadowRay);
                    if (tValue>0&&tValue<1){
                        inShadow = true;
                    }

                }
            }


            if(!inShadow){
                //calculates the diffuse component
                Vector lightVector = Vector.vectorSubtration(light.location, pointOfIntersection);
                Vector untiLightVector = Vector.scalarMult(lightVector, 1/Vector.magnitude(lightVector));
                float dotProduct = Vector.dotProduct(unitNormal, untiLightVector);
                if(dotProduct>=0){
                    Color diffuseComponent = scalarColorMult(scalarColorMult(light.diffuseIntensity, dotProduct),sphere.material.diffuseConstant);                    
                    //combines the diffuse and specular components
                    totalDiffuseAndSpecularComponent = addColors(diffuseComponent, totalDiffuseAndSpecularComponent);
                    Color specularComponent = calculateSpecularTerm(sphere, screen, unitNormal, pointOfIntersection, untiLightVector, light);
                    totalDiffuseAndSpecularComponent = addColors(addColors(specularComponent, totalDiffuseAndSpecularComponent),specularComponent);
                }
            }
        }

        return totalDiffuseAndSpecularComponent;
    }

    /**
     * @param sphere the shpere that the pixel sees
     * @param screen the screen that holds all of the informaion about the scene
     * @param unitNormal the unit normal vector at the poiunt of intersection
     * @param pointOfIntersection vector to the point where the piuxel sees the sphere
     * @param lightVector unit vector of the direction from the point of intersection to the light
     * @param light a light that is beign examined
     * 
     * calculate the specular term for the light on the shpere at the  point of intersection
     * 
     * @return the specular term
     */
    private static Color calculateSpecularTerm(Sphere sphere, Screen screen, Vector unitNormal, Vector pointOfIntersection, Vector lightVector, Light light){

        //create vectors for calculation
        Vector reflectance = Vector.vectorSubtration(Vector.scalarMult(Vector.scalarMult(unitNormal,Vector.dotProduct(unitNormal, lightVector)), 2),lightVector);
        reflectance = Vector.scalarMult(reflectance, 1 / Vector.magnitude(reflectance));
        Vector viewVector = Vector.vectorSubtration(screen.camera, pointOfIntersection);
        viewVector = Vector.scalarMult(viewVector, 1 / Vector.magnitude(viewVector));

        //calculate values for specular constant
        float dotProduct = Math.max(Vector.dotProduct(reflectance, viewVector), 0);
        float specularColorScalar = (float)Math.pow(dotProduct,sphere.material.shininess)*sphere.material.specularConstant;
        return scalarColorMult(light.specularIntensity, specularColorScalar);
    }


    /**
     * @param color a representation of color with float values
     * 
     * convert from color to colorImage
     * 
     * @return a representation of color with values [0,255]
     */
    public static ImageColor colorToImageColor(Color color){
        return new ImageColor(
                    Math.round(color.getR()*255),
                    Math.round(color.getG()*255), 
                    Math.round(color.getB()*255)
        );
    }

    /**
     * @param c1 color
     * @param c2 color
     * 
     * add the rgb values of the colors together
     * 
     * @return combined color
     */
    public static Color addColors(Color c1, Color c2){
        return new Color(c1.getR()+c2.getR(), c1.getG()+c2.getG(), c1.getB()+c2.getB());
    }

    /**
     * @param c1 color
     * @param c2 color
     * 
     * multiply the values of rgb
     * 
     * @return product color
     */
    private static Color multColors(Color c1, Color c2){
        return new Color(c1.getR()*c2.getR(), c1.getG()*c2.getG(), c1.getB()*c2.getB());
    }

    /**
     * @param c color to be clamped
     * 
     * ensures that the values for rgb in the colors fall in the range [0,1]
     * 
     * @return adjusted color
     */
    private static Color clamp(Color c) {
        return new Color(
            Math.max(0, Math.min(c.getR(), 1)),
            Math.max(0, Math.min(c.getG(), 1)),
            Math.max(0, Math.min(c.getB(), 1))
        );
    }

    /**
     * @param light light vector
     * @param pointOfIntersection point of intersection
     * 
     * create shadow ray from light and point of intersection
     * 
     * @return new shadow ray
     */
    public static Ray shadowRay(Vector light, Vector pointOfIntersection){
        Vector bias = Vector.scalarMult(pointOfIntersection, 1e-4f); 
        return new Ray(Vector.vectorSubtration(light, Vector.vectorAddition(bias, pointOfIntersection)),pointOfIntersection);
    }

    public static Color scalarColorMult(Color c, float scalar){
        return new Color(c.getR()*scalar,c.getG()*scalar,c.getB()*scalar);
    }
    
    
}

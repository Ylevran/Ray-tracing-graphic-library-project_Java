package elements;


import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Interface for common actions of light sources
 *
 * @author Dan Zilberstein
 */
public interface LightSource {


    // ***************** Getters/Setters ********************** //

    /**
     * Get light source intensity as it reaches a point I<sub>P</sub>
     *
     * @param p the lighted point
     * @return intensity I<sub>P</sub>
     */
    public Color getIntensity(Point3D p);

    /**
     * Get normalized vector in the direction from light source
     * towards the lighted point
     *
     * @param p the lighted point
     * @return light to point vector
     */
    public Vector getL(Point3D p);


    /**
     * Get distance from light to point
     *
     * @param point
     * @return distance
     */
    double getDistance(Point3D point);

}

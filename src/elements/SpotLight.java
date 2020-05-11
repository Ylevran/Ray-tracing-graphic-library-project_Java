package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 *
 * @author Yossef Levran, ID: 332484609, Email Address: yossef.levran@gmail.com
 * @author Shmuel Segal, ID: 052970464, Email address: shmuelse@gmail.com
 */
public class SpotLight extends PointLight {

    private Vector _direction;

    //***************** Constructors **********************//

    /**
     * Constructor of a spot light
     *
     * @param colorIntensity
     * @param position
     *          - Position of the light (Point3D)
     * @param direction
     *          - direction of lighting (Normalized Vector)
     * @param kC
     *          - Coefficient of Quadratic attenuation of the light in the distance
     * @param kL
     *          - Coefficient of linear weakening of the light in the distance
     * @param kQ
     *          - Coefficient of exponential weakening of the light at a distance
     */
    public SpotLight(Color colorIntensity, Point3D position, Vector direction, double kC, double kL, double kQ) {
        super(colorIntensity,position,kC,kL,kQ);;
        this._direction = new Vector(direction).normalized();
    }

    // ***************** Getters/Setters ********************** //

}

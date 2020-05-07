package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface Geometry is the basic interface for all geometric objects
 * who are implementing getNormal method.
  */
public abstract class Geometry implements Intersectable {

     protected Color _emmission;

    // ***************** Constructors ********************** //

    /**
     * Constructor
     *
     * @param _emmission
     */
    public Geometry(Color _emmission) {
        this._emmission = _emmission;
    }

    /**
     * Default constructor
     *
     */
    public Geometry() {
        this._emmission = Color.BLACK;
    }

// ***************** Getters/Setters ********************** //


    /**
     * Getter
     *
     * @return - The _emission
     */
    public Color getEmmission() {
        return _emmission;
    }

    /**
     * get Normal Vector
     * @param p point3D
     * @return normal vector
     */
    public abstract Vector getNormal(Point3D p);
}

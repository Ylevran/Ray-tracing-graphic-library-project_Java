package geometries;

import elements.Material;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface Geometry is the basic interface for all geometric objects
 * who are implementing getNormal method.
  */
public abstract class Geometry implements Intersectable {

     protected Color _emission;
     protected Material _material;
     

    // ***************** Constructors ********************** //

    /**
     * @param emission
     * @param material
     */
    public Geometry(Color emission, Material material){
        this._emission = emission;
        this._material = material;
    }


    /**
     * Constructor
     *
     * @param _emission
     */
    public Geometry(Color _emission) {

        this(_emission , Material.DEFAULT);
    }

    /**
     * Default constructor
     *
     */
    public Geometry() {
        this._emission = Color.BLACK;
        this._material = Material.DEFAULT;
    }

// ***************** Getters/Setters ********************** //

    /**
     * Getter
     * @return - The _material
     */
    public Material getMaterial() {
        return _material;
    }

    /**
     * Getter
     *
     * @return - The _emission
     */
    public Color getEmissionLight() {
        return _emission;
    }

    /**
     * get Normal Vector
     * @param p point3D
     * @return normal vector
     */
    public abstract Vector getNormal(Point3D p);


    /**
     * @param emission
     */
    public void setEmission(Color emission){
        this._emission = emission;
    }


}

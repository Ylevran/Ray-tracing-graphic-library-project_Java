package geometries;

/**
 * @author Yossef
 * RadialGeometry is an abstract class that defines
 * all radial geometries.
 */
public abstract class RadialGeometry implements Geometry{

    double  _radius;

    /**
     *
     * @param _radius
     */
    public RadialGeometry(double _radius) {
        this._radius = _radius;
    }
    public RadialGeometry(RadialGeometry other){
        this._radius= other._radius;
    }
    public double get_radius() {
        return _radius;
    }
}

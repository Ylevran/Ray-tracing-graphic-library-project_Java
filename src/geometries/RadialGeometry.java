package geometries;

/**
 * RadialGeometry is an abstract class that defines
 * all radial geometries.
 */
public abstract class RadialGeometry extends Geometry{

    double  _radius;

    /**
     * RadialGeometry Constructor receiving radius
     * @param _radius
     */
    public RadialGeometry(double _radius) {
        this._radius = _radius;
    }

    /**
     * RadialGeometry Copy Constructor
     * @param other
     */
    public RadialGeometry(RadialGeometry other){
        this._radius= other._radius;
    }

    /**
     * Radius Getter
     * @return _radius
     */
    public double get_radius() {
        return _radius;
    }
}

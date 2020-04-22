package primitives;

import static primitives.Util.*;

/**
 * Ray class is the basic class representing a ray in a
 * 3D system
 */
public class Ray {
    // Point of origin
    private Point3D _POO;

    // Ray direction
    private Vector _direction;

    /**
     * Copy Constructor
     * @param ray
     */
    public Ray(Ray ray){
        this._POO = ray.getPOO();
        this._direction = ray.getDirection();
    }

    /**
     * Ray Constructor receiving origin point and direction vector
     * @param poo origin point
     * @param direction direction vector
     */
    public Ray(Point3D poo, Vector direction){
        this._POO = new Point3D(poo._x._coord, poo._y._coord, poo._z._coord);
        this._direction = new Vector (direction);
        this._direction.normalize();
    }

    /**
     * vector direction getter
     * @return _direction
     */
    public Vector  getDirection() { return new Vector(_direction); }

    /**
     * origin point getter
     * @return _POO
     */
    public Point3D getPOO() { return new Point3D(_POO._x._coord, _POO._y._coord, _POO._z._coord);}

    /**
     * @author  Dan Zilberstein
     * @param length
     * @return new Point3D
     */
    public Point3D getTargetPoint(double length) {
        return isZero(length ) ? _POO : _POO.add(_direction.scale(length));
    }
}

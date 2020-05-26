package primitives;

import static primitives.Util.*;

/**
 * Ray class is the basic class representing a ray in a
 * 3D system
 */
public class Ray {
    // Point of origin
    private Point3D _point;

    // Ray direction
    private Vector _direction;

    // ***************** Constructors ********************** //

    /**
     * Ray Constructor receiving origin point and direction vector
     * @param startingPoint origin point
     * @param direction direction vector
     */
    public Ray(Point3D startingPoint, Vector direction){
        this._point = new Point3D(startingPoint);
        this._direction = new Vector (direction.normalize());
    }

    /**
     * Copy Constructor
     * @param ray
     */
    public Ray(Ray ray){
        this._point = ray.getPoint();
        this._direction = ray.getDirection();
    }


    // ***************** Getters/Setters ********************** //


    /**
     * Getter
     *
     * @return Vector -the direction vector
     */
    public Vector  getDirection() { return new Vector(_direction); }

    /**
     * Getter
     *
     * @return
     */
    public Point3D getPoint() { return new Point3D(_point);}

    /**
     * @author  Dan Zilberstein
     * @param length
     * @return new Point3D
     */
    public Point3D getTargetPoint(double length) {
        return isZero(length ) ? _point : _point.add(_direction.scale(length));
    }


    // ***************** Administration  ******************** //

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Ray))
            return false;
        Ray other = (Ray) obj;
        return _point.equals(other._point) && _direction.equals(other._direction);
    }

    /**
     * toString of Ray, uses the Point3D toString and Vector toString
     *
     * @return - the details of ray in format: "Ray= Point:(x,y,z) Vector:(x,y,z)
     */
    @Override
    public String toString() {
        return "Ray{" +
                "_POO=" + _point +
                ", _direction=" + _direction +
                '}';
    }
}

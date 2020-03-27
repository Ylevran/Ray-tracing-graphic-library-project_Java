package primitives;

import java.io.PipedOutputStream;
import java.util.Objects;

/**
 * Point3D class is the basic class representing a point in a
 * 3D system
 */
public class Point3D {

    Coordinate _x;
    Coordinate _y;
    Coordinate _z;


    public final static Point3D ZERO = new Point3D(0.0, 0.0, 0.0);

    /**
     * Point3D Constructor receiving three coordinates
     * @param _x coordinate on the X axis
     * @param _y coordinate on the Y axis
     * @param _z coordinate on the Z axis
     */
    public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
        this._x = _x;
        this._y = _y;
        this._z = _z;
    }

    /**
     * Point3D Constructor receiving three double params
     * @param _x _coord value on the X axis
     * @param _y _coord value on the Y axis
     * @param _z _coord value on the Z axis
     */
    public Point3D(double _x, double _y, double _z) {
        this(new Coordinate(_x), new Coordinate(_y), new Coordinate(_z));
    }

    /**
     * x coordinate getter
     * @return x coordinate
     */
    public Coordinate get_x() {
        return new Coordinate(_x);
    }

    /**
     * y coordinate getter
     * @return y coordinate
     */
    public Coordinate get_y() {
        return new Coordinate(_y);
    }

    /**
     * z coordinate getter
     * @return z coordinate
     */
    public Coordinate get_z() {
        return new Coordinate(_z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) &&
                _y.equals(point3D._y) &&
                _z.equals(point3D._z);
    }

    /**
     * Add vector to the Point3D
     * @param v added vector
     * @return new Point3D
     */
    public Point3D add(Vector v) {
        return new Point3D(this._x._coord + v._head._x._coord,
                this._y._coord + v._head._y._coord,
                this._z._coord + v._head._z._coord);
    }

    /**
     * Subtract Point3D from Point3D
     * @param p subtracted Point3D
     * @return Vector from p to this
     */
    public Vector subtract(Point3D p) {
        return new Vector(new Point3D(
                this._x._coord - p._x._coord,
                this._y._coord - p._y._coord,
                this._z._coord - p._z._coord));
    }

    /**
     * distance squared between this to another Point3D
     * @param p the another point
     * @return distance squared
     */
    public double distanceSquared (Point3D p){
        return (this._x._coord - p._x._coord) * (this._x._coord - p._x._coord)
                + (this._y._coord - p._y._coord) * (this._y._coord - p._y._coord)
                + (this._z._coord - p._z._coord) * (this._z._coord - p._z._coord);
    }

    /**
     * distance between this to another Point3D
     * @param p the another point
     * @return distance
     */
    public double distance (Point3D p){
        return Math.sqrt(distanceSquared(p));
    }

    @Override
    public String toString() {
        return "Point3D{" +
                "x = " + _x +
                ", y = " + _y +
                ", z =" + _z +
                '}';
    }
}

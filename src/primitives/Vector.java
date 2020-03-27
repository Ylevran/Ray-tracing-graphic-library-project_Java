package primitives;

import java.util.Objects;

/**
 * Vector class is the basic class representing a vector in a
 * 3D system
 */
public class Vector {

    Point3D _head;

    /**
     * Vector Constructor receiving Point3D
     * @param p Point 3D
     */
    public Vector(Point3D p) {
        if (p.equals(Point3D.ZERO)) {
            throw new IllegalArgumentException("Point3D(0.0,0.0,0.0) not valid for vector head");
        }
        this._head = new Point3D(p._x._coord, p._y._coord, p._z._coord);
    }

    /**
     * Copy Constructor
     * @param v Vector
     */
    public Vector(Vector v) {
        this(v._head);
    }

    /**
     * Vector Constructor receiving two 3D Points
     * @param p1 head
     * @param p2
     */
    public Vector(Point3D p1, Point3D p2) {
        this(p1.subtract(p2));
    }

    /**
     * Vector Constructor receiving three double params
     * @param x double value of x coordinate
     * @param y double value of y coordinate
     * @param z double value of z coordinate
     */
    public Vector(double x,double y, double z) {
        this(new Point3D(x,y,z));
    }

    /**
     * _head Getter
     * @return _head
     */
    public Point3D get_head() {
        return new Point3D(_head._x._coord, _head._y._coord, _head._z._coord);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    /**
     * Add Vector to this Vector
     * @param v added vector
     * @return the new Vector after adding
     */
    public Vector add(Vector v) {
        return new Vector(this._head.add(v));
    }

    /**
     * Subtract Vector from this vector
     * @param v subtracting vector
     * @return the new vector
     */
    public Vector subtract(Vector v){
        return this._head.subtract(v._head);
    }

    /**
     * Scaling this vector
     * @param f scale factor
     * @return the scaled vector
     */
    public Vector scale(double f){
        return new Vector(
                new Point3D(
                        new Coordinate(f * _head._x._coord),
                        new Coordinate(f * _head._y._coord),
                        new Coordinate(f * _head._z._coord)));
    }

    /**
     * Dot Product
     * @param v second vector
     * @return product
     */
    public double dotProduct(Vector v) {
        return this._head._x._coord * v._head._x._coord +
                this._head._y._coord * v._head._y._coord +
                this._head._z._coord * v._head._z._coord;
    }

    /**
     * Cross Product
     * @param v second vector
     * @return product
     */
    public Vector crossProduct(Vector v) {
        double w1 = this._head._y._coord * v._head._z._coord - this._head._z._coord * v._head._y._coord;
        double w2 = this._head._z._coord * v._head._x._coord - this._head._x._coord * v._head._z._coord;
        double w3 = this._head._x._coord * v._head._y._coord - this._head._y._coord * v._head._x._coord;
        return new Vector(new Point3D(w1, w2, w3));
    }

    /**
     * Squared vector length
     * @return squared length
     */
    public double lengthSquared() {
        double xx = this._head._x._coord * this._head._x._coord;
        double yy = this._head._y._coord * this._head._y._coord;
        double zz = this._head._z._coord * this._head._z._coord;

        return xx + yy + zz;
    }

    /**
     * vector length
     * @return length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Vector Normalisation
     * @return the same Vector after normalisation
     * @throws ArithmeticException if length = 0
     */
    public Vector normalize() {

        double x = this._head._x._coord;
        double y = this._head._y._coord;
        double z = this._head._z._coord;

        double length = this.length();

        if (length == 0)
            throw new ArithmeticException("divide by Zero");

        this._head._x = new Coordinate(x / length);
        this._head._y = new Coordinate(y / length);
        this._head._z = new Coordinate(z / length);

        return this;
    }

    /**
     * Normalised vector
     * @return new normalised Vector based on this Vector
     */
    public Vector normalized() {
        Vector vector = new Vector(this);
        vector.normalize();
        return vector;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "head = " + _head.toString() +
                '}';
    }
}

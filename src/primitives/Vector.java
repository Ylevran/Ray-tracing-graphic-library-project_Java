package primitives;

import java.util.Objects;

/**
 * @author Yossef
 */
public class Vector {
    Point3D _head;

    public Vector(Point3D p) {
        this._head = new Point3D(p._x._coord, p._y._coord, p._z._coord);
    }

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

    public double dotProduct(Vector v) {
        return this._head._x._coord * v._head._x._coord +
                this._head._y._coord * v._head._y._coord +
                this._head._z._coord * v._head._z._coord;
    }

    public Vector crossProduct(Vector v) {
        double w1 = this._head._y._coord * v._head._z._coord - this._head._z._coord * v._head._y._coord;
        double w2 = this._head._z._coord * v._head._x._coord - this._head._x._coord * v._head._z._coord;
        double w3 = this._head._x._coord * v._head._y._coord - this._head._y._coord * v._head._x._coord;
        return new Vector(new Point3D(w1, w2, w3));
    }
}

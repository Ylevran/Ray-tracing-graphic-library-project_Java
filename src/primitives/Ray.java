package primitives;

public class Ray {
    // Point of origin
    private Point3D _POO;

    // Ray direction
    private Vector _direction;

    public Ray(Ray ray){
        this._POO = ray.getPOO();
        this._direction = ray.getDirection();
    }

    public Ray(Point3D poo, Vector direction){
        this._POO = new Point3D(poo._x._coord, poo._y._coord, poo._z._coord);
        this._direction = new Vector (direction);
        this._direction.normalize();
    }

    public Vector  getDirection() { return new Vector(_direction); }
    public Point3D getPOO()       { return new Point3D(_POO._x._coord, _POO._y._coord, _POO._z._coord);}

}

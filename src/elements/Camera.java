package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import static primitives.Util.isZero;


public class Camera {

    Point3D _p0;
    Vector _vTo;
    Vector _vUp;
    Vector _vRight;

    // ***************** Constructors ********************** //


    public Camera(Point3D _p0, Vector _vTo, Vector _vUp) {

        //if the vectors are not orthogonal, throw exception.
        if (_vUp.dotProduct(_vTo) != 0)
            throw new IllegalArgumentException("the vector must be orthogonal");

        this._p0 = new Point3D(_p0);
        this._vTo = _vTo.normalized();
        this._vUp = _vUp.normalized();

        _vRight = this._vTo.crossProduct(this._vUp).normalize();

    }

    // ***************** Operations ******************** //

    public Ray constructRayThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWidth,
                                        double screenHeight) {

        if (isZero(screenDistance)) throw new IllegalArgumentException("distance cannot be 0");


        // Pc is the point in the middle of the screen (Pc = P0 + distance*Vto)
        Point3D Pc = _p0.add(_vTo.scale(screenDistance)); //image center

        double Ry = screenHeight / nY;
        double Rx = screenWidth / nX;

        double Yi = ((i - (nY-1) / 2d) * Ry + Ry / 2d);
        double Xj = ((j - (nX-1) / 2d) * Rx + Rx / 2d);

        Point3D pIJ = Pc;  // pIJ is the point on the middle of the given pixel

        if (!isZero(Xj)) pIJ = pIJ.add(_vRight.scale(Xj));
        if (!isZero(Yi)) pIJ = pIJ.add(_vUp.scale(-Yi));

        Vector vIJ = pIJ.subtract(_p0);
        return new Ray(_p0, vIJ);
    }

    // ***************** Getters/Setters ********************** //


    /**
     * @return Point3D (Position the center of the camera)
     */
    public Point3D get_p0() {
        return new Point3D(_p0);
    }

    /**
     * @return the vector _vTo
     */
    public Vector get_vTo() {
        return new Vector(_vTo);
    }

    /**
     * Getter
     *
     * @return the vector _vUp
     */
    public Vector get_vUp() {
        return new Vector(_vUp);
    }

    /**
     * Getter
     *
     * @return the vector _vRight
     */
    public Vector get_vRight() {
        return new Vector(_vRight);
    }


}


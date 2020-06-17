package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static primitives.Util.isZero;


/**
 *  Class to represent a camera
 *
 *  @author Yosef Levran & Shmuel Segal
 */
public class Camera {

    Point3D _p0;
    Vector _vTo;
    Vector _vUp;
    Vector _vRight;

    // number of rows and columns in one pixel for supersampling
    private static final int SUPERSAMPLING_NUM = 9;

    protected static Random random = new Random();

    // ***************** Constructors ********************** //


    /**
     *  Constructor of camera
     * @param _p0
     *            - Point3D: Position of center of the camera
     * @param _vTo
     *            - Vector: Direction vector to the middle of the screen
     * @param _vUp
     *            - Vector: Direction vector up
     */
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

    /**
     *
     * Construct ray through pixel
     *
     * @param nX
     *            - number of pixels in the screen width
     * @param nY
     *            - number of pixels in the screen height
     * @param j
     *             - "y" rate of the pixel
     * @param i
     *             - "x" rate of the pixel
     * @param screenDistance
     *            - distance from the camera to the screen
     * @param screenWidth
     *
     * @param screenHeight
     * @return  ray from camera to the middle of the given pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWidth,
                                        double screenHeight) {

        if (isZero(screenDistance)) throw new IllegalArgumentException("distance cannot be 0");

        // Pc is the point in the middle of the screen (Pc = P0 + distance*Vto)
        Point3D Pc = _p0.add(_vTo.scale(screenDistance)); //image center

        Point3D pIJ = getPixelCenter(Pc, nX, nY, j, i, screenWidth, screenHeight);

        Vector vIJ = pIJ.subtract(_p0);
        return new Ray(_p0, vIJ.normalize());
    }




    public List<Ray> constructBeamThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWidth,
                                               double screenHeight) {
        List<Ray> beam = new ArrayList<Ray>();

        if (isZero(screenDistance)) throw new IllegalArgumentException("distance cannot be 0");

        // Pc is the point in the middle of the screen (Pc = P0 + distance*Vto)
        Point3D Pc = _p0.add(_vTo.scale(screenDistance)); //image center

        Point3D pIJ = getPixelCenter(Pc, nX, nY, j, i, screenWidth, screenHeight);

        double Ry = screenHeight / nY; // pixel height
        double Rx = screenWidth / nX; // pixel width

        for (int row = 0; row < SUPERSAMPLING_NUM ; ++ row)
            for (int column = 0; column < SUPERSAMPLING_NUM ; ++column){
                // pIJS is the center of the subpixel
                Point3D pIJS = getPixelCenter(pIJ, SUPERSAMPLING_NUM, SUPERSAMPLING_NUM, column, row, Rx, Ry );

                // Jittered randomization
                double Sry = Ry / SUPERSAMPLING_NUM; // subpixel height
                double Srx = Rx / SUPERSAMPLING_NUM; // subpixel width
                double yRand = randomInRange(-Sry / 2 , Sry / 2);
                double xRand = randomInRange(-Srx / 2 , Srx / 2);
                if (!isZero(xRand)) pIJ = pIJ.add(_vRight.scale(xRand));
                if (!isZero(yRand)) pIJ = pIJ.add(_vUp.scale(yRand));

                // Create an Adding Ray to the beam
                Vector vIJS = pIJS.subtract(_p0);
                beam.add(new Ray(_p0, vIJS.normalize()));
            }
        return beam;
    }

    /**
     @param nX
      *            - number of pixels in the screen width
      * @param nY
     *            - number of pixels in the screen height
     * @param j
     *             - "y" rate of the pixel
     * @param i
     *             - "x" rate of the pixel
     * @param screenWidth
     *
     * @param screenHeight
     *
     * @return pixel middle
     */
    private Point3D getPixelCenter(Point3D center ,int nX, int nY, int j, int i, double screenWidth, double screenHeight) {

        double Ry = screenHeight / nY; // pixel height
        double Rx = screenWidth / nX; // pixel width

        double Yi = ((i - nY / 2d) * Ry + Ry / 2d);
        double Xj = ((j - nX / 2d) * Rx + Rx / 2d);

        Point3D pIJ = center;  // pIJ is the point on the middle of the given pixel

        if (!isZero(Xj)) pIJ = pIJ.add(_vRight.scale(Xj));
        if (!isZero(Yi)) pIJ = pIJ.add(_vUp.scale(-Yi));
        return pIJ;
    }

    /**
     * Generate double random number in range
     *
     * @param min value in range
     * @param max value in range
     * @return rand num
     */
    private static double randomInRange(double min, double max) {
        double range = max - min;
        double scaled = random.nextDouble() * range;
        double shifted = scaled + min;
        return shifted; // == (rand.nextDouble() * (max-min)) + min;
    }

    // ***************** Getters/Setters ********************** //


    /**
     * @return Point3D (Position the center of the camera)
     */
    public Point3D getP0() {
        return new Point3D(_p0);
    }

    /**
     * @return the vector _vTo
     */
    public Vector getVTo() {
        return new Vector(_vTo);
    }

    /**
     * Getter
     *
     * @return the vector _vUp
     */
    public Vector getVUp() {
        return new Vector(_vUp);
    }

    /**
     * Getter
     *
     * @return the vector _vRight
     */
    public Vector getVRight() {
        return new Vector(_vRight);
    }


}


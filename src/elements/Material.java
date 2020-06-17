package elements;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Yossef Levran, ID: 332484609, Email Address: yossef.levran@gmail.com
 *
 * @author Shmuel Segal, ID: 052970464, Email address: shmuelse@gmail.com
 */
public class Material {

    private final double _kD;
    private final double _kS;
    private final int _nShininess;

    private final double _kT; // k transparency
    private final double _kR; // k reflection

    //BufferedImage _image;

    public final static Material DEFAULT = new Material(0d,0d,0);

    //***************** Constructors **********************//

    /**
     * Basic Constructor
     *
     * @param _kD
     * @param _kS
     * @param _nShininess
     *                  - Determines the level of shining of the material
     */
    public Material(double _kD, double _kS, int _nShininess) {
        this(_kD, _kS, _nShininess, 0, 0);
    }

    /**
     * Constructor with transparency and reflection parameters
     *
     * @param _kD
     * @param _kS
     * @param _nShininess
     * @param _kT
     * @param _kR
     */
    public Material(double _kD, double _kS, int _nShininess, double _kT, double _kR){
        this._kD = _kD;
        this._kS = _kS;
        this._nShininess = _nShininess;
        this._kT = _kT;
        this._kR = _kR;

    }

   /* public Material(double _kD, double _kS, int _nShininess, double _kT, double _kR, String _image){
        this(_kD,_kS,_nShininess,_kT,_kR);
        if(_image != null){
            try {
                this._image = ImageIO.read(new File(_image));
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }*/

    // ***************** Getters/Setters ********************** //

    /**
     * Getter
     *
     * Gets the degree of diffusion of the material
     * @return diffusion exponent
     */
    public double getKd() {
        return _kD;
    }

    /**
     * Getter
     *
     * Gets the specular degree of the material
     * @return specualr exponent
     */
    public double getKs() {
        return _kS;
    }

    /**
     * Getter
     *
     * Gets the degree of shining of the material
     * @return Exponent of shininess
     */
    public int getNShininess() {
        return _nShininess;
    }

    /**
     * Getter
     *
     * Gets the degree of transparency of the material
     * @return transparency exponent
     */
    public double getKt() {return _kT;}

    /**
     * Getter
     *
     * Gets the degree of reflection of the material
     * @return reflection exponent
     */
    public double getKr() {return _kR;}

}

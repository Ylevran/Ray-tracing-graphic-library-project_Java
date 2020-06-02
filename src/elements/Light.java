package elements;


import primitives.Color;

/**
 * base class for all Lights, _intensity value, intentionally "package-friendly" due to performance
 * .
 *
 * @author Yosef Levran & Shmuel Segal
 */
public abstract class Light {

     protected Color _intensity;


    //***************** Constructors **********************//

    /**
     * Initialize Lights color intensity
     * @param _intensity
     */
    public Light(Color _intensity) {
        this._intensity = _intensity;
    }

    /*public Light() {
        this._intensity = Color.BLACK;
    }*/

    // ***************** Getters/Setters ********************** //

    /**
     * Gets the original Light intensity I<sub>0</sub>
     * @return Light intensity
     */
    public Color getIntensity(){
        return new Color(_intensity);
    }


}

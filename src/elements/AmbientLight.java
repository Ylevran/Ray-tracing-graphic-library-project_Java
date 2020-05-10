package elements;

import primitives.Color;

public class AmbientLight extends Light {



    // ***************** Getters/Setters ********************** //





   // private double _Ka;         // Sets the intensity of the light

    //***************** Constructors **********************//

    /**
     * @param _intensity Filling Light Intensity
     * @param _Ka Sets the intensity of the light
     */
    public AmbientLight(Color _intensity, double _Ka) {
        super(_intensity.scale(_Ka));
    }

}

package elements;

import primitives.Color;


/**
 * @author Yossef Levran, ID: 332484609, Email Address: yossef.levran@gmail.com
 * @author Shmuel Segal, ID: 052970464, Email address: shmuelse@gmail.com
 */
public class AmbientLight extends Light {

     //private double _ka; // Sets the intensity of the light

    //***************** Constructors **********************//

    /**
     * @param _intensity Filling Light Intensity
     * @param _Ka Sets the intensity of the light
     */
    public AmbientLight(Color _intensity, double _Ka) {
        super(_intensity.scale(_Ka));
    }


    // ***************** Getters/Setters ********************** //


}

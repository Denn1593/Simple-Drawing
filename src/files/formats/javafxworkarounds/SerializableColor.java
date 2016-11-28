package files.formats.javafxworkarounds;

import java.io.Serializable;

/**
 * Created by dennis on 11/28/16.
 */
public class SerializableColor implements Serializable
{
    private double[] color = new double[3];

    public SerializableColor(double red, double green, double blue)
    {
        color[0] = red;
        color[1] = green;
        color[2] = blue;
    }

    public double[] getColor()
    {
        return color;
    }
}

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
        color[0] = (byte) (red * 255 - 128);
        color[1] = (byte) (green * 255 - 128);
        color[2] = (byte) (blue * 255 - 128);
    }

    public double[] getColor()
    {
        double[] col =  {(color[0] + 128) / 255, (color[1] + 128) / 255, (color[2] + 128) / 255};
        return col;
    }
}

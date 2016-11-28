package files.formats.javafxworkarounds;

import java.io.Serializable;

/**
 * Created by dennis on 11/28/16.
 */
public class SerializableLayer implements Serializable
{
    private SerializableColor[][] layer;

    public SerializableLayer(SerializableColor[][] colors)
    {
        layer = colors;
    }

    public SerializableColor getColor(int x, int y)
    {
        return layer[x][y];
    }
}

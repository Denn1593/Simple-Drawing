package files.formats.javafxworkarounds;

import java.io.Serializable;

/**
 * Created by dennis on 11/28/16.
 */
public class SerializableLayer implements Serializable
{
    private SerializableColor[][] layer;
    private String name;

    public SerializableLayer(SerializableColor[][] colors, String name)
    {
        layer = colors;
        this.name = name;
    }

    public SerializableColor getColor(int x, int y)
    {
        return layer[x][y];
    }

    public String getName()
    {
        return name;
    }
}

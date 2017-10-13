package effects;

import effects.base.LayerEffect;
import effects.base.Parameter;
import javafx.scene.paint.Color;

/**
 * Created by Dennis on 29-06-2017.
 */
public class Pixelate extends LayerEffect
{
    public Pixelate(String name)
    {
        super(name);
        Parameter[] parameters = {new Parameter(0, 60, "Pixel size")};
        setParameters(parameters);
    }

    @Override
    public Color[][] applyEffect(Color[][] colors, int width, int height)
    {
        if((int) getParameter(0).getValue() != 0)
        {
            for (int x = 0; x < width; x = x + (int) getParameter(0).getValue())
            {
                for (int y = 0; y < height; y = y + (int) getParameter(0).getValue())
                {
                    try
                    {
                        Color pixelColor = colors[x][y];

                        for (int xx = 0; xx < (int) getParameter(0).getValue(); xx++)
                        {
                            for (int yy = 0; yy < (int) getParameter(0).getValue(); yy++)
                            {
                                try
                                {
                                    colors[x + xx][y + yy] = pixelColor;
                                }
                                catch (Exception e)
                                {

                                }
                            }
                        }
                    } catch (Exception e)
                    {

                    }
                }
            }
        }
        return colors;
    }

    @Override
    public boolean usesCursor()
    {
        return false;
    }
}

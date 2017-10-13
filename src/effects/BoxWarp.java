package effects;

import effects.base.LayerEffect;
import effects.base.Parameter;
import effects.util.EffectUtilities;
import javafx.scene.paint.Color;

/**
 * Created by Dennis on 29-06-2017.
 */
public class BoxWarp extends LayerEffect
{
    public BoxWarp(String name)
    {
        super(name);
        Parameter[] parameters = {new Parameter(1, 50, "Box size"), new Parameter(1, 50, "Intensity")};
        setParameters(parameters);
    }

    @Override
    public Color[][] applyEffect(Color[][] colors, int width, int height)
    {
        double waveSize = getParameter(0).getValue();
        double intensity = getParameter(1).getValue();

        double xPos = width / 2;
        double yPos = height / 2;

        Color[][] newColors = new Color[width][height];

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                double yDist = Math.abs(yPos - y);
                double xDist = Math.abs(xPos - x);

                try
                {
                    newColors[x][y] = colors[x + (int) (Math.sin(xDist / waveSize) * intensity)][y + (int) (Math.sin(yDist / waveSize) * intensity)];
                }
                catch (Exception e)
                {
                    int[] coordinates = EffectUtilities.getClosestPixel(x  + (int) (Math.sin(xDist / waveSize) * intensity), y + (int) (Math.sin(yDist / waveSize) * intensity), width, height);
                    newColors[x][y] = colors[coordinates[0]][coordinates[1]];
                }
            }
        }
        return newColors;
    }

    @Override
    public boolean usesCursor()
    {
        return false;
    }
}

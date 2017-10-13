package effects;

import effects.base.LayerEffect;
import effects.base.Parameter;
import effects.util.EffectUtilities;
import javafx.scene.paint.Color;

/**
 * Created by Dennis on 29-06-2017.
 */
public class Ripples extends LayerEffect
{
    public Ripples(String name, int width, int height)
    {
        super(name);
        Parameter[] parameters = {new Parameter(0, width, "Center X-Position"), new Parameter(0, height, "Center Y-Position"), new Parameter(1, 50, "Wave size"), new Parameter(1, 50, "Intensity")};
        setParameters(parameters);
    }

    @Override
    public Color[][] applyEffect(Color[][] colors, int width, int height)
    {
        double xPos = getParameter(0).getValue();
        double yPos = getParameter(1).getValue();
        double waveSize = getParameter(2).getValue();
        double intensity = getParameter(3).getValue();

        Color[][] newColors = new Color[width][height];

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                double vectorY = yPos - y;
                double vectorX = xPos - x;

                double centerDistance = Math.sqrt((x - xPos)*(x - xPos) + (y - yPos) * (y - yPos));

                vectorY = vectorY / centerDistance;
                vectorX = vectorX / centerDistance;

                vectorY = vectorY * (Math.sin(centerDistance / waveSize) * intensity);
                vectorX = vectorX * (Math.sin(centerDistance / waveSize) * intensity);


                try
                {
                    newColors[x][y] = colors[x + (int) vectorX][y + (int) vectorY];
                }
                catch (Exception e)
                {
                    int[] coordinates = EffectUtilities.getClosestPixel(x  + (int) vectorX, y + (int) vectorY, width, height);
                    newColors[x][y] = colors[coordinates[0]][coordinates[1]];
                }
            }
        }
        return newColors;
    }

    @Override
    public boolean usesCursor()
    {
        return true;
    }
}

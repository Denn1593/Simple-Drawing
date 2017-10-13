package effects;

import effects.base.LayerEffect;
import effects.base.Parameter;
import effects.util.EffectUtilities;
import javafx.scene.paint.Color;

/**
 * Created by Dennis on 29-06-2017.
 */
public class Warp extends LayerEffect
{
    public Warp(String name, int width, int height)
    {
        super(name);
        Parameter[] parameters = {new Parameter(0, width, "Center X-Position"), new Parameter(0, height, "Center Y-Position"), new Parameter(-1, 1, "Intensity/Direction")};
        setParameters(parameters);
    }

    @Override
    public Color[][] applyEffect(Color[][] colors, int width, int height)
    {
        double xPos = getParameter(0).getValue();
        double yPos = getParameter(1).getValue();
        double intensity = getParameter(2).getValue() / 10;

        System.out.println(intensity);

        Color[][] newColors = new Color[width][height];

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                double centerDistance = Math.sqrt((x - xPos)*(x - xPos) + (y - yPos) * (y - yPos));

                //angle between (x, y) and (1, 0)

                double vecX = x - xPos;
                double vecY = y - yPos;

                double angle = Math.atan2(vecX, vecY);
                angle = angle + centerDistance * intensity - Math.PI / 2;


                try
                {
                    newColors[x][y] = colors[(int) (xPos + Math.cos(angle) * centerDistance)][(int) (yPos - Math.sin(angle) * centerDistance)];
                }
                catch (Exception e)
                {

                    int[] coordinates = EffectUtilities.getClosestPixel((int) (xPos + Math.cos(angle) * centerDistance), (int) (yPos - Math.sin(angle) * centerDistance), width, height);
                    newColors[x][y] = colors[coordinates[0]][coordinates[1]];
                    //e.printStackTrace();

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

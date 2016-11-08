package tools;

import javafx.scene.paint.Color;
import tools.Tool;

import java.util.Random;

/**
 * Created by dennis on 11/7/16.
 */
public class SprayTool implements Tool
{
    private Random random = new Random();
    @Override
    public Color[][] paint(int size, Color color, Color[][] inData)
    {
        Color[][] colors = new Color[size][size];
        for (int i = 0; i < size * 2; i++)
        {
            int radius = random.nextInt(size / 2 - 1);
            double rotation = random.nextDouble() * Math.PI * 2;

            colors[(int) (radius * Math.cos(rotation)) + size/2][(int)(radius * Math.sin(rotation)) + size/2] = color;
        }
        return colors;
    }

    public String toString()
    {
        return "Spraytool";
    }
}

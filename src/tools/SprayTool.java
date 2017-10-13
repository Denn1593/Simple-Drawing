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
    public boolean[][] erase(int size, Color color, Color[][] inData)
    {
        return new boolean[size][size];
    }

    @Override
    public boolean[][] eraseAll(int size, Color color, Color[][] inData, int width, int height, int mouseX, int mouseY)
    {
        return new boolean[width][height];
    }

    @Override
    public boolean canRead()
    {
        return true;
    }

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

    @Override
    public Color[][] paintAll(int size, Color color, Color[][] inData, int width, int height, int mouseX, int mouseY)
    {
        Color targetColor = inData[mouseX][mouseY];

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                y = y + random.nextInt(size);
                if(y < height)
                {
                    if(targetColor == null)
                    {
                        inData[x][y] = color;
                    }
                    else
                    {
                        if(inData[x][y] == targetColor)
                        {
                            inData[x][y] = color;
                        }
                    }
                }
            }
        }
        return inData;
    }

    public String toString()
    {
        return "Spraytool";
    }
}

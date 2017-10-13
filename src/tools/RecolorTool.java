package tools;

import javafx.scene.paint.Color;
import tools.util.ToolUtilities;

/**
 * Created by dennis on 11/9/16.
 */
public class RecolorTool implements Tool
{
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

        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                if(ToolUtilities.isInRange(x, y, size))
                {
                    if (inData[x][y] != null)
                    {
                        inData[x][y] = color;
                    }
                }
            }
        }
        return inData;
    }

    @Override
    public Color[][] paintAll(int size, Color color, Color[][] inData, int width, int height, int mouseX, int mouseY)
    {
        Color targetColor = inData[mouseX][mouseY];

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if(targetColor == null)
                {
                    if (inData[x][y] != null)
                    {
                        inData[x][y] = color;
                    }
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
        return inData;
    }

    public String toString()
    {
        return "RecolorTool";
    }
}

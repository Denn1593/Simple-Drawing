package tools;

import javafx.scene.paint.Color;
import tools.util.ToolUtilities;

/**
 * Created by dennis on 11/8/16.
 */
public class BrushTool implements Tool
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
        return false;
    }

    @Override
    public Color[][] paint(int size, Color color, Color[][] inData)
    {
        Color[][] colors = new Color[size][size];

        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                if(ToolUtilities.isInRange(x, y, size))
                {
                    colors[x][y] = color;
                }
            }
        }
        return colors;
    }

    @Override
    public Color[][] paintAll(int size, Color color, Color[][] inData, int width, int height, int mouseX, int mouseY)
    {
        Color[][] outPut = new Color[width][height];

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                outPut[x][y] = color;
            }
        }
        return outPut;
    }

    public String toString()
    {
        return "BrushTool";
    }


}

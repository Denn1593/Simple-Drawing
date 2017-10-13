package tools;

import javafx.scene.paint.Color;
import tools.util.ToolUtilities;

/**
 * Created by dennis on 11/10/16.
 */
public class ColorEraserTool implements Tool
{
    @Override
    public boolean[][] erase(int size, Color color, Color[][] inData)
    {
        boolean[][] erase = new boolean[size][size];

        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                if(ToolUtilities.isInRange(x, y, size))
                {
                    if (inData[x][y] == color)
                    {
                        erase[x][y] = true;
                    }
                }
            }
        }
        return erase;
    }

    @Override
    public boolean[][] eraseAll(int size, Color color, Color[][] inData, int width, int height, int mouseX, int mouseY)
    {
        boolean[][] erased = new boolean[width][height];

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if(inData[x][y] == color)
                {
                    erased[x][y] = true;
                }
            }
        }
        return erased;
    }

    @Override
    public boolean canRead()
    {
        return true;
    }

    @Override
    public Color[][] paint(int size, Color color, Color[][] inData)
    {
        return inData;
    }

    @Override
    public Color[][] paintAll(int size, Color color, Color[][] inData, int width, int height, int mouseX, int mouseY)
    {
        return inData;
    }

    public String toString()
    {
        return "ColorEraserTool";
    }
}

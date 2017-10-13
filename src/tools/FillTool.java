package tools;

import javafx.scene.paint.Color;
import tools.util.ToolUtilities;

/**
 * Created by Dennis on 19-03-2017.
 */
public class FillTool implements Tool
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
                    if(inData[x][y] == null)
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
        return new Color[0][];
    }

    public String toString()
    {
        return "FillTool";
    }
}

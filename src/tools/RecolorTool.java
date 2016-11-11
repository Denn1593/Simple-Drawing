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

    public String toString()
    {
        return "RecolorTool";
    }
}

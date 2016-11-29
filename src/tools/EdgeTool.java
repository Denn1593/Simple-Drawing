package tools;

import javafx.scene.paint.Color;
import tools.util.ToolUtilities;

/**
 * Created by Dennis on 18-11-2016.
 */
public class EdgeTool implements Tool {
    @Override
    public boolean[][] erase(int size, Color color, Color[][] inData) {
        return new boolean[size][size];
    }

    @Override
    public boolean canRead() {
        return true;
    }

    @Override
    public Color[][] paint(int size, Color color, Color[][] inData)
    {
        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                if(inData[x][y] == null && ToolUtilities.isInRange(x, y, size))
                {
                    for (int xx = 0; xx < size; xx++)
                    {
                        for (int yy = 0; yy < size; yy++)
                        {
                            try
                            {
                                if(ToolUtilities.isInRange(xx, yy, size))
                                {
                                    if (inData[x + xx - size / 2][y + yy - size / 2] != null &&
                                            inData[x + xx - size / 2][y + yy - size / 2] != color)
                                    {
                                        inData[x][y] = color;
                                    }
                                }
                            }
                            catch (Exception e)
                            {

                            }
                        }
                    }
                }
            }
        }
        return inData;
    }

    public String toString()
    {
        return "EdgeTool";
    }
}

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
                if(inData[x][y] == null)
                {
                    for (int xx = 0; xx < size / 2; xx++)
                    {
                        for (int yy = 0; yy < size / 2; yy++)
                        {
                            try
                            {
                                if(ToolUtilities.isInRange(xx - size/64, yy - size/64, size/2))
                                {
                                    if (inData[x + xx - size / 4][y + yy - size / 4] != null &&
                                            inData[x + xx - size / 4][y + yy - size / 4] != color)
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

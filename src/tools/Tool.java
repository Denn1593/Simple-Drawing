package tools;

import javafx.scene.paint.Color;

/**
 * Created by dennis on 11/7/16.
 */
public interface Tool
{
    public boolean canRead();
    public Color[][] paint(int size, Color color, Color[][] inData);
}

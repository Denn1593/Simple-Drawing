package tools.util;

/**
 * Created by dennis on 11/10/16.
 */
public class ToolUtilities
{
    public static boolean isInRange(int x, int y, int size)
    {
        return (Math.sqrt((x - size/2) * (x - size/2) + (y - size/2) * (y - size/2)) < size/2);
    }
}

package effects.base;

import javafx.scene.paint.Color;

/**
 * Created by Dennis on 29-06-2017.
 */
public abstract class LayerEffect
{
    private Parameter[] parameters;
    private String name;

    public LayerEffect(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setParameters(Parameter[] ranges)
    {
        if(parameters == null)
        {
            parameters = ranges;
        }
    }

    public abstract Color[][] applyEffect(Color[][] colors, int width, int height);

    public abstract boolean usesCursor();

    public Parameter getParameter(int index)
    {
        return parameters[index];
    }

    public int getAmountOfParameters()
    {
        return  parameters.length;
    }
}

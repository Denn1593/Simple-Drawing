package painting.history;

import painting.layers.Layer;

import java.util.ArrayList;

/**
 * Created by dennis on 11/25/16.
 */
public class HistoryManager
{
    private int maxUndoAmount;
    private int index = 0;
    private ArrayList<ArrayList<Layer>> undoHistory = new ArrayList<>();

    public HistoryManager(int maxUndoAmount)
    {
        this.maxUndoAmount = maxUndoAmount;
    }

    public void addChange(ArrayList<Layer> layers)
    {
        undoHistory.add(index, layers);
        index++;

        for (int i = index; i < undoHistory.size() - 1;)
        {
            undoHistory.remove(index);
        }

    }

    public ArrayList<Layer> undo()
    {
        index--;
        return undoHistory.get(index);
    }

    public boolean canUndo()
    {
        return true;
    }

    public boolean canRedo()
    {
        return true;
    }
}

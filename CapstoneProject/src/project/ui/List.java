package project.ui;

import gameutils.func.*;

import static gameutils.util.Mathf.*;

/** Represents a list of items. */
public class List extends Table{
    private float bottom = 0;

    /**
     * Creates a list and runs the given runnable on itself.
     * @param cons the runnable
     */
    public List(Cons<List> cons){
        super();
        cons.get(this);
    }

    /**
     * Moves y of the next added UI by the specified amount.
     * @param row the amount
     * @return itself, for chaining
     */
    public List row(float row){
        bottom += row;
        return this;
    }

    @Override
    public float height(){
        float sum = 0;
        for(Table t : contents) sum += t.height();
        return max(sum, height);
    }

    @Override
    public List add(Table table){
        table.y(bottom);
        bottom += table.height();
        super.add(table);
        return this;
    }

    @Override
    public List clear(){
        bottom = 0;
        super.clear();
        return this;
    }
}

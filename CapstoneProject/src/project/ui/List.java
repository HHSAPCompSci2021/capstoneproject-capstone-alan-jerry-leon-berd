package project.ui;

import gameutils.func.*;

import static gameutils.util.Mathf.*;

/** Represents a list of items. */
public class List extends Table{
    public float bottom = 0;

    public List(Cons<List> cons){
        super();
        cons.get(this);
    }

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
}

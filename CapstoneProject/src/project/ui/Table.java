package project.ui;

import gameutils.func.*;
import gameutils.func.prim.*;
import gameutils.math.*;
import gameutils.struct.*;
import project.*;
import project.core.UI.*;
import project.ui.drawables.*;
import project.ui.interactables.Button;
import project.ui.interactables.*;
import project.ui.screens.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** The base class for all UI. Contains an x, y, width, height, color, and align. Can have a list of contents. */
public class Table{
    /** The parent table. Can be null. */
    public Table parent;
    public float x, y, width, height;
    /** Contains the contents, or children of this table. */
    public Seq<Table> contents;
    /** Returns the color the table should be, given the table. */
    public Func<Table, Color> color;
    public Floatf<Table> alpha;
    public AlignX alignX = AlignX.left;
    public AlignY alignY = AlignY.top;
    /** The runnable called every frame this piece of UI is updated. */
    public Cons<Table> update;

    public Table(){
        contents = new Seq<>();
    }

    /** Creates a table and runs the given runnable on itself. */
    public Table(Cons<Table> init){
        this();
        init.get(this);
    }

    /** Sets the parent of this table. */
    public Table parent(Table parent){
        this.parent = parent;
        return this;
    }

    /** Sets the x of this table. */
    public Table x(float x){
        this.x = x;
        return this;
    }

    /** Sets the y of this table. */
    public Table y(float y){
        this.y = y;
        return this;
    }

    /** Sets the width of this table. */
    public Table width(float w){
        width = w;
        return this;
    }

    /** Sets the height of this table. */
    public Table height(float h){
        height = h;
        return this;
    }

    /** Sets the x align of this table. */
    public Table alignX(AlignX alignX){
        this.alignX = alignX;
        return this;
    }

    /** Sets the y align of this table. */
    public Table alignY(AlignY alignY){
        this.alignY = alignY;
        return this;
    }

    /** Returns the color of this table. */
    public Color color(){
        return color == null ? Color.white : color.get(this);
    }

    public float alpha(){
        return alpha == null ? 255 : alpha.get(this);
    }

    /** Sets the color of this table. */
    public Table color(Color color){
        return color(t -> color);
    }

    /** Sets the color of this table with a runnable which takes the table as the parameter and returns the color. */
    public Table color(Func<Table, Color> color){
        this.color = color;
        return this;
    }

    public Table alpha(Floatf<Table> alpha){
        this.alpha = alpha;
        return this;
    }

    /** Sets the update runnable of this table. */
    public Table update(Cons<Table> update){
        this.update = update;
        return this;
    }

    /** Adds a table to the contents of this table. */
    public Table table(Cons<Table> cons){
        Table table = new Table().parent(this);
        cons.get(table);
        add(table);
        return this;
    }


    /** Adds a drawable to the contents of this table. */
    public Table drawable(Cons<Drawable> cons){
        Drawable drawable = (Drawable)new Drawable().parent(this);
        cons.get(drawable);
        add(drawable);
        return this;
    }

    /** Adds a piece of text to the contents of this table. */
    public Table text(Cons<Text> cons){
        Text text = (Text)new Text().parent(this);
        cons.get(text);
        add(text);
        return this;
    }

    /** Adds a button to the contents of this table. */
    public Table button(Cons<Button> cons){
        Button button = (Button)new Button().parent(this);
        cons.get(button);
        add(button);
        return this;
    }

    /** Adds a slider to the contents of this table. */
    public Table slider(Cons<Slider> cons){
        Slider slider = (Slider)new Slider().parent(this);
        cons.get(slider);
        add(slider);
        return this;
    }

    /** Adds a tooltip to the contents of this table. */
    public Table tooltip(Cons<Tooltip> cons){
        Tooltip tooltip = (Tooltip)new Tooltip().parent(this);
        cons.get(tooltip);
        add(tooltip);
        return this;
    }

    /** Adds the specified item to the contents of this table. */
    public Table add(Table table){
        table.parent = this;
        contents.add(table);
        return this;
    }

    public Table clear(){
        contents.clear();
        return this;
    }

    /** Calculates and returns the real x of this table. */
    public float x(){
        return x + (parent == null ? 0 : parent.x());

    }

    /** Calculates and returns the real y of this table. */
    public float y(){
        return y + (parent == null ? 0 : parent.y());
    }

    /** Calculates and returns the x translation of the table (based on align). */
    public float tx(){
        return (alignX == AlignX.left ? 0 :
        alignX == AlignX.center ? -width() / 2 :
        alignX == AlignX.right ? -width() : 0);
    }

    /** Calculates and returns the y translation of the table (based on align). */
    public float ty(){
        return (alignY == AlignY.top ? 0 :
        alignY == AlignY.center ? -height() / 2 :
        alignY == AlignY.bottom ? -height() : 0);
    }

    /** Calculates and returns the real width of this table. */
    public float width(){
        float max = width;
        for(Table t : contents) max = max(max, t.width() + t.x() - x());
        return max * UIscale;
    }

    /** Calculates and returns the real height of this table. */
    public float height(){
        float max = height;
        for(Table t : contents) max = max(max, t.height() + t.y() - y());
        return max * UIscale;
    }

    /** Calculates and returns the bounds of this table. */
    public Range2 bounds(){
        return Tmp.r1.set(x() + tx(), y() + ty(), width(), height());
    }

    /** Simulates this table, and it's contents. */
    public void process(){
        update();

        canvas.pushMatrix();
        canvas.translate(x() + tx(), y() + ty());
        draw();
        canvas.popMatrix();

        for(Table t : contents) t.process();
    }

    /** Updates this table. */
    public void update(){
        if(update != null) update.get(this);
    }

    /** Draws this table. */
    public void draw(){
    }
}

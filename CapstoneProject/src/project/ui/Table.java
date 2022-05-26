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

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** The base class for all UI. Contains an x, y, width, height, color, and align. Can have a list of contents. */
public class Table{
    /** The parent table. Can be null. */
    public Table parent;
    /** The x, y, width, and height, respectively. */
    public float x, y, width, height;

    protected Seq<Table> contents;
    protected Func<Table, Color> color;
    protected Floatf<Table> alpha;
    protected AlignX alignX = AlignX.left;
    protected AlignY alignY = AlignY.top;
    protected Cons<Table> update;

    /** Creates a new table. */
    public Table(){
        contents = new Seq<>();
    }

    /**
     * Creates a table and runs the given runnable on itself.
     * @param init the runnable
     */
    public Table(Cons<Table> init){
        this();
        init.get(this);
    }

    /**
     * Sets the parent of this table.
     * @param parent the parent
     * @return itself, for chaining
     */
    public Table parent(Table parent){
        this.parent = parent;
        return this;
    }

    /**
     * Sets the x of this table.
     * @param x the x
     * @return itself, for chaining
     */
    public Table x(float x){
        this.x = x;
        return this;
    }

    /**
     * Sets the y of this table.
     * @param y the y
     * @return itself, for chaining
     */
    public Table y(float y){
        this.y = y;
        return this;
    }

    /**
     * Sets the width of this table.
     * @param w the width
     * @return itself, for chaining
     */
    public Table width(float w){
        width = w;
        return this;
    }

    /**
     * Sets the height of this table.
     * @param h the height
     * @return itself, for chaining
     */
    public Table height(float h){
        height = h;
        return this;
    }

    /**
     * Sets the x align of this table.
     * @param alignX the x align
     * @return itself, for chaining
     */
    public Table alignX(AlignX alignX){
        this.alignX = alignX;
        return this;
    }

    /**
     * Sets the y align of this table.
     * @param alignY the y align
     * @return itself, for chaining
     */
    public Table alignY(AlignY alignY){
        this.alignY = alignY;
        return this;
    }

    protected Color color(){
        return color == null ? Color.white : color.get(this);
    }

    protected float alpha(){
        return alpha == null ? 255 : alpha.get(this);
    }

    /**
     * Sets the color of this table.
     * @param color the color
     * @return itself, for chaining
     */
    public Table color(Color color){
        return color(t -> color);
    }

    /**
     * Sets the color of this table, with a runnable that takes in the table as a parameter.
     * @param color the color
     * @return itself, for chaining
     */
    public Table color(Func<Table, Color> color){
        this.color = color;
        return this;
    }

    /**
     * Sets the alpha to a runnable that returns a float.
     * @param alpha the alpha runnable
     * @return itself, for chaining
     */
    public Table alpha(Floatf<Table> alpha){
        this.alpha = alpha;
        return this;
    }

    /**
     * Sets the update runnable of this table.
     * @param update the runnable called every time this table is updated
     * @return itself, for chaining
     */
    public Table update(Cons<Table> update){
        this.update = update;
        return this;
    }

    /**
     * Adds a table to the contents of this table.
     * @param cons the runnable that is called on the new item
     * @return itself, for chaining
     */
    public Table table(Cons<Table> cons){
        Table table = new Table().parent(this);
        cons.get(table);
        add(table);
        return this;
    }


    /**
     * Adds a drawable to the contents of this table.
     * @param cons the runnable that is called on the new item
     * @return itself, for chaining
     */
    public Table drawable(Cons<Drawable> cons){
        Drawable drawable = (Drawable)new Drawable().parent(this);
        cons.get(drawable);
        add(drawable);
        return this;
    }

    /**
     * Adds a piece of text to the contents of this table.
     * @param cons the runnable that is called on the new item
     * @return itself, for chaining
     */
    public Table text(Cons<Text> cons){
        Text text = (Text)new Text().parent(this);
        cons.get(text);
        add(text);
        return this;
    }

    /**
     * Adds a button to the contents of this table.
     * @param cons the runnable that is called on the new item
     * @return itself, for chaining
     */
    public Table button(Cons<Button> cons){
        Button button = (Button)new Button().parent(this);
        cons.get(button);
        add(button);
        return this;
    }

    /**
     * Adds a slider to the contents of this table.
     * @param cons the runnable that is called on the new item
     * @return itself, for chaining
     */
    public Table slider(Cons<Slider> cons){
        Slider slider = (Slider)new Slider().parent(this);
        cons.get(slider);
        add(slider);
        return this;
    }

    /**
     * Adds a tooltip to the contents of this table.
     * @param cons the runnable that is called on the new item
     * @return itself, for chaining
     */
    public Table tooltip(Cons<Tooltip> cons){
        Tooltip tooltip = (Tooltip)new Tooltip().parent(this);
        cons.get(tooltip);
        add(tooltip);
        return this;
    }

    /**
     * Adds the specified item to the contents of this table.
     * @param table the item to be added
     * @return itself, for chaining
     */
    public Table add(Table table){
        table.parent = this;
        contents.add(table);
        return this;
    }

    /**
     * Clears the contents of this table.
     * @return itself, for chaining
     */
    public Table clear(){
        contents.clear();
        return this;
    }

    /**
     * Calculates and returns the real x of this table.
     * @return the real x
     */
    public float x(){
        return x + (parent == null ? 0 : parent.x());

    }

    /**
     * Calculates and returns the real y of this table.
     * @return the real y
     */
    public float y(){
        return y + (parent == null ? 0 : parent.y());
    }

    protected float tx(){
        return (alignX == AlignX.left ? 0 :
        alignX == AlignX.center ? -width() / 2 :
        alignX == AlignX.right ? -width() : 0);
    }

    protected float ty(){
        return (alignY == AlignY.top ? 0 :
        alignY == AlignY.center ? -height() / 2 :
        alignY == AlignY.bottom ? -height() : 0);
    }

    /**
     * Calculates and returns the real width of this table.
     * @return the real width
     */
    public float width(){
        float max = width;
        for(Table t : contents) max = max(max, t.width() + t.x() - x());
        return max * UIscale;
    }

    /**
     * Calculates and returns the real height of this table.
     * @return the real height
     */
    public float height(){
        float max = height;
        for(Table t : contents) max = max(max, t.height() + t.y() - y());
        return max * UIscale;
    }

    /**
     * Calculates and returns the bounds of this table.
     * @return the bounds
     */
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

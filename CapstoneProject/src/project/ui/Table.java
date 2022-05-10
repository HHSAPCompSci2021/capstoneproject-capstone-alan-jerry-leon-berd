package project.ui;

import gameutils.func.*;
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
    public Table parent;
    public float x, y, width, height;
    public Seq<Table> contents;
    public Func<Table, Color> color;
    public AlignX alignX = AlignX.left;
    public AlignY alignY = AlignY.top;
    public Cons<Table> update;

    public Table(){
        contents = new Seq<>();
    }

    public Table(Cons<Table> init){
        this();
        init.get(this);
    }

    public Table parent(Table parent){
        this.parent = parent;
        return this;
    }

    public Table x(float x){
        this.x = x;
        return this;
    }

    public Table y(float y){
        this.y = y;
        return this;
    }

    public Table width(float w){
        width = w;
        return this;
    }

    public Table height(float h){
        height = h;
        return this;
    }

    public Table alignX(AlignX alignX){
        this.alignX = alignX;
        return this;
    }

    public Table alignY(AlignY alignY){
        this.alignY = alignY;
        return this;
    }

    public Color color(){
        return color == null ? Color.white : color.get(this);
    }

    public Table color(Color color){
        return color(t -> color);
    }

    public Table color(Func<Table, Color> color){
        this.color = color;
        return this;
    }

    public Table update(Cons<Table> update){
        this.update = update;
        return this;
    }

    public Table drawable(Cons<Drawable> cons){
        Drawable drawable = (Drawable)new Drawable().parent(this);
        cons.get(drawable);
        add(drawable);
        return this;
    }

    public Table text(Cons<Text> cons){
        Text text = (Text)new Text().parent(this);
        cons.get(text);
        add(text);
        return this;
    }

    public Table glow(Cons<GlowText> cons){
        GlowText text = (GlowText)new GlowText().parent(this);
        cons.get(text);
        add(text);
        return this;
    }

    public Table button(Cons<Button> cons){
        Button button = (Button)new Button().parent(this);
        cons.get(button);
        add(button);
        return this;
    }

    public Table slider(Cons<Slider> cons){
        Slider slider = (Slider)new Slider().parent(this);
        cons.get(slider);
        add(slider);
        return this;
    }

    public Table tooltip(Cons<Tooltip> cons){
        Tooltip tooltip = (Tooltip)new Tooltip().parent(this);
        cons.get(tooltip);
        add(tooltip);
        return this;
    }

    public Table add(Table table){
        table.parent = this;
        contents.add(table);
        return this;
    }

    public float x(){
        return x + (parent == null ? 0 : parent.x());

    }

    public float y(){
        return y + (parent == null ? 0 : parent.y());
    }

    public float tx(){
        return (alignX == AlignX.left ? 0 :
        alignX == AlignX.center ? -width() / 2 :
        alignX == AlignX.right ? -width() : 0);
    }

    public float ty(){
        return (alignY == AlignY.top ? 0 :
        alignY == AlignY.center ? -height() / 2 :
        alignY == AlignY.bottom ? -height() : 0);
    }

    public float width(){
        float max = width;
        for(Table t : contents) max = max(max, t.width() + t.x() - x());
        return max * UIscale;
    }

    public float height(){
        float max = height;
        for(Table t : contents) max = max(max, t.height() + t.y() - y());
        return max * UIscale;
    }

    public Range2 bounds(){
        return Tmp.r1.set(x(), y(), width(), height());
    }

    public void process(){
        update();

        canvas.pushMatrix();
        canvas.translate(x() + tx(), y() + ty());
        draw();
        canvas.popMatrix();

        for(Table t : contents) t.process();
    }

    public void update(){
        if(update != null) update.get(this);
    }

    public void draw(){
    }
}

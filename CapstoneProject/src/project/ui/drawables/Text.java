package project.ui.drawables;

import project.ui.*;

import static project.Vars.*;

/** Represents a text box. */
public class Text extends Table{
    public float size = 10;
    public String text;

    /** Set the test of this text box. */
    public Text text(String text){
        this.text = text;
        return this;
    }

    /** Set the size of the text. */
    public Text size(float size){
        this.size = size;
        return this;
    }

    /** Returns the real size of this piece of text. */
    public float size(){
        return size * UIscale;
    }

    @Override
    public float width(){
        canvas.pushMatrix();
        canvas.textSize(size());
        float width = canvas.textWidth(text);
        canvas.popMatrix();
        return width;
    }

    @Override
    public float height(){
        return size();
    }

    @Override
    public void draw(){
        canvas.fill(color());
        canvas.textSize(size());
        canvas.textAlign(canvas.LEFT, canvas.TOP);

        canvas.pushMatrix();
        canvas.text(text, 0, 0);
        canvas.popMatrix();
    }
}

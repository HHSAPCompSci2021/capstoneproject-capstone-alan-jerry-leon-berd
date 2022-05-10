package project.ui.drawables;

import project.ui.*;

import static project.Vars.*;

/** Represents a text box. */
public class Text extends Table{
    public float size = 10, xScale = 1, yScale = 1;
    public String text;

    public Text text(String text){
        this.text = text;
        return this;
    }

    public Text size(float size){
        this.size = size;
        return this;
    }

    public Text xScale(float xScale){
        this.xScale = xScale;
        return this;
    }

    public Text yScale(float yScale){
        this.yScale = yScale;
        return this;
    }

    public float size(){
        return size * UIscale;
    }

    @Override
    public float width(){
        canvas.pushMatrix();
        canvas.textSize(size());
        float width = canvas.textWidth(text);
        canvas.popMatrix();
        return width * xScale;
    }

    @Override
    public float height(){
        return size() * yScale;
    }

    @Override
    public void draw(){
        canvas.fill(color());
        canvas.textSize(size());
        canvas.textAlign(canvas.LEFT, canvas.TOP);

        canvas.pushMatrix();
        canvas.scale(xScale, yScale);
        canvas.text(text, 0, 0);
        canvas.popMatrix();
    }
}

package project.core;

import processing.core.*;

/** Represents a canvas, which contains graphical functions, inputs processing, and world updating. */
public class Canvas extends PApplet{
    public Canvas(){
    }

    public void init(){
    }

    @Override
    public void draw(){
        textAlign(CENTER, CENTER);
        textSize(100);
        text("Hello World!", width / 2f, height / 2f);
    }
}

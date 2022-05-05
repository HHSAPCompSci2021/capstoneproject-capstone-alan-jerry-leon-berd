package project.core;

import processing.core.*;

public class Canvas extends PApplet{
    public Canvas(){
    }

    public void init(){
    }

    @Override
    public void draw(){
        frameRate(10000000);
        System.out.println(frameRate);
    }
}

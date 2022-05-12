package project.core;

import gameutils.func.prim.*;
import gameutils.math.*;
import gameutils.math.interp.*;
import gameutils.util.*;
import processing.core.*;
import project.graphics.*;
import project.ui.screens.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** An extension of PApplet. Represents the canvas of the project. */
public class Canvas extends PApplet{
    public Screen screen;
    public float shake;

    public Canvas(){
    }

    public void shake(float shake){
        this.shake = max(this.shake, shake);
    }

    public void screen(Screen screen){
        screen.rebuild();
        this.screen = screen;
    }

    @Override
    public void settings(){
        super.settings();
        smooth(8);
    }

    @Override
    public void setup(){
        super.setup();
        textFont(createFont("assets/fonts/brandbe/Brandbe Regular.otf", 100, true));
        for(Sprite s : Sprite.all) s.load();
        screen(ui.menuScreen);
    }

    @Override
    public void draw(){
        input.mouse = new Vec2(mouseX, mouseY);
        screen.update();
        screen.draw();
        shake = max(shake - shakeDrag, 0);
    }

    @Override
    public void mousePressed(){
        if(mouseButton == 37) input.register(input.left);
        else if(mouseButton == 38) input.register(input.middle);
        else if(mouseButton == 39) input.register(input.right);
    }

    @Override
    public void mouseReleased(){
        if(mouseButton == 37) input.remove(input.left);
        else if(mouseButton == 38) input.remove(input.middle);
        else if(mouseButton == 39) input.remove(input.right);
    }


    @Override
    public void keyPressed(){
        if(key == 'd') debug = !debug;
        input.register(keyCode);
    }

    @Override
    public void keyReleased(){
        input.remove(keyCode);
    }


    public void background(Color color){
        background(color.getRGB());
    }

    public void fill(Color color){
        fill(color, color.getAlpha());
    }

    public void fill(Color color, float alpha){
        fill(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }


    @Override
    public void fill(int rgb){
        super.fill(rgb);
        noStroke();
    }

    @Override
    public void fill(int rgb, float a){
        super.fill(rgb, a);
        noStroke();
    }

    @Override
    public void fill(float rgb){
        super.fill(rgb);
        noStroke();
    }

    @Override
    public void fill(float r, float g, float b){
        super.fill(r, g, b);
        noStroke();
    }

    @Override
    public void fill(float r, float g, float b, float a){
        super.fill(r, g, b, a);
        noStroke();
    }


    public void stroke(Color color){
        stroke(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public void stroke(Color color, float alpha){
        stroke(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }


    @Override
    public void stroke(int rgb){
        super.stroke(rgb);
        strokeWeight(1);
    }

    @Override
    public void stroke(float rgb){
        super.stroke(rgb);
        strokeWeight(1);
    }

    @Override
    public void stroke(float r, float g, float b){
        super.stroke(r, g, b);
        strokeWeight(1);
    }

    @Override
    public void stroke(float r, float g, float b, float a){
        super.stroke(r, g, b, a);
        strokeWeight(1);
    }


    public void rect(float x, float y, float s){
        rect(x, y, s, s);
    }

    public void rectr(float x, float y, float w, float h, float r){
        rectr(0, 0, x, y, w, h, r);
    }

    public void rectr(float tx, float ty, float x, float y, float w, float h, float r){
        pushMatrix();
        translate(x + w / 2, y + h / 2);
        rotate(r / 180f * pi);
        rect(tx - w / 2, ty - h / 2, w, h);
        popMatrix();
    }

    public void rectc(float x, float y, float s){
        rectc(x, y, s, s);
    }

    public void rectc(float x, float y, float w, float h){
        rect(x - w / 2, y - h / 2, w, h);
    }

    public void rectc(float x, float y, float w, float h, float r){
        rectr(x - w / 2, y - h / 2, w, h, r);
    }

    public void rectc(float tx, float ty, float x, float y, float w, float h, float r){
        rectr(tx, ty, x - w / 2, y - h / 2, w, h, r);
    }


    public void ellipse(float x, float y, float s){
        ellipse(x, y, s, s);
    }
}

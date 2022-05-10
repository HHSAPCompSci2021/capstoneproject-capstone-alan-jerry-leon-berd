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
        screen.rebuild();
    }

    public void init(){
        screen = ui.menuScreen;
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
        if(keyCode == 'd') debug = !debug;
        input.register(keyCode);
    }

    @Override
    public void keyReleased(){
        input.remove(keyCode);
    }


    public void background(Color color){
        background(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
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


    //TODO: Change the opaque shape spam of glow into an image
    //Current roadblock: Lack of image color tinting
    public void glow(float size, float increment, Color color, float alpha, Floatc drawer){
        glow(size, increment, color, color, alpha, drawer);
    }

    public void glow(float size, float increment, Color color, float alpha, Interp sizei, Floatc drawer){
        glow(size, increment, color, color, alpha, sizei, Interpf.lin, drawer);
    }

    public void glow(float size, float increment, Color from, Color to, float alpha, Floatc drawer){
        glow(size, increment, from, to, alpha, Interpf.lin, Interpf.lin, drawer);
    }

    public void glow(float size, float increment, Color from, Color to, float alpha, Interp sizei, Floatc drawer){
        glow(size, increment, from, to, alpha, sizei, Interpf.lin, drawer);
    }

    public void glow(float size, float increment, Color from, Color to, float alpha, Interp sizei, Interp colori, Floatc drawer){
        if(glowRendered > 0){
            for(float i = 0;i <= size;i += increment * 10 / glowRendered){
                fill(lerpColor(from.getRGB(), to.getRGB(), colori.get(i / size)), alpha * 10 / glowRendered);
                drawer.get(sizei.get(i / size) * size);
            }
        }else{
            fill(lerpColor(from.getRGB(), to.getRGB(), colori.get(0.5f)), alpha * 20);
            drawer.get(sizei.get(0.5f) * size);
        }
    }


    public void glows(float size, float increment, Color color, float alpha, Floatc drawer){
        glows(size, increment, color, alpha, Interpf.lin, drawer);
    }

    public void glows(float size, float increment, Color color, float alpha, Interp sizei, Floatc drawer){
        glows(size, increment, color, color, alpha, sizei, Interpf.lin, drawer);
    }

    public void glows(float size, float increment, Color from, Color to, float alpha, Interp sizei, Interp colori, Floatc drawer){
        if(glowRendered > 0){
            noFill();
            for(float i = 0;i <= size;i += increment * 10 / glowRendered){
                stroke(lerpColor(from.getRGB(), to.getRGB(), colori.get(i / size)), alpha * 10 / glowRendered);
                drawer.get(sizei.get(i / size) * size);
            }
        }else{
            stroke(lerpColor(from.getRGB(), to.getRGB(), colori.get(0.5f)), alpha * 20);
            drawer.get(sizei.get(0.5f) * size);
        }
    }
}

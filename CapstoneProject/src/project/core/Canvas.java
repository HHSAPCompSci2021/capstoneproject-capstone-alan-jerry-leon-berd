package project.core;

import gameutils.math.*;
import processing.core.*;
import project.core.Content.*;
import project.graphics.*;
import project.ui.screens.*;
import project.world.ship.hulls.Hull.*;
import project.world.ship.weapons.Weapon.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** An extension of PApplet. Represents the canvas of the project. */
public class Canvas extends PApplet{
    private Screen screen;
    private float shake;

    public Canvas(){
    }

    /**
     * Shake the screen with a specified intensity.
     * @param shake intensity
     */
    public void shake(float shake){
        this.shake = max(this.shake, shake);
    }

    /**
     * Returns the amount of shake currently on the screen
     * @return the shake
     */
    public float shake(){
        return shake;
    }

    /**
     * Switch the screen.
     * @param screen screen to switch to
     */
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
        Sprite.loadAll();
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

    /**
     * Very unreliable timer function; Returns true in once for every length specified. Use only for visual effects.
     * @param length amount of frames per true
     * @return true/false depending on the current frame
     */
    public boolean timer(int length){
        return frameCount % length == 0;
    }

    /**
     * Draw a background on the PApplet.
     * @param color the color of the background
     */
    public void background(Color color){
        background(color.getRGB());
    }

    /**
     * Set the tint of the PApplet
     * @param color the color of the tint
     */
    public void tint(Color color){
        tint(color, color.getAlpha());
    }

    /**
     * Set the tint of the PApplet
     * @param color the color of the tint
     * @param alpha the alpha of the color
     */
    public void tint(Color color, float alpha){
        tint(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    /**
     * Set the fill of the PApplet
     * @param color the color of the fill
     */
    public void fill(Color color){
        fill(color, color.getAlpha());
    }

    /**
     * Set the fill of the PApplet
     * @param color the color of the fill
     * @param alpha the alpha of the color
     */
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

    /**
     * Set the stroke of the PApplet
     * @param color the color of the stroke
     */
    public void stroke(Color color){
        stroke(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    /**
     * Set the stroke of the PApplet
     * @param color the color of the stroke
     * @param alpha the alpha of the color
     */
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


    /**
     * Draw a square
     * @param x x coordinate
     * @param y y coordinate
     * @param s length of a side
     */
    public void rect(float x, float y, float s){
        rect(x, y, s, s);
    }

    /**
     * Draw a rotated rectangle
     * @param x x coordinate
     * @param y y coordinate
     * @param w width
     * @param h height
     * @param r rotation
     */
    public void rectr(float x, float y, float w, float h, float r){
        rectr(0, 0, x, y, w, h, r);
    }

    /**
     * Draw a rotated rectangle
     * @param tx translate x (applied with rotation)
     * @param ty translate y (applied with rotation)
     * @param x x coordinate
     * @param y y coordinate
     * @param w width
     * @param h height
     * @param r rotation
     */
    public void rectr(float tx, float ty, float x, float y, float w, float h, float r){
        pushMatrix();
        translate(x + w / 2, y + h / 2);
        rotate(r / 180f * pi);
        rect(tx - w / 2, ty - h / 2, w, h);
        popMatrix();
    }

    /**
     * Draw a centered square
     * @param x x coordinate
     * @param y y coordinate
     * @param s length of a side
     */
    public void rectc(float x, float y, float s){
        rectc(x, y, s, s);
    }

    /**
     * Draw a centered rectangle
     * @param x x coordinate
     * @param y y coordinate
     * @param w width
     * @param h height
     */
    public void rectc(float x, float y, float w, float h){
        rect(x - w / 2, y - h / 2, w, h);
    }

    /**
     * Draw a centered rectangle
     * @param x x coordinate
     * @param y y coordinate
     * @param w width
     * @param h height
     * @param r rotation
     */
    public void rectc(float x, float y, float w, float h, float r){
        rectr(x - w / 2, y - h / 2, w, h, r);
    }

    /**
     * Draw a centered rectangle
     * @param tx translate x (applied with rotation)
     * @param ty translate y (applied with rotation)
     * @param x x coordinate
     * @param y y coordinate
     * @param w width
     * @param h height
     * @param r rotation
     */
    public void rectc(float tx, float ty, float x, float y, float w, float h, float r){
        rectr(tx, ty, x - w / 2, y - h / 2, w, h, r);
    }


    /**
     * Draw a centered circle
     * @param x x coordinate
     * @param y y coordinate
     * @param s radius
     */
    public void ellipse(float x, float y, float s){
        ellipse(x, y, s, s);
    }
}

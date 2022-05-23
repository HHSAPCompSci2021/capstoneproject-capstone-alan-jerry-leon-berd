package project.graphics;

import gameutils.struct.*;
import processing.core.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Represents a sprite. */
public class Sprite{
    public static Seq<Sprite> all = new Seq<>();

    public String path;
    public PImage image;

    /** Creates a sprite with the source png at the specified path and name. */
    public Sprite(){
        all.add(this);
    }

    /**
     * Sets the path and name of this sprite
     * @param path the path
     * @param name the name
     * @return itself, for chaining
     */
    public Sprite set(SpritePath path, String name){
        this.path = path.path + name + ".png";
        return this;
    }

    /** Loads this sprite. */
    public void load(){
        image = canvas.loadImage(path);
    }

    /** Loads all sprites. */
    public static void loadAll(){
        for(Sprite s : Sprite.all) s.load();
    }

    /**
     * Draw an image
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void draw(float x, float y){
        draw(x, y, image.width, image.height);
    }

    /**
     * Draw an image
     * @param x the x coordinate
     * @param y the y coordinate
     * @param tint the tint of the image
     */
    public void draw(float x, float y, float r, Color tint){
        draw(x, y, image.width, image.height, r, tint);
    }

    /**
     * Draw an image
     * @param x the x coordinate
     * @param y the y coordinate
     * @param w th width
     * @param h the height
     */
    public void draw(float x, float y, float w, float h){
        canvas.image(image, x, y, w, h);
    }

    /**
     * Draw an image
     * @param x the x coordinate
     * @param y the y coordinate
     * @param w th width
     * @param h the height
     * @param tint the tint of the image
     */
    public void draw(float x, float y, float w, float h, Color tint){
        draw(x, y, w, h, tint, 255);
    }

    /**
     * Draw an image
     * @param x the x coordinate
     * @param y the y coordinate
     * @param w th width
     * @param h the height
     * @param tint the tint of the image
     * @param alpha the alpha of the tint
     */
    public void draw(float x, float y, float w, float h, Color tint, float alpha){
        canvas.tint(tint, alpha);
        draw(x, y, w, h);
        canvas.tint(255, 255, 255);
    }

    /**
     * Draw an image
     * @param x the x coordinate
     * @param y the y coordinate
     * @param w th width
     * @param h the height
     * @param r the rotation
     */
    public void draw(float x, float y, float w, float h, float r){
        canvas.pushMatrix();
        canvas.translate(x + w / 2, y + h / 2);
        canvas.rotate(r / 180f * pi);
        canvas.image(image, -w / 2, -h / 2, w, h);
        canvas.popMatrix();
    }

    /**
     * Draw an image
     * @param x the x coordinate
     * @param y the y coordinate
     * @param w th width
     * @param h the height
     * @param r the rotation
     * @param tint the tint of the image
     */
    public void draw(float x, float y, float w, float h, float r, Color tint){
        draw(x, y, w, h, r, tint, 255);
    }

    /**
     * Draw an image with top left corner at (x, y), a width w, a height h, a rotation r, the specified tint and alpha. All other draw methods are overloaded variants of this method.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param w th width
     * @param h the height
     * @param r the rotation
     * @param tint the tint of the image
     * @param alpha the alpha of the tint
     */
    public void draw(float x, float y, float w, float h, float r, Color tint, float alpha){
        canvas.tint(tint, alpha);
        draw(x, y, w, h, r);
        canvas.tint(255, 255, 255);
    }

    /**
     * Draw a centered image
     * @param x the x coordinate
     * @param y the y coordinate
     * @param tint the tint of the image
     */
    public void drawc(float x, float y, float r, Color tint){
        draw(x - image.width / 2f, y - image.height / 2f, image.width, image.height, r, tint);
    }

    /**
     * Draw a centered image
     * @param x the x coordinate
     * @param y the y coordinate
     * @param tint the tint of the image
     * @param alpha the alpha of the tint
     */
    public void drawc(float x, float y, float r, Color tint, float alpha){
        draw(x - image.width / 2f, y - image.height / 2f, image.width, image.height, r, tint, alpha);
    }

    /**
     * Draw a centered image
     * @param x the x coordinate
     * @param y the y coordinate
     * @param w th width
     * @param h the height
     */
    public void drawc(float x, float y, float w, float h){
        draw(x - w / 2, y - h / 2, w, h);
    }

    /**
     * Draw a centered image
     * @param x the x coordinate
     * @param y the y coordinate
     * @param w th width
     * @param h the height
     * @param tint the tint of the image
     */

    public void drawc(float x, float y, float w, float h, Color tint){
        draw(x - w / 2, y - h / 2, w, h, tint);
    }

    /**
     * Draw a centered image
     * @param x the x coordinate
     * @param y the y coordinate
     * @param w th width
     * @param h the height
     * @param tint the tint of the image
     * @param alpha the alpha of the tint
     */
    public void drawc(float x, float y, float w, float h, Color tint, float alpha){
        draw(x - w / 2, y - h / 2, w, h, tint, alpha);
    }

    /**
     * Draw a centered image
     * @param x the x coordinate
     * @param y the y coordinate
     * @param w th width
     * @param h the height
     * @param r the rotation
     */
    public void drawc(float x, float y, float w, float h, float r){
        draw(x - w / 2, y - h / 2, w, h, r);
    }

    /**
     * Draw a centered image
     * @param x the x coordinate
     * @param y the y coordinate
     * @param w th width
     * @param h the height
     * @param r the rotation
     * @param tint the tint of the image
     */
    public void drawc(float x, float y, float w, float h, float r, Color tint){
        draw(x - w / 2, y - h / 2, w, h, r, tint);
    }

    /**
     * Draw an image with center at (x, y), a width w, a height h, a rotation r, the specified tint and alpha. All other drawc methods are overloaded variants of this method.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param w th width
     * @param h the height
     * @param r the rotation
     * @param tint the tint of the image
     * @param alpha the alpha of the tint
     */
    public void drawc(float x, float y, float w, float h, float r, Color tint, float alpha){
        draw(x - w / 2, y - h / 2, w, h, r, tint, alpha);
    }

    /** Represents a path in the project. */
    public enum SpritePath{
        none(""),
        sprites("assets/sprites/"),
        bullets("assets/sprites/bullets/"),
        backgrounds("assets/sprites/backgrounds/"),
        effects("assets/sprites/effects/"),
        enemies("assets/sprites/enemies/"),
        ships("assets/sprites/ships/"),
        upgrades("assets/sprites/upgrades/");

        public final String path;

        SpritePath(String path){
            this.path = path;
        }
    }
}

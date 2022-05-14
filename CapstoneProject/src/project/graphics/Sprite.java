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
    public PImage image; //TODO: normalize all sprites (Processing sucks at scaling images)

    public Sprite(String path){
        this.path = path;
        all.add(this);
    }

    /** Creates a sprite with the source png at the specified path and name. */
    public Sprite(SpritePath path, String name){
        this(path.path + name + ".png");
    }

    /** Loads this sprite. */
    public void load(){
        image = canvas.loadImage(path);
    }

    public void draw(float x, float y){
        draw(x, y, image.width, image.height);
    }

    public void draw(float x, float y, float r, Color tint){
        draw(x, y, image.width, image.height, r, tint);
    }


    public void ç(float x, float y, float w){
        draw(x, y, w, image.height * (w / image.width));
    }

    public void drawh(float x, float y, float h){
        draw(x, y, image.width * (h / image.height), h);
    }

    public void draw(float x, float y, float w, float h){
        canvas.image(image, x, y, w, h);
    }

    public void draw(float x, float y, float w, float h, Color tint){
        draw(x, y, w, h, tint, 255);
    }

    public void draw(float x, float y, float w, float h, Color tint, float alpha){
        canvas.tint(tint.getRGB(), alpha);
        draw(x, y, w, h);
    }

    public void draw(float x, float y, float w, float h, float r){
        canvas.pushMatrix();
        canvas.translate(x + w / 2, y + h / 2);
        canvas.rotate(r / 180f * pi);
        canvas.image(image, -w / 2, -h / 2, w, h);
        canvas.popMatrix();
    }

    public void draw(float x, float y, float w, float h, float r, Color tint){
        draw(x, y, w, h, r, tint, 255);
    }

    /** Draw an image with top left corner at (x, y), a width w, a height h, a rotation r, the specified tint and alpha. All other draw methods are overloaded variants of this method. */
    public void draw(float x, float y, float w, float h, float r, Color tint, float alpha){
        canvas.tint(tint.getRGB(), alpha);
        draw(x, y, w, h, r);
    }

    public void drawc(float x, float y, float r, Color tint){
        draw(x - image.width / 2, y - image.height / 2, image.width, image.height, r, tint);
    }

    public void drawc(float x, float y, float r, Color tint, float alpha){
        draw(x - image.width / 2, y - image.height / 2, image.width, image.height, r, tint, alpha);
    }

    public void drawc(float x, float y, float w, float h){
        draw(x - w / 2, y - h / 2, w, h);
    }

    public void drawc(float x, float y, float w, float h, Color tint){
        draw(x - w / 2, y - h / 2, w, h, tint);
    }

    public void drawc(float x, float y, float w, float h, Color tint, float alpha){
        draw(x - w / 2, y - h / 2, w, h, tint, alpha);
    }

    public void drawc(float x, float y, float w, float h, float r){
        draw(x - w / 2, y - h / 2, w, h, r);
    }

    public void drawc(float x, float y, float w, float h, float r, Color tint){
        draw(x - w / 2, y - h / 2, w, h, r, tint);
    }

    /** Draw an image with center at (x, y), a width w, a height h, a rotation r, the specified tint and alpha. All other drawc methods are overloaded variants of this method. */
    public void drawc(float x, float y, float w, float h, float r, Color tint, float alpha){
        draw(x - w / 2, y - h / 2, w, h, r, tint, alpha);
    }

    /** Represents a path in the project. */
    public enum SpritePath{
        sprites("assets/sprites/"),
        bullets("assets/sprites/bullets/"),
        backgrounds("assets/sprites/backgrounds/"),
        effects("assets/sprites/effects/"),
        enemies("assets/sprites/enemies/"),
        ships("assets/sprites/ships/"),
        upgrades("assets/sprites/upgrades/");

        public String path;

        SpritePath(String path){
            this.path = path;
        }
    }
}

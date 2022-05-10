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

    public Sprite(String path){
        this.path = path;
        all.add(this);
    }

    public Sprite(SpritePath path, String name){
        this(path.path + name + ".png");
    }

    public void load(){
        image = canvas.loadImage(path);
        System.out.println(path + ": " + image);
    }

    public void draw(float x, float y){
        draw(x, y, image.width, image.height);
    }

    public void drawh(float x, float y, float h){
        draw(x, y, image.width * (h / image.height), h);
    }

    public void draw(float x, float y, float w, float h){
        draw(x, y, w, h, Color.white);
    }

    public void draw(float x, float y, float w, float h, Color tint){
        draw(x, y, w, h, tint, 255);
    }

    public void draw(float x, float y, float w, float h, Color tint, float alpha){
        canvas.tint(tint.getRGB(), alpha);
        canvas.image(image, x, y, w, h);
    }

    public void draw(float x, float y, float w, float h, float r){
        draw(x, y, w, h, r, Color.white);
    }

    public void draw(float x, float y, float w, float h, float r, Color tint){
        draw(x, y, w, h, r, tint, 255);
    }

    public void draw(float x, float y, float w, float h, float r, Color tint, float alpha){
        canvas.tint(tint.getRGB(), alpha);
        canvas.pushMatrix();
        canvas.translate(x + w / 2, y + h / 2);
        canvas.rotate(r / 180f * pi);
        canvas.image(image, -w / 2, -h / 2, w, h);
        canvas.popMatrix();
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

    /** Represents a path in the project. */
    public enum SpritePath{
        sprites("assets/sprites/"),
        bullets("assets/sprites/bullets/"),
        backgrounds("assets/sprites/backgrounds/"),
        effects("assets/sprites/effects/"),
        enemies("assets/sprites/enemies/"),
        ships("assets/sprites/ships/");


        public String path;

        SpritePath(String path){
            this.path = path;
        }
    }
}

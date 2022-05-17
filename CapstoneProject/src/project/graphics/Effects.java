package project.graphics;

import gameutils.func.*;
import gameutils.math.*;
import project.*;
import project.graphics.Sprite.*;
import project.world.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Contains a list of all effects in the game. */
public class Effects{
    public static Sprite glow, blur, thruster, frame, slant;

    public static Effect
    explosion,
    shockwave,
    fragment,
    laserLine,
    gunfire,
    thrust,
    upgrade,
    trail;

    public void init(){
        glow = new GlowSprite(SpritePath.effects, "glow");
        blur = new GlowSprite(SpritePath.effects, "blur");
        thruster = new Sprite(SpritePath.effects, "thruster");
        frame = new Sprite(SpritePath.effects, "frame");
        slant = new Sprite(SpritePath.effects, "slant");

        shockwave = new Effect(25, e -> e.create(5).set(3, 1).set(4, 1), e -> {
            canvas.noFill();
            for(int i = 1;i < e.data[4] + 1;i++){
                e.stroke(0, 255 * e.fout() / i);
                canvas.strokeWeight(e.fout() / 2f / i * e.data[3]);
                canvas.ellipse(0, 0, e.fin() * 2 * (0.5f + i * 1.5f) * e.data[3], e.fin() * 2 * (0.5f + i * 1.5f) * e.data[3]);
            }
        });
        fragment = new Effect(25, e -> e.create(10 * 2 + 5).rand(0, 10 * 2).set(23, 1).set(24, 5), e -> {
            e.fill(20, 255 * e.fout());
            for(int i = 0;i < e.data[24];i++) canvas.rectc(e.fin() * 10 * e.data[i + 10] * e.data[23], 0, 0, 0, e.fout() * e.data[23], 0.4f * e.data[23], e.data[i] * 360);
        });
        laserLine = new Effect(10, e -> e.create(2), e -> {
            canvas.fill(255, 255, 255, 255 * e.fout());
            canvas.rectc(maxLineLen / 2, 0, 0, 0, maxLineLen, e.fout() * 2 * e.data[1], e.data[0]);
        }).essential(true).follow(true);
        gunfire = new Effect(8, e -> e.create(3), e -> {
            e.fill(0);
            canvas.ellipse(0, 0, 10 * e.fout(), 10 * e.fout());

            canvas.fill(255, 255, 255, 255 * e.fout() * 2 - 255);
            canvas.ellipse(0, 0, 10 * e.fout(), 10 * e.fout());
        }).follow(true);
        thrust = new Effect(35, e -> e.create(6), e -> {
            e.tint(0, e.fout() * 255);
            Tmp.v1.setr(e.data[5] + 90, e.data[4] * e.fin() / 2);
            thruster.drawc(Tmp.v1.x, Tmp.v1.y, e.data[3], e.data[4] * e.fin(), e.data[5]);
            canvas.tint(255, 255, 255, 255 * e.fout() * 2 - 255);
            thruster.drawc(Tmp.v1.x, Tmp.v1.y, e.data[3], e.data[4] * e.fin(), e.data[5]);
        }).follow(true);
        upgrade = new Effect(25, e -> e.create(2), e -> {
            for(int i = 0;i < 3;i++){
                canvas.tint(world.player.color(), 255 * e.fout() / (i + 1));
                world.player.sprite().drawc(0, 0, e.data[0] * 20 * e.fin() * i, e.data[0] * 20 * e.fin() * i, world.player.rotation + 90);
                canvas.tint(255, 255, 255, 100 * e.fout() / (i + 1));
                world.player.sprite().drawc(0, 0, e.data[0] * 20 * e.fin() * i, e.data[0] * 20 * e.fin() * i, world.player.rotation + 90);
            }
        }).follow(true);
        trail = new Effect(5, e -> e.create(6), e -> {
            e.stroke(0, 255 * e.fout());
            canvas.strokeWeight(e.data[5]);
            canvas.line(0, 0, e.data[3] - e.pos.x, e.data[4] - e.pos.y);
            canvas.stroke(255, 255, 255, 100 * e.fout());
            canvas.strokeWeight(e.data[5]);
            canvas.line(0, 0, e.data[3] - e.pos.x, e.data[4] - e.pos.y);
        });
    }

    /** Represents a sprite that is only drawn when glowEnabled is on. */
    public static class GlowSprite extends Sprite{
        public GlowSprite(SpritePath path, String name){
            super(path, name);
        }

        @Override
        public void draw(float x, float y, float w, float h, Color tint, float alpha){
            if(glowEnabled) super.draw(x, y, w, h, tint, alpha);
        }

        @Override
        public void draw(float x, float y, float w, float h, float r, Color tint, float alpha){
            if(glowEnabled) super.draw(x, y, w, h, r, tint, alpha);
        }
    }

    /** Represents a type of effect. Stores the effect renderer and initialization runnables. */
    public static class Effect{
        public Cons<EffectEntity> init;
        public Cons<EffectEntity> drawer;

        public float lifetime;

        public boolean essential, follow;

        public Effect(float lifetime, Cons<EffectEntity> drawer){
            this.lifetime = lifetime;
            this.drawer = drawer;
        }

        public Effect(float lifetime, Cons<EffectEntity> init, Cons<EffectEntity> drawer){
            this(lifetime, drawer);
            this.init = init;
        }

        /** Set whether this effect should still be drawn even when effects are disabled. */
        public Effect essential(boolean essential){
            this.essential = essential;
            return this;
        }

        /** Set whether this effect follows along with it's parent. */
        public Effect follow(boolean follow){
            this.follow = follow;
            return this;
        }

        /** Create an effect at (x, y). */
        public EffectEntity at(float x, float y){
            EffectEntity e = new EffectEntity(this);
            e.pos.set(x, y);
            if(effectsEnabled || essential) world.effects.add(e);
            return e;
        }

        /** Create an effect at (x, y) and run the runnable init on it. */
        public void at(float x, float y, Cons<EffectEntity> init){
            EffectEntity e = at(x, y);
            init.get(e);
        }

        /** Represents a effect. */
        public class EffectEntity extends Entity{
            public Effect effect;

            public float lifetime;
            public float[] data;

            public Pos2 parent;

            public EffectEntity(Effect effect){
                super(null);
                this.effect = effect;
                this.lifetime = effect.lifetime;
                if(init != null) init.get(this);
            }

            /** Create the data array as a float array of length len. */
            public EffectEntity create(int len){
                data = new float[len];
                return this;
            }

            /** Sets all values in the data array in range [start, end - 1] inclusive to random values from 0-1. */
            public EffectEntity rand(int start, int end){
                for(int i = start;i < end;i++) data[i] = random();
                return this;
            }

            /** Sets the values of data[i], data[i+1], and data[i+2] to the rgb of the color, respectively. */
            public EffectEntity color(int i, Color c){
                data[i] = c.getRed();
                data[i + 1] = c.getGreen();
                data[i + 2] = c.getBlue();
                return this;
            }

            /** Sets the value at data[i] to the specified value. */
            public EffectEntity set(int i, float value){
                data[i] = value;
                return this;
            }

            /** Sets the parent of this effect. */
            public EffectEntity parent(Pos2 parent){
                this.parent = parent;
                return this;
            }

            public EffectEntity lifetime(float lifetime){
                this.lifetime = lifetime;
                return this;
            }

            /** Returns the ratio of life to lifetime. */
            public float fin(){
                return life / lifetime;
            }

            /** Returns 1f - fin(). */
            public float fout(){
                return 1f - fin();
            }

            /** Call the fill method of canvas with arguments data[i], data[i+1], and data[i+2], the rgb, respectively. */
            public void fill(int i){
                canvas.fill(data[i], data[i + 1], data[i + 2]);
            }

            /** Call the fill method of canvas with arguments data[i], data[i+1], and data[i+2], the rgb, respectively, with the specified alpha. */
            public void fill(int i, float alpha){
                canvas.fill(data[i], data[i + 1], data[i + 2], alpha);
            }

            public void stroke(int i){
                canvas.stroke(data[i], data[i + 1], data[i + 2]);
            }

            public void stroke(int i, float alpha){
                canvas.stroke(data[i], data[i + 1], data[i + 2], alpha);
            }

            /** Call the tint method of canvas with arguments data[i], data[i+1], and data[i+2], the rgb, respectively. */
            public void tint(int i){
                canvas.tint(data[i], data[i + 1], data[i + 2]);
            }

            /** Call the tint method of canvas with arguments data[i], data[i+1], and data[i+2], the rgb, respectively, with the specified alpha. */
            public void tint(int i, float alpha){
                canvas.tint(data[i], data[i + 1], data[i + 2], alpha);
            }

            @Override
            public void update(){
                life += delta;
            }

            @Override
            public void draw(){
                canvas.pushMatrix();
                canvas.translate(pos.x, pos.y);
                if(follow) canvas.translate(parent.x(), parent.y());
                drawer.get(this);
                canvas.popMatrix();
            }

            @Override
            public boolean keep(){
                return !(life >= lifetime);
            }
        }
    }
}

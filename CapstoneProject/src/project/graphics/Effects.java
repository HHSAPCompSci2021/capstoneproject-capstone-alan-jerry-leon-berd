package project.graphics;

import gameutils.func.*;
import gameutils.math.*;
import project.*;
import project.world.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Contains a list of all effects in the game. */
public class Effects{
    public static Sprite glow, blur, blur2, thruster, frame, slant;

    public static Effect
    explosion,
    shockwave,
    fragment,
    laserLine,
    gunfire,
    thrust,
    upgrade,
    trail;

    /** Initializes all effects. */
    public void init(){
        glow = new GlowSprite().set("glow");
        blur = new GlowSprite().set("blur");
        blur2 = new GlowSprite().set("blur2");
        thruster = new EffectSprite().set("thruster");
        frame = new EffectSprite().set("frame");
        slant = new EffectSprite().set("slant");

        explosion = new Effect(20, e -> e.create(4), e -> {
//            e.tint(0, 255 * e.fout());
//            Effects.glow.drawc(0, 0, e.data[3] * 5 * e.fin(), e.data[3] * 5 * e.fin());
            e.fill(0, 150 * e.fout());
            canvas.ellipse(0, 0, e.data[3] * 3 * e.fin(), e.data[3] * 3 * e.fin());

            Effects.glow.drawc(0, 0, e.data[3] * 5 * e.fin(), e.data[3] * 5 * e.fin(), Color.white, 255 * e.fout());
            canvas.fill(255, 255, 255, 150 * rt2(e.fout()));
            e.stroke(0, 100f * e.fout());
            canvas.strokeWeight(10);
            canvas.ellipse(0, 0, e.data[3] * 3 * e.fout() * e.fout(), e.data[3] * 3 * e.fout() * e.fout());
        });
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
        gunfire = new Effect(15, e -> e.create(3), e -> {
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
                world.player.sprite().drawc(0, 0, e.data[0] * 20 * e.fin() * i, e.data[0] * 20 * e.fin() * i, world.player.rotation() + 90);
                canvas.tint(255, 255, 255, 100 * e.fout() / (i + 1));
                world.player.sprite().drawc(0, 0, e.data[0] * 20 * e.fin() * i, e.data[0] * 20 * e.fin() * i, world.player.rotation() + 90);
            }
        }).follow(true);
        trail = new Effect(5, e -> e.create(6), e -> {
            e.stroke(0);
            canvas.strokeWeight(e.data[5] * e.fout());
            canvas.line(0, 0, e.data[3] - e.pos.x, e.data[4] - e.pos.y);
            canvas.stroke(255, 255, 255, 100 * e.fout());
            canvas.strokeWeight(e.data[5] * e.fout());
            canvas.line(0, 0, e.data[3] - e.pos.x, e.data[4] - e.pos.y);

            canvas.stroke(0, 0, 0, 10);
            canvas.strokeWeight(e.data[5] * e.fout());
            canvas.line(5, 5, e.data[3] - e.pos.x + 5, e.data[4] - e.pos.y + 5);
        });
    }

    protected static class EffectSprite extends Sprite{
        public EffectSprite set(String name){
            super.set(SpritePath.effects, name);
            return this;
        }
    }

    protected static class GlowSprite extends EffectSprite{
        @Override
        public GlowSprite set(String name){
            super.set(name);
            return this;
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

        /**
         * Creates an effect with the specified default lifetime and renderer
         * @param lifetime the default lifetime
         * @param drawer the renderer
         */
        public Effect(float lifetime, Cons<EffectEntity> drawer){
            this.lifetime = lifetime;
            this.drawer = drawer;
        }

        /**
         * Creates an effect with the specified default lifetime, initialization, and renderer
         * @param lifetime the default lifetime
         * @param init the initialization runnable
         * @param drawer the renderer
         */
        public Effect(float lifetime, Cons<EffectEntity> init, Cons<EffectEntity> drawer){
            this(lifetime, drawer);
            this.init = init;
        }

        /**
         * Set whether this effect should still be drawn even when effects are disabled.
         * @param essential whether this is essential
         * @return itself, for chaining
         */
        public Effect essential(boolean essential){
            this.essential = essential;
            return this;
        }

        /**
         * Set whether this effect follows along with it's parent.
         * @param follow whether this effect follows it's parent
         * @return itself, for chaining
         */
        public Effect follow(boolean follow){
            this.follow = follow;
            return this;
        }

        /**
         * Create an effect at (x, y).
         * @param x the x coordinate
         * @param y the y coordinate
         * @return the entity created
         */
        public EffectEntity at(float x, float y){
            EffectEntity e = new EffectEntity(this);
            e.pos.set(x, y);
            if(effectsEnabled || essential) world.effects.add(e);
            return e;
        }

        /**
         * Create an effect at (x, y) and run the runnable init on it.
         * @param x the x coordinate
         * @param y the y coordinate
         * @param init the initialization runnable
         */
        public void at(float x, float y, Cons<EffectEntity> init){
            EffectEntity e = at(x, y);
            init.get(e);
        }

        /** Represents a effect. */
        public class EffectEntity extends Entity{
            /** The effect type. */
            public Effect effect;

            public float lifetime;
            private float[] data;

            public Pos2 parent;

            /**
             * Create an effect entity with specified type
             * @param effect the type
             */
            public EffectEntity(Effect effect){
                super(null);
                this.effect = effect;
                this.lifetime = effect.lifetime;
                if(init != null) init.get(this);
            }

            /**
             * Create the data array as a float array of length len.
             * @param len the length of the float array
             * @return itself, for chaining
             */
            public EffectEntity create(int len){
                data = new float[len];
                return this;
            }

            /**
             * Sets all values in the data array in range [start, end - 1] inclusive to random values from 0-1.
             * @param start the start the range
             * @param end the end of the range
             * @return itself, for chaining
             */
            public EffectEntity rand(int start, int end){
                for(int i = start;i < end;i++) data[i] = random();
                return this;
            }

            /**
             * Sets the values of data[i], data[i+1], and data[i+2] to the rgb of the color, respectively.
             * @param i the index in the array to write the rgb of the color to
             * @param c the color
             * @return itself, for chaining
             */
            public EffectEntity color(int i, Color c){
                data[i] = c.getRed();
                data[i + 1] = c.getGreen();
                data[i + 2] = c.getBlue();
                return this;
            }

            /**
             * Sets the value at data[i] to the specified value.
             * @param i the index
             * @param value the value
             * @return itself, for chaining
             */
            public EffectEntity set(int i, float value){
                data[i] = value;
                return this;
            }

            /**
             * Sets the parent of this effect.
             * @param parent the parent
             * @return itself, for chaining.
             */
            public EffectEntity parent(Pos2 parent){
                this.parent = parent;
                return this;
            }

            /**
             * Set a custom lifetime for this effect
             * @param lifetime the lifetime
             * @return itself, for chaining
             */
            public EffectEntity lifetime(float lifetime){
                this.lifetime = lifetime;
                return this;
            }

            /** Returns the ratio of life to lifetime. */
            @Override
            public float fin(){
                return life / lifetime;
            }

            /** Returns 1f - fin(). */
            public float fout(){
                return 1f - fin();
            }

            /**
             * Call the fill method of canvas with arguments data[i], data[i+1], and data[i+2], the rgb, respectively.
             * @param i the index
             */
            public void fill(int i){
                canvas.fill(data[i], data[i + 1], data[i + 2]);
            }

            /**
             * Call the fill method of canvas with arguments data[i], data[i+1], and data[i+2], the rgb, respectively, with the specified alpha.
             * @param i the index
             * @param alpha the alpha
             */
            public void fill(int i, float alpha){
                canvas.fill(data[i], data[i + 1], data[i + 2], alpha);
            }

            /**
             * Call the stroke method of canvas with arguments data[i], data[i+1], and data[i+2], the rgb, respectively.
             * @param i the index
             */
            public void stroke(int i){
                canvas.stroke(data[i], data[i + 1], data[i + 2]);
            }

            /**
             * Call the stroke method of canvas with arguments data[i], data[i+1], and data[i+2], the rgb, respectively, with the specified alpha.
             * @param i the index
             * @param alpha the alpha
             */
            public void stroke(int i, float alpha){
                canvas.stroke(data[i], data[i + 1], data[i + 2], alpha);
            }

            /**
             * Call the tint method of canvas with arguments data[i], data[i+1], and data[i+2], the rgb, respectively.
             * @param i the index
             */
            public void tint(int i){
                canvas.tint(data[i], data[i + 1], data[i + 2]);
            }

            /**
             * Call the tint method of canvas with arguments data[i], data[i+1], and data[i+2], the rgb, respectively, with the specified alpha.
             * @param i the index
             * @param alpha the alpha
             */
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

package project.graphics;

import gameutils.func.*;
import gameutils.math.*;
import project.core.Content.*;
import project.graphics.Sprite.*;
import project.world.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Contains a list of all effects in the game. */
public class Effects implements ContentList{
    public static Sprite glow, blur;

    public static Effect
    explosion,
    shockwave1,
    shockwave3,
    fragment3,
    fragment5,
    fragment10,
    laserLine,
    gunfire;

    public void load(){
        glow = new Sprite(SpritePath.effects, "glow");
        blur = new Sprite(SpritePath.effects, "blur");

        explosion = new Effect(10, e -> {
            canvas.fill(255, 255, 255, 100 * e.fout());
            canvas.ellipse(0, 0, 10 * e.fin(), 10 * e.fin());
        });
        shockwave1 = new Effect(20, e -> {
            canvas.noFill();
            canvas.stroke(255, 255, 255, 255 * e.fout());
            canvas.strokeWeight(e.fout() / 4f);
            canvas.ellipse(0, 0, e.fin() * 10, e.fin() * 10);
        });
        shockwave3 = new Effect(20, e -> {
            canvas.noFill();
            for(int i = 1;i < 4;i++){
                canvas.stroke(255, 255, 255, 255 * e.fout() / i);
                canvas.strokeWeight(e.fout() / 2f / i);
                canvas.ellipse(0, 0, e.fin() * 2 * (0.5f + i * 1.5f), e.fin() * 2 * (0.5f + i * 1.5f));
            }
        });
        fragment3 = new Effect(25, e -> e.create(3 * 2).rand(0, 3 * 2), e -> {
            canvas.fill(255, 255, 255, 255 * e.fout());
            for(int i = 0;i < 3;i++) canvas.rectc(e.fin() * 10 * e.data[i + 3], 0, 0, 0, e.fout(), 0.4f, e.data[i] * 360);
        });
        fragment5 = new Effect(25, e -> e.create(5 * 2).rand(0, 5 * 2), e -> {
            canvas.fill(255, 255, 255, 255 * e.fout());
            for(int i = 0;i < 5;i++) canvas.rectc(e.fin() * 10 * e.data[i + 5], 0, 0, 0, e.fout(), 0.4f, e.data[i] * 360);
        });
        fragment10 = new Effect(25, e -> e.create(10 * 2).rand(0, 10 * 2), e -> {
            canvas.fill(255, 255, 255, 255 * e.fout());
            for(int i = 0;i < 10;i++) canvas.rectc(e.fin() * 10 * e.data[i + 10], 0, 0, 0, e.fout(), 0.4f, e.data[i] * 360);
        });
        laserLine = new Effect(10, e -> e.create(1), e -> {
            canvas.fill(255, 255, 255, 255 * e.fout());
            canvas.rectc(maxLineLen / 2, 0, 0, 0, maxLineLen, e.fout() * 2, e.data[0]);
        }).essential(true);
        gunfire = new Effect(8, e -> e.create(3), e -> {
            e.fill(0);
            canvas.ellipse(0, 0, 10 * e.fout(), 10 * e.fout());

            canvas.fill(255, 255, 255, 255 * e.fout() * 2 - 255);
            canvas.ellipse(0, 0, 10 * e.fout(), 10 * e.fout());
        }).follow(true);
    }

    /** Represents a type of effect. Stores the effect renderer and initialization runnables. */
    public class Effect extends Type{
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

        public Effect essential(boolean essential){
            this.essential = essential;
            return this;
        }

        public Effect follow(boolean follow){
            this.follow = follow;
            return this;
        }

        @Override
        public ContentType type(){
            return ContentType.effect;
        }

        @Override
        public EffectEntity create(){
            return new EffectEntity(this);
        }

        public EffectEntity at(float x, float y){
            EffectEntity e = create();
            e.pos.set(x, y);
            if(effectsEnabled || essential) world.effects.add(e);
            return e;
        }

        public void at(float x, float y, Cons<EffectEntity> init){
            EffectEntity e = at(x, y);
            init.get(e);
        }

        /** Represents a effect. */
        public class EffectEntity extends Entity{
            public float scale;
            public float[] data;

            public Pos2 parent;

            public EffectEntity(Effect effect){
                super(effect);
                this.scale = 1;
                if(init != null) init.get(this);
            }

            public EffectEntity create(int len){
                data = new float[len];
                return this;
            }

            public EffectEntity rand(int start, int end){
                for(int i = start;i < end;i++) data[i] = random();
                return this;
            }

            public EffectEntity color(int i, Color c){
                data[i] = c.getRed();
                data[i + 1] = c.getGreen();
                data[i + 2] = c.getBlue();
                return this;
            }

            public EffectEntity set(int i, float value){
                data[i] = value;
                return this;
            }

            public EffectEntity scale(float scale){
                this.scale = scale;
                return this;
            }

            public EffectEntity parent(Pos2 parent){
                this.parent = parent;
                return this;
            }

            public float fin(){
                return life / lifetime;
            }

            public float fout(){
                return 1f - fin();
            }

            public void fill(int i){
                canvas.fill(data[i], data[i + 1], data[i + 2]);
            }

            @Override
            public void update(){
                life++;
            }

            @Override
            public void draw(){
                canvas.pushMatrix();
                canvas.translate(pos.x, pos.y);
                if(follow) canvas.translate(parent.x(), parent.y());
                canvas.scale(scale);
                drawer.get(this);
                canvas.popMatrix();
            }

            @Override
            public boolean keep(){
                return !(life >= lifetime);
            }

            @Override
            public Effect type(){
                return (Effect)type;
            }
        }
    }
}

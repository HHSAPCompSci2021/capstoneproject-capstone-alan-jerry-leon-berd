package project.game;

import project.*;
import project.core.Events.Event;
import project.core.Rules.*;
import project.graphics.*;
import project.world.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Represents an orb of experience. */
public class Experience extends Entity{
    private float amount;

    public Experience(){
        super(null);
    }

    /** Set the amount of experience of this orb. */
    public Experience amount(float amount){
        this.amount = amount;
        return this;
    }

    @Override
    public float size(){
        return 5;
    }

    @Override
    public void update(){
        super.update();
        life += delta;
        if(dst(pos, world.player) < expRange) vel.add(Tmp.v1.set(world.player.pos).sub(pos).nor().scl(0.5f));
        if(dst(pos, world.player) < world.player.size() + 5){
            world.player.exp += amount * rules.mult(Rule.expGain, Team.player);
            amount = 0;
            events.call(Event.expGain);
        }
        vel.scl(1f - expDrag);
    }

    @Override
    public void draw(){
        canvas.fill(255, 255, 255, 200);
        canvas.ellipse(pos.x, pos.y, 5, 5);
    }

    @Override
    public void remove(){
        Effects.fragment.at(pos.x, pos.y, e -> e.color(20, Color.white).set(23, size()));
    }

    @Override
    public boolean keep(){
        return amount > 0 && world.bounds.contains(pos) && life < expLifetime;
    }
}

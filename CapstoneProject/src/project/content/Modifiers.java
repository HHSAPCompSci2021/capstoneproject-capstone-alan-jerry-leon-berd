package project.content;

import project.core.Content.*;
import project.core.Rules.*;
import project.world.modifiers.*;

import static project.core.Rules.Rule.*;

/** Contains and loads all modifiers in the game. */
public class Modifiers implements ContentList{
    public static Modifier
    theVoid,
    hullStrength, regeneration,
    shieldDurability,
    blastRadius;

    @Override
    public void load(){
        theVoid = new Modifier("the-void"){{
            addPro("You stare into the void...");
            addPro("...And the void stares into you");
        }};
//        hullStrength = new Modifier("hull-strength"){{
//            mult(maxHull, 0.2f);
//        }};
        regeneration = new Modifier("regeneration"){{
            addPro("Hull regenerates at a rate proportional to maximum hull");
            mult(hullRegen, 0.4f);
        }};
        shieldDurability = new Modifier("shield-durability"){{
            mult(maxShields, 0.2f);
        }};
        blastRadius = new Modifier("blast-radius"){{
            addPro("Non-explosive projectiles deal 15% damage as blast damage to other targets");
            addPro("Triggers when the projectile is destroyed on impact");
            add(Rule.blastRadius, 50);
            add(Rule.blastDamage, 15);
        }};
    }
}

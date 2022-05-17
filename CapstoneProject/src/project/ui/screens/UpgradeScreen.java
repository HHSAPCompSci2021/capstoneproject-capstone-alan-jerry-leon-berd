package project.ui.screens;

import gameutils.struct.*;
import project.content.*;
import project.core.Content.*;
import project.core.Events.Event;
import project.core.UI.*;
import project.graphics.*;
import project.ui.List;
import project.ui.*;
import project.ui.drawables.*;
import project.ui.interactables.Button;
import project.ui.interactables.*;
import project.world.*;
import project.world.modifiers.*;
import project.world.ship.*;
import project.world.ship.Hull.*;
import project.world.ship.Shield.*;
import project.world.ship.weapons.*;
import project.world.ship.weapons.Weapon.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;

/** Contains all the UI of an upgrade screen. */
public class UpgradeScreen extends PauseScreen{
    public Modifier[] choices;
    public Table selection;
    public Table description;
    public List stats;
    public int selected, last;
    public float hexSize = 75, fadein;

    public void def(Table t, Hexagon hex){
        t.drawable(d -> {
            d.drawer(drawer -> {
                canvas.tint(255, 255, 255);
                def(Effects.frame, d, 2.2f);
            });
            d.x(hex.x).y(hex.y).width(hex.width).height(hex.width).alignX(AlignX.center).alignY(AlignY.center);
        });
    }

    public void def(Button button, int i){
        button.drawer(b -> {
            canvas.tint(255, 255, 255);
            def(choices[i].sprite, b, 1f);
        }).width(hexSize).height(hexSize);
        button.hover(b -> selected = i);

        button.press(b -> select(choices[i]));
        button.alignX(AlignX.center).alignY(AlignY.center);
    }

    public void def(Sprite sprite, Table t, float s){
        sprite.drawc(t.width() / 2, t.height() / 2, t.width() * s, t.width() / sprite.image.width * sprite.image.height * s);
    }

    public void select(Modifier mod){
        if(mod == Modifiers.theVoid) return;

        if(mod instanceof Hull) world.player.hull = (HullInstance)mod.create();
        else if(mod instanceof Weapon) world.player.weapon = (WeaponInstance)mod.create();
        else if(mod instanceof Shield) world.player.shield = (ShieldInstance)mod.create();
        else world.player.addMod(mod);

        world.player.spent++;
        events.call(Event.modChange);

        if(world.player.spent >= world.player.level) canvas.screen(ui.gameScreen);
        else rebuild();
        reroll();
    }

    public void choose(Seq<Type> from){
        Seq<Type> copy = new Seq<>(from.list());
        for(int i = 0;i < 7;i++){
            if(copy.size == 0) choices[i] = Modifiers.theVoid;
            else{
                int chosen = randInt(0, copy.size - 1);
                choices[i] = (Modifier)copy.get(chosen);
                copy.remove(chosen);
            }
        }
    }

    public void reroll(){
        if(world.player.spent == 0) choose(content.list(ContentType.hull));
        if(world.player.spent == 1) choose(content.list(ContentType.weapon));
        if(world.player.spent == 2) choose(content.list(ContentType.shield));
        if(world.player.spent >= 3) choose(content.list(ContentType.modifier));

        choices[7] = (Modifier)content.list(ContentType.hull).get(randInt(0, content.list(ContentType.hull).size - 1));
        choices[8] = (Modifier)content.list(ContentType.weapon).get(randInt(0, content.list(ContentType.weapon).size - 1));
        choices[9] = (Modifier)content.list(ContentType.shield).get(randInt(0, content.list(ContentType.shield).size - 1));
    }

    public void stats(){
        if(selected == -1) return;

        stats.clear();
        stats.text(text -> text.update(t -> ((Text)t).text(format(choices[selected].tag + ": " + choices[selected].name)).size(30)));
        stats.row(25);
        stats.drawable(d -> d.drawer(drawer -> Effects.blur.draw(0, 0, 400, 4, Color.white)));

        stats.row(20);
        for(String str : choices[selected].pros){
            stats.text(text -> text.text(str).size(20).x(10));
            stats.row(5);
        }

        stats.row(10);
        for(String str : choices[selected].cons){
            stats.text(text -> text.text(str).size(20).x(10).color(Pal.healthRed));
            stats.row(5);
        }
    }

    public String format(String name){
        StringBuilder res = new StringBuilder();
        for(int i = 0;i < name.length();i++){
            if(name.charAt(i) == '-') res.append(' ');
            else res.append(Character.toUpperCase(name.charAt(i)));
            res.append(' ');
        }
        return res.substring(0, res.length() - 1);
    }

    @Override
    public void init(){
        choices = new Modifier[10];

        reroll();
    }

    @Override
    public void rebuild(){
        fadein = 0;

        selection = new Table(t -> {
            t.text(text -> text.text("U P G R A D E").size(40).y(-200).alignX(AlignX.center));

            t.text(text -> text.text("C H O O S E   M O D").size(25).y(-150).alignX(AlignX.center));

            Hexagon base = new Hexagon(), hull = new Hexagon();
            base.width(hexSize);
            hull.width(hexSize).y(290);
            Hexagon[] all = new Hexagon[]{base, base.left(), base.topLeft(), base.topRight(), base.right(), base.bottomRight(), base.bottomLeft(), hull, hull.topLeft(), hull.topRight()};

            for(int i = 0;i < 7;i++){
                def(t, all[i]);
            }

            for(int i = 0;i < 7;i++){
                int j = i;
                t.add(all[i].button(button -> def(button, j)));
            }

            if(world.player.spent >= 3){
                t.text(text -> text.text("C H A N G E   G E A R").size(25).y(135).alignX(AlignX.center));

                for(int i = 0;i < 3;i++){
                    int j = i + 7;
                    def(t, all[j]);
                }

                for(int i = 0;i < 3;i++){
                    int j = i + 7;
                    t.add(all[j].button(button -> def(button, j)));
                }
            }

            t.add(new Hexagon(hex -> {
                hex.width(hexSize);
                hex.drawable(d -> {
                    d.drawer(drawer -> {
                        if(selected == -1) return;
                        canvas.tint(220, 220, 220, 200);
                        def(Effects.frame, d, 2f);
                    });
                    d.width(hexSize).height(hexSize).alignX(AlignX.center).alignY(AlignY.center);
                });
                hex.update(h -> {
                    if(selected == -1) return;
                    hex.x(all[selected].x).y(all[selected].y);
                });
            }));

            t.update(table -> selected = -1);
        }).x(width * 0.35f).y(height * 0.35f);

        description = new Tooltip(tip -> {
            tip.drawable(d -> d.drawer(drawer -> Effects.slant.draw(0, 0, 300, 300, Color.black, 150)));

            tip.translated(true).draw(table -> selected != -1);
            tip.add(new List(list -> {
                stats = list;
                list.x(20).y(10);
                stats();
            })).y(20);
        }).x(width * 0.45f).y(50);
    }

    @Override
    public void update(){
        super.update();

        if(selected != last){
            last = selected;
            stats();
        }

        fadein += 5;
    }


    @Override
    public void draw(){
        ui.gameScreen.draw();

        canvas.fill(255, 255, 255, 20);
        canvas.rect(0, 0, width, height);

        description.process();
        selection.process();

        canvas.fill(0, 0, 0, 100 - fadein);
        canvas.rect(0, 0, width, height);
    }
}

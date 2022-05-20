package project.ui.screens;

import gameutils.func.*;
import processing.core.*;
import project.core.UI.*;
import project.graphics.*;
import project.ui.*;
import project.ui.interactables.*;

import static java.lang.Math.*;
import static project.Vars.*;

public class LoseScreen extends Screen{
    public Table side;
    public Cons<Button> buttonHover = b -> {
        Effects.blur.draw(100, 0, 50, b.height(), Pal.opaqueWhite);
        Effects.blur2.draw(50, 0, 50, b.height(), Pal.opaqueWhite);
    };
    public float time;

    @Override
    public void rebuild(){
        canvas.cursor(PConstants.ARROW);
        side = new Table(table -> table.add(new List(list -> {
            list.text(text -> text.text("G A M E  O V E R").size(100).x(100).y(100).alignX(AlignX.center).color(Pal.opaqueWhite).alpha(t -> min(time, 255)));
            list.row(20);
            list.button(button -> {
                button.hover = buttonHover;
                button.press(b -> canvas.exit()).text(text -> text.text("QUIT").size(35).x(100).alignX(AlignX.center).color(Pal.opaqueWhite).alpha(t -> min(time, 255))).width(150);
            }).alignX(AlignX.center); //Exit cuz play again is broken
        }))).x(width / 2f - 100).y(10);
    }

    @Override
    public void update(){
        time++;

        world.update();
        delta = max(1f - time / 300, 0);
    }

    @Override
    public void draw(){
        ui.gameScreen.draw();

        canvas.fill(0, 0, 0, 100);
        canvas.rect(0, 0, width + 20, height + 50);

        side.process();
    }
}

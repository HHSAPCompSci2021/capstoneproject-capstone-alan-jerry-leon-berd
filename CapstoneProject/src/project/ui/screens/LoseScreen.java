package project.ui.screens;

import gameutils.func.*;
import project.core.UI.*;
import project.graphics.*;
import project.ui.*;
import project.ui.interactables.*;

import static java.lang.Math.*;
import static project.Vars.*;

public class LoseScreen extends Screen{
    public Table side;
    public Cons<Button> buttonHover = b -> {
        Effects.blur.draw(100, 0, 125, b.height(), Pal.opaqueWhite);
        Effects.blur2.draw(-25, 0, 125, b.height(), Pal.opaqueWhite);
    };

    @Override
    public void rebuild(){
        side = new Table(table -> table.add(new List(list -> {
            list.text(text -> text.text("G A M E  O V E R").size(100).x(100).y(100).alignX(AlignX.center).color(Pal.opaqueWhite));
            list.row(20);
            list.button(button -> {
                button.hover = buttonHover;
                button.press(b -> canvas.screen(ui.menuScreen)).text(text -> text.text("PLAY AGAIN").size(35).x(100).alignX(AlignX.center).color(Pal.opaqueWhite)).width(150);
            }).alignX(AlignX.center);
        }))).x(width / 2f - 100).y(10);
    }

    @Override
    public void update(){
        ui.gameScreen.update();
        delta = max(delta - 0.01f, -100000);
    }

    @Override
    public void draw(){
        canvas.pushMatrix();
        canvas.translate(-canvas.mouseX / 50f, -canvas.mouseY / 50f);
        canvas.tint(255, 255, 255);
        ui.gameScreen.draw();
        canvas.fill(0, 0, 0, 100);
        canvas.rect(0, 0, width + 20, height + 50);
        canvas.popMatrix();

        side.process();
    }
}

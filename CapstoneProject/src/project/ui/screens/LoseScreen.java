package project.ui.screens;

import gameutils.func.*;
import project.core.UI.*;
import project.graphics.*;
import project.ui.List;
import project.ui.*;
import project.ui.interactables.Button;

import java.awt.*;

import static java.lang.Math.*;
import static project.Vars.*;

public class LoseScreen extends Screen{
    public Table side;
    public float sidex = 0;
    public Cons<Button> buttonHover = b -> Effects.blur.draw(-50, 0, 350, b.height(), Pal.opaqueWhite);

    @Override
    public void rebuild(){
        side = new Table(table -> table.add(new List(list -> {
            list.text(text -> text.text("G A M E  O V E R").size(100).y(100).alignX(AlignX.center).color(Pal.opaqueWhite));
            list.row(20);
            list.button(button -> {
                button.hover = buttonHover;
                button.press(b -> canvas.screen(ui.menuScreen)).text(text -> text.text("PLAY AGAIN").size(35).color(Pal.opaqueWhite)).width(150);
            }).alignX(AlignX.center);
        }))).x(width/2f).y(10);
    }

    @Override
    public void update(){
        float wanted = input.mouse.x < 200 ? 200 : 0;
        sidex += (wanted - sidex) / 10;

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
        canvas.rect(0, 0, width, height);
        canvas.popMatrix();

        Effects.blur.draw(sidex - 300, 0, 300, height, Color.black);

        side.process();
    }
}

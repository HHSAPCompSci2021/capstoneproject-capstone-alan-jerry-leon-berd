package project.ui.screens;

import project.content.*;
import project.graphics.*;
import project.ui.*;
import project.ui.drawables.*;
import project.world.modifiers.*;
import project.world.ship.weapons.*;
import project.world.ship.weapons.Weapon.*;

import static project.Vars.*;

/** Contains all the UI of an upgrade screen. */
public class UpgradeScreen extends Screen{
    public Modifier[] choices;
    public Table selection;
    public Table description;
    public Modifier selected;

    public void def(Sprite s, Table t){
        s.drawc(t.width() / 2, t.height() / 2, t.width(), t.width() / s.image.width * s.image.height);
    }

    @Override
    public void rebuild(){
        choices = new Modifier[7];
        choices[0] = Weapons.salvo;
        choices[1] = Weapons.salvo;
        choices[2] = Weapons.salvo;
        choices[3] = Weapons.salvo;
        choices[4] = Weapons.salvo;
        choices[5] = Weapons.flak;
        choices[6] = Weapons.blaster;

        selection = new Table(t -> {
            Hexagon base = new Hexagon();
            base.width(100);
            Hexagon[] all = new Hexagon[]{base, base.left(), base.topLeft(), base.topRight(), base.right(), base.bottomRight(), base.bottomLeft()};

            for(int i = 0;i < 7;i++){
                int j = i;
                t.add(all[i].button(button -> {
                    button.drawer(b -> {
                        canvas.tint(255, 255, 255);
                        def(choices[j].sprite, b);
                    }).width(100).height(100);
                    button.hover(b -> {
                        canvas.tint(255, 255, 255, 50);
                        def(Effects.frame, b);
                        selected = choices[j];
                    });

                    button.press(b -> {
                        if(choices[j] instanceof Weapon){
                            world.player.weapon = (WeaponInstance)choices[j].create();
                            canvas.screen(ui.gameScreen);
                        }
                        world.player.spent++;
                    });
                }));
            }
        }).x(250).y(250);
    }

    @Override
    public void draw(){
        ui.gameScreen.draw();

//        canvas.fill(255, 255, 255, 20);
//        canvas.rect(0, 0, width, height);

//        canvas.fill(0, 0, 0, 200);
//        canvas.textSize(30);
//        canvas.textAlign(canvas.CENTER, canvas.TOP);
//        canvas.text("UPGRADE", width / 2f, 5);

        selection.process();
    }
}

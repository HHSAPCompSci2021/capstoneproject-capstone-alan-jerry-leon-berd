package project.ui.screens;

import gameutils.func.*;
import project.core.Input.*;
import project.core.UI.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.ui.List;
import project.ui.*;
import project.ui.interactables.Button;
import project.ui.interactables.*;

import java.awt.*;

import static project.Vars.*;

/** Contains all the UI of a menu screen. */
public class MenuScreen extends Screen{
    public Table side;
    public Sprite background = new Sprite(SpritePath.backgrounds, "space3");
    public int menu = 0;
    public float sidex = 0;

    public Cons<Button> buttonHover = b -> {
        Effects.blur.draw(-50, 0, 350, b.height(), Pal.opaqueWhite);
    };

    public MenuScreen(){
    }

    public void def(Slider slider){
        slider.color(s -> s.bounds().contains(input.mouse) ? Pal.opaqueWhite : Pal.opaqueGray);
        slider.drawer(s -> {
            canvas.rectc(s.width() / 2, s.height() / 2, s.width(), s.height() / 2);
            canvas.rectc(s.width() * slider.value(), s.height() / 2, 5, s.height());
        });
    }

    @Override
    public void rebuild(){
        side = new Table(table -> {
            table.add(new List(list -> {
                list.text(text -> text.text("N O V A   S U B R I F T").size(60).color(Pal.opaqueWhite));
                list.row(20);
                if(menu == 0){
                    list.button(button -> {
                        button.hover = buttonHover;
                        button.press(b -> canvas.screen(ui.gameScreen)).text(text -> text.text("PLAY").size(35).color(Pal.opaqueWhite)).width(150);
                    });
                    list.row(10);
                    list.button(button -> {
                        button.hover = buttonHover;
                        button.press(b -> {
                            menu = 1;
                            rebuild();
                        }).text(text -> text.text("SETTINGS").size(35).color(Pal.opaqueWhite)).width(150);
                    });
                }else if(menu == 1){
                    list.text(text -> text.text("SETTINGS").size(40).color(Pal.opaqueWhite));
                    list.row(10);
                    list.button(button -> {
                        button.hover = buttonHover;
                        button.press(b -> {
                            menu = 2;
                            rebuild();
                        }).text(text -> text.text("KEYBINDS").size(30).color(Pal.opaqueWhite));
                    });
                    list.row(10);
                    list.button(button -> {
                        button.hover = buttonHover;
                        button.press(b -> {
                            menu = 3;
                            rebuild();
                        }).text(text -> text.text("GRAPHICS").size(30).color(Pal.opaqueWhite));
                    });
                }else if(menu == 2){
                    list.text(text -> text.text("KEYBINDS").size(40).color(Pal.opaqueWhite));
                    list.row(10);
                    for(KeyBind k : KeyBind.all){
                        list.text(text -> text.text(k.name + ": " + k.value).size(20).color(Pal.opaqueWhite));
                        list.row(5);
                    }
                }else if(menu == 3){
                    list.text(text -> text.text("GRAPHICS").size(40).color(Pal.opaqueWhite));
                    list.row(10);
                    list.button(button -> {
                        button.hover = buttonHover;
                        button.press(b -> {
                            glowEnabled = !glowEnabled;
                            rebuild();
                        }).text(text -> text.text("GLOW: " + (glowEnabled ? "ON" : "OFF")).size(30).color(Pal.opaqueWhite));
                    });
                    list.row(10);
                    list.text(text -> text.text("UI SCALE").size(30).color(Pal.opaqueWhite));
                    list.row(10);
                    list.slider(slider -> {
                        def(slider);
                        slider.slide(f -> {
                            UIscale = f * 1.5f + 0.5f;
                            rebuild();
                            input.inputs.clear();
                        });
                        slider.value(() -> (UIscale - 0.5f) / 1.5f).width(150).height(20);
                        slider.tooltip(t -> {
                            t.text(text -> {
                                text.text("(ABSOLUTELY BROKEN)").size(20).color(Color.white);
                                text.alignX(AlignX.center).alignY(AlignY.bottom);
                            });
                            t.update(tip -> tip.x(slider.x() + slider.width() * slider.value()).y(slider.y() - 10));
                        });
                    });
                    list.row(10);
                    list.button(button -> {
                        button.hover = buttonHover;
                        button.press(b -> {
                            effectsEnabled = !effectsEnabled;
                            rebuild();
                        }).text(text -> text.text("EFFECTS: " + (effectsEnabled ? "ON" : "OFF")).size(30).color(Pal.opaqueWhite));
                    });
                    list.row(10);
                    list.text(text -> text.text("SCREEN SHAKE").size(30).color(Pal.opaqueWhite));
                    list.row(10);
                    list.slider(slider -> {
                        def(slider);
                        slider.slide(f -> {
                            screenShake = f * 10;
                            rebuild();
                        });
                        slider.value(() -> screenShake / 10).width(150).height(20);
                        slider.tooltip(t -> {
                            t.text(text -> {
                                text.text("" + (int)(screenShake * 10)).size(20).color(Color.white);
                                text.alignX(AlignX.center).alignY(AlignY.bottom);
                            });
                            t.update(tip -> tip.x(slider.x() + slider.width() * slider.value()).y(slider.y() - 10));
                        });
                    });
                }
            }));
            if(menu > 0) table.button(button -> {
                button.hover = buttonHover;
                button.press(b -> {
                    menu = menu == 1 ? 0 : 1;
                    rebuild();
                }).text(text -> text.text("<").size(30).color(Pal.opaqueWhite)).width(150).y(height - 80);
            });
        }).x(20).y(10);
    }

    @Override
    public void update(){
        float wanted =
        menu >= 1 ? 250 :
        input.mouse.x < 200 ? 200 : 0;

        sidex += (wanted - sidex) / 10;
    }

    @Override
    public void draw(){
        canvas.pushMatrix();
        canvas.translate(-canvas.mouseX / 50f, -canvas.mouseY / 50f);
        background.drawh(-125, 0, height);
        canvas.popMatrix();

        Effects.blur.draw(sidex - 300, 0, 300, height, Color.black);

        side.process();
    }
}

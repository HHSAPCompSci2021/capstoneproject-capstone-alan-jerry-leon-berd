package project.ui.screens;

import gameutils.func.*;
import processing.core.*;
import project.core.Input.*;
import project.core.*;
import project.core.UI.*;
import project.graphics.*;
import project.graphics.Sprite.*;
import project.ui.List;
import project.ui.*;
import project.ui.interactables.Button;
import project.ui.interactables.*;

import java.awt.*;

import static project.Vars.*;

/**
 * Contains all the UI of a menu screen.
 */
public class MenuScreen extends Screen{
    private Table side;
    private Sprite background = new Sprite().set(SpritePath.backgrounds, "space3");
    private int menu = 0;
    private float sidex = 0;
    private Cons<Button> buttonHover = b -> Effects.blur.draw(-50, 0, sidex + 33, b.height(), Pal.opaqueWhite);

    private void def(Slider slider){
        slider.color(s -> s.bounds().contains(input.mouse) ? Pal.opaqueBlack : Pal.opaqueGray);
        slider.drawer(s -> {
            canvas.rectc(s.width() / 2, s.height() / 2, s.width(), s.height() / 2);
            canvas.rectc(s.width() * slider.value(), s.height() / 2, 5, s.height());
        });
    }

    @Override
    public void rebuild(){
        canvas.cursor(PConstants.ARROW);
        side = new Table(table -> {
            table.add(new List(list -> {
                list.text(text -> text.text("N O V A   S U B R I F T").size(60).color(Pal.opaqueBlack));
                list.row(20);
                if(menu == 0){
                    list.button(button -> {
                        button.hover(buttonHover);
                        button.press(b -> canvas.screen(ui.gameScreen)).text(text -> text.text("PLAY").size(35).color(Pal.opaqueBlack)).width(150);
                    });
                    list.row(10);
                    list.button(button -> {
                        button.hover(buttonHover);
                        button.press(b -> {
                            menu = 1;
                            rebuild();
                        }).text(text -> text.text("SETTINGS").size(35).color(Pal.opaqueBlack)).width(150);
                    });
                    list.row(10);
                    list.button(button -> {
                        button.hover(buttonHover);
                        button.press(b -> canvas.exit()).text(text -> text.text("EXIT").size(35).color(Pal.opaqueBlack)).width(150);
                    });
                }else if(menu == 1){
                    list.text(text -> text.text("SETTINGS").size(40).color(Pal.opaqueBlack));
                    list.row(10);
                    list.button(button -> {
                        button.hover(buttonHover);
                        button.press(b -> {
                            menu = 2;
                            rebuild();
                        }).text(text -> text.text("KEYBINDS").size(30).color(Pal.opaqueBlack));
                    });
                    list.row(10);
                    list.button(button -> {
                        button.hover(buttonHover);
                        button.press(b -> {
                            menu = 3;
                            rebuild();
                        }).text(text -> text.text("GRAPHICS").size(30).color(Pal.opaqueBlack));
                    });
                    list.row(10);
                    list.button(button -> {
                        button.hover(buttonHover);
                        button.press(b -> {
                            menu = 4;
                            rebuild();
                        }).text(text -> text.text("SOUNDS").size(30).color(Pal.opaqueBlack));
                    });
                }else if(menu == 2){
                    list.text(text -> text.text("KEYBINDS").size(40).color(Pal.opaqueBlack));
                    list.row(10);
                    for(KeyBind k : KeyBind.all){
                        list.text(text -> text.text(k.name + ": " + k.value).size(20).color(Pal.opaqueBlack));
                        list.row(5);
                    }
                }else if(menu == 3){
                    list.text(text -> text.text("GRAPHICS").size(40).color(Pal.opaqueBlack));
                    list.row(10);
                    list.button(button -> {
                        button.hover(buttonHover);
                        button.press(b -> {
                            glowEnabled = !glowEnabled;
                            rebuild();
                        }).text(text -> text.text("GLOW: " + (glowEnabled ? "ON" : "OFF")).size(30).color(Pal.opaqueBlack));
                    });
                    list.row(10);
                    list.text(text -> text.text("UI SCALE").size(30).color(Pal.opaqueBlack));
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
                                text.text("" + (int)(slider.value() * 100)).size(20).color(Color.white);
                                text.alignX(AlignX.center).alignY(AlignY.bottom);
                            });
                            t.update(tip -> tip.x(slider.x() + slider.width() / UIscale * slider.value()).y(slider.y() - 10));
                        });
                    });
                    list.row(10);
                    list.button(button -> {
                        button.hover(buttonHover);
                        button.press(b -> {
                            effectsEnabled = !effectsEnabled;
                            rebuild();
                        }).text(text -> text.text("EFFECTS: " + (effectsEnabled ? "ON" : "OFF")).size(30).color(Pal.opaqueBlack));
                    });
                    list.row(10);
                    list.text(text -> text.text("SCREEN SHAKE").size(30).color(Pal.opaqueBlack));
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
                            t.update(tip -> tip.x(slider.x() + slider.width() / UIscale * slider.value()).y(slider.y() - 10));
                        });
                    });
                }else if(menu == 4){
                    list.text(text -> text.text("SOUNDS").size(40).color(Pal.opaqueBlack));
                    list.row(10);
                    list.button(button -> {
                        button.hover(buttonHover);
                        button.press(b -> {
                            soundEffects = !soundEffects;
                            rebuild();
                        }).text(text -> text.text("SFX: " + (soundEffects ? "ON" : "OFF")).size(30).color(Pal.opaqueBlack));
                    });
                    list.row(10);
                    list.button(button -> {
                        button.hover(buttonHover);
                        button.press(b -> {
                            if(musicEnabled){
                                Sounds.stopSong();
                                musicEnabled = false;
                            }else{
                                Sounds.resumeSong();
                                musicEnabled = true;
                            }
                            rebuild();
                        }).text(text -> text.text("MUSIC: " + (musicEnabled ? "ON" : "OFF")).size(30).color(Pal.opaqueBlack));
                    });
                }
            }));
            if(menu > 0) table.button(button -> {
                button.hover(buttonHover);
                button.press(b -> {
                    menu = menu == 1 ? 0 : 1;
                    rebuild();
                }).text(text -> text.text("<").size(30).color(Pal.opaqueBlack)).width(150).y(height - 80);
            });
        }).x(20).y(10);
    }

    @Override
    public void update(){
        float wanted = menu >= 1 ? 250 : input.mouse.x < 200 ? 200 : 0;

        sidex += (wanted - sidex) / 10;
    }

    @Override
    public void draw(){
        canvas.background(220, 220, 220);

        Effects.blur.draw(sidex - 300, 0, 300, height, Pal.opaqueGray);

        side.process();
    }
}

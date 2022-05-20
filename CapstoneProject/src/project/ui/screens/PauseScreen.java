package project.ui.screens;

import processing.core.*;
import project.core.Input.*;

import static project.Vars.*;

/** Contains all the UI of a pause screen. */
public class PauseScreen extends Screen{
    @Override
    public void rebuild(){
        canvas.cursor(PConstants.ARROW);
    }

    @Override
    public void update(){
        if(input.pressed(KeyBind.upgrade)){
            input.consume(KeyBind.upgrade);
            canvas.screen(ui.gameScreen);
        }
    }

    @Override
    public void draw(){
        ui.gameScreen.draw();

        canvas.fill(100, 100, 100, 100);
        canvas.textSize(50);
        canvas.textAlign(canvas.CENTER, canvas.TOP);
        canvas.text("PAUSED", width / 2f, 50);
    }
}

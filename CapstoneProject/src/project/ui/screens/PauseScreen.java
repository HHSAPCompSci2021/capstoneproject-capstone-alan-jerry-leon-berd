package project.ui.screens;

import project.core.Input.*;

import static project.Vars.*;

/** Contains all the UI of a pause screen. */
public class PauseScreen extends Screen{
    @Override
    public void update(){
        if(input.pressed(KeyBind.pause)){
            input.consume(KeyBind.pause);
            canvas.screen(ui.gameScreen);
        }
    }

    @Override
    public void draw(){
        ui.gameScreen.draw();

        canvas.fill(100, 100, 100, 100);
        canvas.textSize(30);
        canvas.textAlign(canvas.CENTER, canvas.TOP);
        canvas.text("PAUSED", width / 2f, 20);
    }
}

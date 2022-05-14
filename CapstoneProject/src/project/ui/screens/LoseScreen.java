package project.ui.screens;

import static java.lang.Math.*;
import static project.Vars.*;

public class LoseScreen extends Screen{
    @Override
    public void update(){
        ui.gameScreen.update();
        delta = max(delta - 0.01f, -100000);
    }

    @Override
    public void draw(){
        ui.gameScreen.draw();

        canvas.fill(0, 0, 0, 100);
        canvas.rect(0, 0, width, height);
    }
}

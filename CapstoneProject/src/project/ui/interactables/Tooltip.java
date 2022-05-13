package project.ui.interactables;

import project.ui.*;

import static project.Vars.*;

/** Represents a piece of UI which is only drawn and updated when it's parent is hovered over. */
public class Tooltip extends Table{
    @Override
    public float x(){
        return x;
    }

    @Override
    public float y(){
        return y;
    }

    @Override
    public float width(){
        return 0;
    }

    @Override
    public float height(){
        return 0;
    }

    @Override
    public void process(){
        if(parent.bounds().contains(input.mouse)) super.process();
    }
}

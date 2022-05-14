package project.content;

import project.core.Content.*;
import project.world.ship.*;

public class Hulls implements ContentList{
    public static Hull standard;

    @Override
    public void load(){
        standard = new Hull("standard");
    }
}

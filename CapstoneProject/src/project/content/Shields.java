package project.content;

import project.core.Content.*;
import project.world.ship.*;

public class Shields implements ContentList{
    public static Shield standard;

    @Override
    public void load(){
        standard = new Shield("standard");
    }
}

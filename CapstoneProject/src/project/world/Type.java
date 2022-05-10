package project.world;

import project.core.Content.*;

import static project.Vars.*;

/** Represents a piece of content, and the type of instance. Contains fields and methods that may control how each instance functions. */
public class Type{
    public Type(){
        content.add(this);
    }

    public void init(){
    }

    public ContentType type(){
        return null;
    }

    public Instance create(){
        return null;
    }
}

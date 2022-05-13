package project.world;

import project.core.Content.*;

import static project.Vars.*;

/** Represents a piece of content, and the type of instance. Contains fields and methods that may control how each instance functions. */
public class Type{
    public Type(){
        content.add(this);
    }

    /** Initialize this piece of content. */
    public void init(){
    }

    /** Returns the type of this content. */
    public ContentType type(){
        return null;
    }

    /** Create an instance with this content as it's type. */
    public Instance create(){
        return null;
    }
}

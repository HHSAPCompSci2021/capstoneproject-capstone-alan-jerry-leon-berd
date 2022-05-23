package project.world;

import project.core.Content.*;

import static project.Vars.*;

/** Represents a piece of content, and the type of instance. Contains fields and methods that may control how each instance functions. */
public class Type{
    /** Whether this piece of content has been initialized or not. */
    public boolean initialized;

    /** Create a new content type. */
    public Type(){
        content.add(this);
    }

    /** Initialize this piece of content. */
    public void init(){
        initialized = true;
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

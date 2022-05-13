package project.world;

/** Represents an instance of a content type. */
public class Instance{
    /** The type of this instance. */
    public Type type;

    /** Creates an instance with the specified type. */
    public Instance(Type type){
        this.type = type;
    }

    /** Returns the type of this instance. Can be overriden with a type subclass return type in Instance's subclasses. */
    public Type type(){
        return type;
    }
}

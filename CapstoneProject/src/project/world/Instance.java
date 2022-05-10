package project.world;

/** Represents an instance of a content type. */
public class Instance{
    public Type type;

    public Instance(Type type){
        this.type = type;
    }

    public Type type(){
        return type;
    }
}

package project.core;

import gameutils.struct.*;
import project.content.*;
import project.world.*;

/** Loads, stores, and processes all content in the game. */
public class Content{
    /** Stores all content classes to be loaded. */
    public ContentList[] lists = new ContentList[]{
    new Modifiers(),
    new Hulls(),
    new Shields(),
    new Weapons(),
    new Enemies()
    };

    /** Contains all content, sorted by it's type. */
    public Seq<Type>[] map;
    /** Contains all the content. */
    public Seq<Type> all;

    /** Initialize the lists and loads the content. */
    public void init(){
        all = new Seq<>();
        map = new Seq[ContentType.all.length];
        for(int i = 0;i < map.length;i++) map[i] = new Seq<>();

        for(ContentList list : lists) list.load();

        for(Type content : all) content.init();
    }

    /** Process the content specified (Add it to the content map and list). */
    public void add(Type content){
        if(content.type() == null) return;

        all.add(content);
        map[content.type().id()].add(content);
    }

    public Seq<Type> list(ContentType type){
        return map[type.id()];
    }

    /** Represents a type of content. Used to organize the lists in Content. */
    public enum ContentType{
        hull,
        shield,
        weapon,
        modifier,
        enemy,
        bullet;

        public static ContentType[] all = values();

        public int id(){
            return ordinal();
        }
    }

    /** Represents a loadable list of content. */
    public interface ContentList{
        void load();
    }
}

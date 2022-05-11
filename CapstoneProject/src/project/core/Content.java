package project.core;

import gameutils.struct.*;
import project.content.*;
import project.graphics.*;
import project.world.*;

/** Loads, stores, and processes all content in the game. */
public class Content{
    public ContentList[] lists = new ContentList[]{
    new Sounds(),
    new Effects(),
    new Bullets(),
    new Modifiers(),
    new Gear(),
    new Enemies()
    };

    public Seq<Type>[] map;
    public Seq<Type> all;

    public void init(){
        all = new Seq<>();
        map = new Seq[ContentType.all.length];
        for(int i = 0;i < map.length;i++) map[i] = new Seq<>();

        for(ContentList list : lists) list.load();

        for(Type content : all) content.init();
    }

    public void add(Type content){
        all.add(content);
        map[content.type().id()].add(content);
    }

    /** Represents a type of content. Used to organize the lists in Content. */
    public enum ContentType{
        effect,
        body,
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

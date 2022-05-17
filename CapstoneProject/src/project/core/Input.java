package project.core;

import gameutils.math.*;
import gameutils.struct.prim.*;

import java.awt.event.*;

import static project.Vars.*;

/** Handles all inputs by the user. */
public class Input{
    public int left = KeyEvent.BUTTON1_DOWN_MASK;
    public int middle = KeyEvent.BUTTON2_DOWN_MASK;
    public int right = KeyEvent.BUTTON3_DOWN_MASK;

    public Vec2 mouse;
    public IntSet inputs;

    public void init(){
        mouse = new Vec2();
        inputs = new IntSet();
    }

    /** Process an input. */
    public void register(int input){
        inputs.add(input);
    }

    /** Return whether the current KeyBind is pressed. */
    public boolean pressed(KeyBind keyBind){
        return inputs.contains(keyBind.value);
    }

    /** Removes the specified from the storage list. */
    public void consume(KeyBind keyBind){
        inputs.remove(keyBind.value);
    }

    public void remove(int input){
        inputs.remove(input);
    }

    /** Returns whether the left mouse if pressed. */
    public boolean mouseLeft(){
        return inputs.contains(left);
    }

    /** Returns whether the scroll wheel if pressed. */
    public boolean mouseMiddle(){
        return inputs.contains(middle);
    }

    /** Returns whether the right mouse if pressed. */
    public boolean mouseRight(){
        return inputs.contains(right);
    }

    //TODO: Create KeyCode class, that processes and stores key values for keybinds (Mainly just for the name)
    /** Represents a keybind, storing both the value, and name. */
    public enum KeyBind{
        thrust("THRUST", input.right),
        shoot("SHOOT", input.left),

        upgrade("UPGRADE", KeyEvent.VK_SPACE);

        public static KeyBind[] all = values();

        public final String name;
        public final int value;

        KeyBind(String name, int value){
            this.name = name;
            this.value = value;
        }
    }
}

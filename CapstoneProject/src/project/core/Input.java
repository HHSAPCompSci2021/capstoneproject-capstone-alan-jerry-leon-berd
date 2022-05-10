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

    public void register(int input){
        inputs.add(input);
    }

    public boolean pressed(KeyBind keyBind){
        return inputs.contains(keyBind.value);
    }

    public void consume(KeyBind keyBind){
        inputs.remove(keyBind.value);
    }

    public void remove(int input){
        inputs.remove(input);
    }

    public boolean mouseLeft(){
        return inputs.contains(left);
    }

    public boolean mouseMiddle(){
        return inputs.contains(middle);
    }

    public boolean mouseRight(){
        return inputs.contains(right);
    }

    //TODO: Create KeyCode class, that processes and stores key values for keybinds (Mainly just for the name)

    /** Represents a keybind, storing both the value, and name. */
    public enum KeyBind{
        thrust("THRUST", input.right),
        shoot("SHOOT", input.left),

        pause("PAUSE", KeyEvent.VK_SPACE);

        public static KeyBind[] all = values();

        public String name;
        public int value;

        KeyBind(String name, int value){
            this.name = name;
            this.value = value;
        }
    }
}

package project.core;

import gameutils.math.*;
import gameutils.struct.prim.*;

import java.awt.event.*;

import static project.Vars.*;

/** Handles all inputs by the user. */
public class Input{
    /** Stores the value of a left click. */
    public final int left = KeyEvent.BUTTON1_DOWN_MASK;
    /** Stores the value of a middle click. */
    public final int middle = KeyEvent.BUTTON2_DOWN_MASK;
    /** Stores the value of a rightz click. */
    public final int right = KeyEvent.BUTTON3_DOWN_MASK;

    /** Stores the position of the mouse. */
    public Vec2 mouse;
    /** Stores all inputs. */
    public IntSet inputs;

    /** Initializes the inputs. */
    public void init(){
        mouse = new Vec2();
        inputs = new IntSet();
    }

    /**
     * Process an input.
     * @param input the input to process
     */
    public void register(int input){
        inputs.add(input);
    }

    /**
     * Return whether the current KeyBind is pressed.
     * @param keyBind the specified keybind
     * @return whether it is pressed
     */
    public boolean pressed(KeyBind keyBind){
        return inputs.contains(keyBind.value);
    }

    /**
     * Removes the specified keybind from the storage list.
     * @param keyBind the specified keybind
     */
    public void consume(KeyBind keyBind){
        inputs.remove(keyBind.value);
    }

    /**
     * Removes the specified input from the storage list.
     * @param input the specified input
     */
    public void remove(int input){
        inputs.remove(input);
    }

    /**
     * Returns whether the left mouse is pressed.
     * @return whether it is pressed
     */
    public boolean mouseLeft(){
        return inputs.contains(left);
    }

    /**
     * Returns whether the scroll wheel is pressed.
     * @return whether it is pressed
     */
    public boolean mouseMiddle(){
        return inputs.contains(middle);
    }

    /**
     * Returns whether the right mouse is pressed.
     * @return whether it is pressed
     */
    public boolean mouseRight(){
        return inputs.contains(right);
    }

    /** Represents a keybind, storing both the value, and name. */
    public enum KeyBind{
        thrust("THRUST", input.right),
        shoot("SHOOT", input.left),

        upgrade("UPGRADE", KeyEvent.VK_SPACE);

        public static KeyBind[] all = values();

        public final String name;
        public final int value;

        /**
         * Create a keybind with the specified name and value
         * @param name the name of this keybind
         * @param value the value of this keybind
         */
        KeyBind(String name, int value){
            this.name = name;
            this.value = value;
        }
    }
}

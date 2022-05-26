package project;

import processing.awt.PSurfaceAWT.*;
import processing.core.*;

import javax.swing.*;
import java.awt.*;

import static project.Vars.*;

/** The main method. Sets up and runs everything. */
public class Main{
    public static void main(String[] args){
        Vars.init();

        PApplet.runSketch(new String[]{""}, canvas);
        JFrame window = (JFrame)((SmoothCanvas)canvas.getSurface().getNative()).getFrame();
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        window.setBounds(center.x - width / 2, center.y - height / 2, width, height);
        window.setMinimumSize(new Dimension(width, height));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setVisible(true);
    }
}

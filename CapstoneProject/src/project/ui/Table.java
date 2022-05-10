package project.ui;

import gameutils.struct.*;

import java.awt.*;

/** Represents a basic table, which will be the superclass of all UI. A table should contain a x, y, width, height, color, and a list of contents, which are other tables drawn on top of it. */
public class Table{
    public float x, y, width, height;
    public Color color;
    public Seq<Table> contents;
}

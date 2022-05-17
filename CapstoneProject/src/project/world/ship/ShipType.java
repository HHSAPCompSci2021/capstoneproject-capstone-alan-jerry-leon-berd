package project.world.ship;

import gameutils.math.*;
import gameutils.struct.*;
import project.*;
import project.graphics.*;
import project.world.*;

import java.awt.*;

import static gameutils.util.Mathf.*;
import static project.Vars.*;
import static project.core.Rules.Rule.*;

public interface ShipType{
    public float accel();
    public float rotate();
    public float mass();
    public float size();
    public float health();
    public float ram();
}

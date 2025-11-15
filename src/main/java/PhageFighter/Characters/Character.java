package PhageFighter.Characters;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public abstract class Character {

    // members
    protected PVector pos;
    protected PVector vel;
    protected PVector weaponAngle; // unit vector

    protected PApplet global;

    protected PImage skin;

    Character(PApplet global) {
        this.global = global;

        this.pos = new PVector(global.width / 2.0f, global.height / 2.0f);
        this.vel = new PVector(0, 0);
        this.weaponAngle = new PVector(0, 0);
    }

    public void display() {
        global.image(skin, pos.x, pos.y);
    }

    public void step() {
        pos.add(vel);
    }
}

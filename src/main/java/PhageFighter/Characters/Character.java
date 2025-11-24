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
    protected PImage skin_display;

    protected final int DISPLAY_SIZE = 240;

    protected float width;
    protected float height;

    protected String name;

    protected final float healthMax;
    protected final float damage;
    protected final String abilityDescription;
    protected final String description;

    Character(PApplet global, float healthMax, float damage, String abilityDescription, String description) {
        this.global = global;

        this.pos = new PVector(global.width / 2.0f, global.height / 2.0f);
        this.vel = new PVector(0, 0);
        this.weaponAngle = new PVector(0, 0);

        this.healthMax = healthMax;
        this.damage = damage;
        this.abilityDescription = abilityDescription;
        this.description = description;
    }

    public void displayCharacter() {
        this.pos.set(230 - (float) this.DISPLAY_SIZE /2, 320 - (float) this.DISPLAY_SIZE /2);
        global.image(this.skin_display, pos.x, pos.y);
    }

    public void display() {
        global.image(skin, pos.x, pos.y);
    }

    public void step() {
        pos.add(vel);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getHealthMax() {
        return healthMax;
    }

    public float getDamage() {
        return damage;
    }

    public String getAbility() {
        return abilityDescription;
    }
}

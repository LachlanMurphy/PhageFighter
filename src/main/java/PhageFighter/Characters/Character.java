package PhageFighter.Characters;

import PhageFighter.PhageFighter;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.List;

public abstract class Character implements Player {

    // members
    protected int speed = 10;
    protected PVector pos;
    protected PVector vel;
    protected PVector weaponAngle; // unit vector

    protected PhageFighter global;

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

    Character(PhageFighter global, float healthMax, float damage, String abilityDescription, String description) {
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

    public void step(List<Integer> keys) {
        // controls
        this.vel.mult(0);
        if (keys.contains(Keys.a))
            this.vel.x = -1;
        if (keys.contains(Keys.d))
            this.vel.x = 1;
        if (keys.contains(Keys.w))
            this.vel.y = -1;
        if (keys.contains(Keys.s))
            this.vel.y = 1;

        this.vel.normalize().mult(this.speed);

        this.pos.add(this.vel);
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

    public void shoot(int mx, int my) {
        // implemented by sub-class
        throw new RuntimeException("Character should not shoot");
    }
}

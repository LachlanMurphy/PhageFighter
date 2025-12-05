package PhageFighter.Characters;

import processing.core.PApplet;
import processing.core.PVector;

public class Bullet {
    private PVector pos;
    private PVector vel;
    private PApplet global;

    private final int RADIUS = 5;
    private final int SPEED = 5;
    private float damage = 5.0f;

    public Bullet(PApplet global, PVector dir, PVector pos, float bulleDamage) {
        this.vel = dir.copy().mult(SPEED);
        this.global = global;
        this.pos = pos;
        this.damage = bulleDamage;
    }

    public void display() {
        global.fill(0,255,0);
        global.noStroke();
        global.circle(pos.x, pos.y, RADIUS);
    }

    public void step() {
        this.pos.add(this.vel);
    }

    public boolean inBounds() {
        if (this.pos.x < 0) return false;
        if (this.pos.x > global.width) return false;
        if (this.pos.y < 0) return false;
        return !(this.pos.y > global.height);
    }

    public PVector getPos() {
        return this.pos.copy();
    }

    public float getRadius() {
        return this.RADIUS;
    }

    public float getDamage() {
        return this.damage;
    }
}

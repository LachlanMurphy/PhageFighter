package PhageFighter.Characters;

import processing.core.PApplet;
import processing.core.PVector;

public class Bullet {
    private PVector pos;
    private PVector vel;
    private PApplet global;

    private final int SIZE = 10;

    public Bullet(PApplet global, PVector dir, PVector pos) {
        this.vel = dir.copy().mult(2.5f);
        this.global = global;
        this.pos = pos;
    }

    public void display() {
        global.fill(0,255,0);
        global.rect(pos.x, pos.y, SIZE, SIZE);
    }

    public void step() {
        this.pos.add(this.vel);
    }
}

package PhageFighter.Characters;

import PhageFighter.PhageFighter;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Virus extends Character {
    private final String SKIN_DIR = "images/enemy.jpg";
    private static final float HEALTH_MAX = 10.0f;
    private static final int SIZE = 20;
    private static final float DAMAGE = 5.0f;
    private final float SPEED = 1.0f;
    private final float EXPERIENCE = 10.0f;

    Virus(PhageFighter global) {
        super(global, HEALTH_MAX, DAMAGE, "", "");
    }

    @Override
    protected void initCharacter() {
        this.skin = global.loadImage(SKIN_DIR);
        this.skin.resize(SIZE, SIZE);
        this.width = SIZE;
        this.height = SIZE;
        this.health = healthMax;
        this.name = "Virus";

        Random rand = new Random();
        PVector spawnPoint = switch (rand.nextInt(4)) {
            case 0 -> new PVector(global.random(0, global.width), -50);
            case 1 -> new PVector(global.random(0, global.width), global.height + 50);
            case 2 -> new PVector(-50, global.random(0, global.height));
            default -> new PVector(global.width + 50, global.random(0, global.height));
        };

        this.pos.set(spawnPoint);
        this.speed = SPEED;
        this.experience = EXPERIENCE;
    }

    @Override
    protected void initAbility() {
        // pass, no ability
    }

    @Override
    public void step(List<Character> enemies) {
        followCharacter(enemies);
        super.step(enemies);
    }

    @Override
    public void display() {
        if (this.health < this.healthMax) {
            // display health bar
            drawHealthBar(this.pos, this.width, this.height, this.health, this.healthMax);
        }

        super.display();
    }

    @Override
    public boolean isPlayer() {
        return false;
    }
}

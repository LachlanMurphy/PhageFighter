package PhageFighter.Characters;

import PhageFighter.PhageFighter;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.List;
import java.util.Random;

public class MutantPhage extends Character {
    private final String SKIN_DIR = "images/enemy.jpg";
    private static final float HEALTH_MAX = 40.0f;
    private static final int SIZE = 40;
    private static final float DAMAGE = 5.0f;

    private final float SPEED = 0.5f;
    private final float EXPERIENCE = 20.0f;

    MutantPhage(PhageFighter global) {
        super(global, HEALTH_MAX, DAMAGE, "", "");
        this.skin = global.loadImage(SKIN_DIR);
        this.skin.resize(SIZE, SIZE);
        this.width = SIZE;
        this.height = SIZE;
        this.health = healthMax;

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
    public void step(List<Character> enemies) {
        this.vel.add(global.getPlayerPos().sub(this.pos)).normalize();

        // check if enemy is touching character
        for (Character enemy : enemies) {
            if (characterCollide(this.pos, this.width, this.height,
                    enemy.getPos(), enemy.getWidth(), enemy.getHeight())) {
                enemy.damage(this.getDamage());
                this.acc = this.vel.copy().normalize().mult(-6);
            }
        }

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

    private void drawHealthBar(PVector characterPos, float characterWidth, float characterHeight,
                               float health, float maxHealth) {
        float barWidth = characterWidth;       // same width as the square
        float barHeight = 8;      // thickness of the bar

        // Position the bar just under the square
        float x = characterPos.x - barWidth / 2;
        float y = characterPos.y + characterHeight / 2 + 6;   // 6px gap under the square

        // Background
        global.fill(50);
        global.rect(x, y, barWidth, barHeight);

        // Health % and green bar
        float pct = PApplet.constrain(health / maxHealth, 0, 1);
        global.fill(global.lerpColor(global.color(255,0,0), global.color(0,255,0), pct));
        global.rect(x, y, barWidth * pct, barHeight);
    }
}

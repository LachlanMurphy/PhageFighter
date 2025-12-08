package PhageFighter.Characters;

import PhageFighter.Ability.Ability;
import PhageFighter.Events.EventBus;
import PhageFighter.Events.EventType;
import PhageFighter.PhageFighter;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static processing.core.PApplet.abs;
import static processing.core.PApplet.constrain;

public abstract class Character {

    // members
    protected float speed;
    protected PVector pos;
    protected PVector vel;
    protected PVector acc;

    protected PhageFighter global;

    protected PImage skin;
    protected PImage skin_display;

    protected final int DISPLAY_SIZE = 240;

    protected float width;
    protected float height;
    protected float health;
    protected float experience;
    protected float experienceMax;
    private final int INITIAL_MAX_EXPERIENCE = 50;
    protected Ability ability;
    protected long cooldown;
    protected long cooldownElapsed;

    protected String name;

    protected float healthMax;
    protected final float damage;
    protected final String abilityDescription;
    protected final String description;

    protected List<Bullet> bullets;

    protected boolean direction; // true = right, false = left
    private int level;
    protected float bulletDamage;
    private final float DEFAULT_BULLET_DAMAGE = 5.0f;
    protected int numberOfBullets;
    protected boolean turret;
    protected PVector turretPos;
    protected PVector turretDir;

    Character(PhageFighter global, float healthMax, float damage, String abilityDescription, String description) {
        this.global = global;

        this.pos = new PVector(global.width / 2.0f, global.height / 2.0f);
        this.vel = new PVector(0, 0);
        this.acc = new PVector(0, 0);

        this.healthMax = healthMax;
        this.damage = damage;
        this.abilityDescription = abilityDescription;
        this.description = description;

        this.bullets = new ArrayList<Bullet>();

        this.direction = true;
        this.health = healthMax;
        this.level = 1;
        this.bulletDamage = DEFAULT_BULLET_DAMAGE;
        this.numberOfBullets = 1;

        this.experienceMax = INITIAL_MAX_EXPERIENCE;
        this.experience = 0;
        this.turret = false;
        this.turretPos = new PVector();

        // character dependent methods
        initCharacter();
        initAbility();
    }

    protected abstract void initCharacter();
    protected abstract void initAbility();
    protected abstract boolean isPlayer();

    public void displayCharacter() {
        this.pos.set(230 - (float) this.DISPLAY_SIZE /2, 320 - (float) this.DISPLAY_SIZE /2);
        global.image(this.skin_display, pos.x, pos.y);
    }

    public void display() {
        for (Bullet bullet : bullets) bullet.display();
        if (!direction) {
            global.pushMatrix();
            global.scale(-1,1);
            global.image(this.skin, -1*(pos.x+this.width/2), pos.y-this.height/2);
            global.popMatrix();
        } else {
            global.image(skin, pos.x-this.width/2, pos.y-this.height/2);
        }
    }

    public void step(List<Character> enemies) {
        acc.mult(0.95f);
        vel.add(acc);
        if (this.acc.mag() < 1)
            vel.limit(speed);
        pos.add(vel);
        vel.mult(0.6f);

        int bulletSize = bullets.size();
        for (int i = bulletSize - 1; i >= 0; i--) {
            if (!bullets.get(i).inBounds()) {
                bullets.remove(i);
                continue;
            } else {
                bullets.get(i).step();
            }

            // check if bullets hit an enemy.
            // enemy could be player or enemies depending on context of character
            int enemySize = enemies.size();
            for (int enemy = enemySize - 1; enemy >= 0; enemy--) {
                if (bulletCollide(bullets.get(i).getPos(), bullets.get(i).getRadius(),
                        enemies.get(enemy).getPos(), enemies.get(enemy).getWidth(),
                        enemies.get(enemy).getHeight())) {
                    enemies.get(enemy).damage(bullets.get(i).getDamage());
                    bullets.remove(i);
                    break;
                }
            }
        }
    }

    protected void damage(float damage) {
        this.health -= damage;
        if (isPlayer()) {
            EventBus.getInstance().postMessage(EventType.HealthLoss);
        }
    }

    private boolean bulletCollide(PVector bulletPos, float bulletRadius,
                                  PVector characterPos, float squareWidth, float squareHeight) {
        float halfW = squareWidth / 2;
        float halfH = squareHeight / 2;

        float minX = characterPos.x - halfW;
        float maxX = characterPos.x + halfW;

        float minY = characterPos.y - halfH;
        float maxY = characterPos.y + halfH;

        float closestX = constrain(bulletPos.x, minX, maxX);
        float closestY = constrain(bulletPos.y, minY, maxY);

        float dx = bulletPos.x - closestX;
        float dy = bulletPos.y - closestY;

        return dx*dx + dy*dy <= bulletRadius*bulletRadius;
    }

    protected boolean characterCollide(PVector aPosition, float aWidth, float aHeight,
                              PVector bPosition, float bWidth, float bHeight) {
        float aHalfW = aWidth / 2.0f;
        float aHalfH = aHeight / 2.0f;

        float bHalfW = bWidth / 2.0f;
        float bHalfH = bHeight / 2.0f;

        boolean overlapX = abs(aPosition.x - bPosition.x) <= (aHalfW + bHalfW);
        boolean overlapY = abs(aPosition.y - bPosition.y) <= (aHalfH + bHalfH);

        return overlapX && overlapY;
    }

    public void step(List<Integer> keys, List<Character> enemies) {
        // controls
        PVector input =  new PVector(0,0);
        if (keys.contains(Keys.a)) {
            input.x = -1;
            this.direction = false;
        }
        if (keys.contains(Keys.d)) {
            input.x = 1;
            this.direction = true;
        }
        if (keys.contains(Keys.w))
            input.y = -1;
        if (keys.contains(Keys.s))
            input.y = 1;

        if (keys.contains(Keys.space)) this.useAbility();

        input.normalize();
        vel.add(input);
        this.step(enemies);
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

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public float getExp() {
        return this.experience;
    }

    public float getExpMax() {
        return this.experienceMax;
    }

    public PVector getPos() {return this.pos.copy(); }

    public void shoot(int mx, int my) {
        // standard bullet implementation
        PVector dir = new PVector(mx-this.pos.x, my-this.pos.y);
        dir.normalize();
        List<PVector> positions = lineBulletPositions(this.pos, dir, this.numberOfBullets);
        for (int i = 0; i < numberOfBullets; i++) {
            bullets.add(new Bullet(global, dir, positions.get(i), this.bulletDamage));
        }
    }

    ArrayList<PVector> lineBulletPositions(PVector position, PVector dir, int count) {
        ArrayList<PVector> positions = new ArrayList<PVector>();

        if (count <= 0) return positions;
        PVector d = dir.copy().normalize();
        PVector perp = new PVector(-d.y, d.x);
        float start = -(float) 10 * (count - 1) / 2.0f;

        for (int i = 0; i < count; i++) {
            float offset = start + (float) 10 * i;
            PVector p = PVector.add(position, PVector.mult(perp, offset));
            positions.add(p);
        }

        return positions;
    }


    public float getHealth() {
        return health;
    }

    // return true if level up occurred
    public boolean gainExp(float exp) {
        boolean ret  = false;
        this.experience += exp;
        if (this.experience >= this.experienceMax) {
            ret = true;
            this.experience -= this.experienceMax;
            this.level++;
            this.experienceMax *= 1.5f;
            EventBus.getInstance().postMessage(EventType.LevelUp);
        }
        return ret;
    }

    public int getLevel() {
        return this.level;
    }

    public void healthIncrease() {
        this.healthMax *= 1.2f;
        this.health *= 1.2f;
    }

    public void heal() {
        this.health = this.healthMax;
    }

    public void bulletIncrease() {
        this.bulletDamage *= 1.2f;
    }

    public void setAcc(PVector acc) {
        this.acc.set(acc.copy());
    }

    public PVector getVel() {
        return this.vel.copy();
    }

    public void useAbility() {
        long time = System.currentTimeMillis();
        if (time > this.cooldownElapsed) {
            ability.useAbility(this);
            this.cooldownElapsed = time + this.cooldown;
        }
    }

    public long getCooldownElapsed() {
        return this.cooldownElapsed;
    }

    public long getCooldown() {
        return this.cooldown;
    }

    public void addMultiShot() {
        this.numberOfBullets++;
    }

    public void addTurret() {
        this.turret = true;
        this.turretPos = this.pos.copy();
        this.turretDir = new PVector(0, 1);

        // set timeout to disable
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                turret = false;
            }
        }, 10000); // 10 second cool down
    }

    protected void drawHealthBar(PVector characterPos, float barWidth, float characterHeight,
                               float health, float maxHealth) {
        float barHeight = 8;      // thickness of the ba

        float x = characterPos.x - barWidth / 2;
        float y = characterPos.y + characterHeight / 2 + 6;

        global.fill(50);
        global.rect(x, y, barWidth, barHeight);

        float pct = PApplet.constrain(health / maxHealth, 0, 1);
        global.fill(global.lerpColor(global.color(255,0,0), global.color(0,255,0), pct));
        global.rect(x, y, barWidth * pct, barHeight);
    }

    protected void followCharacter(List<Character> enemies) {
        this.vel.add(global.getPlayerPos().sub(this.pos)).normalize();

        // check if enemy is touching character
        for (Character enemy : enemies) {
            if (characterCollide(this.pos, this.width, this.height,
                    enemy.getPos(), enemy.getWidth(), enemy.getHeight())) {
                enemy.damage(this.getDamage());
                this.acc = this.vel.copy().normalize().mult(-6);
            }
        }
    }
}

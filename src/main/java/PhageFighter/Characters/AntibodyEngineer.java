package PhageFighter.Characters;

import PhageFighter.Ability.Ability;
import PhageFighter.Ability.AbilityFactory;
import PhageFighter.Ability.DeployTurret;
import PhageFighter.Ability.TCellDash;
import PhageFighter.PhageFighter;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class AntibodyEngineer extends Character {
    private final String SKIN_DIR = "images/AntibodyEngineer.png";
    private final int SIZE = 40;
    private final float TURRET_LENGTH = 15.0f;
    private static final float HEALTH_MAX = 150.0f;
    private static final float DAMAGE = 0.0f;
    private static final String ABILITY_DESCRIPTION = "(Active) Deploy a turret to\nhelp you fight enemies!";
    private static final String DESCRIPTION = "The Antibody Engineer knows\nhis way around machines.";

    public AntibodyEngineer(PhageFighter global) {
        super(global, HEALTH_MAX, DAMAGE, ABILITY_DESCRIPTION, DESCRIPTION);
    }

    @Override
    protected void initCharacter() {
        this.skin = global.loadImage(SKIN_DIR);
        this.skin.resize(SIZE, SIZE);

        this.skin_display = global.loadImage(SKIN_DIR);
        this.skin_display.resize(DISPLAY_SIZE, DISPLAY_SIZE);

        this.width = SIZE;
        this.height = SIZE;

        this.name = "Antibody Engineer";
        this.speed = 3.0f;
    }

    @Override
    protected void initAbility() {
        this.ability = AbilityFactory.createDeployTurretAbility();
        this.cooldown = 60000;
        this.cooldownElapsed = System.currentTimeMillis();
    }

    @Override
    public void step(List<Character> enemies) {
        if (this.turret) {
            Character target = getClosestCharacter(enemies, this.turretPos);
            if (target != null) {
                this.turretDir.set(target.getPos().sub(this.turretPos)).normalize();

                if (global.frameCount % 60 == 0) {
                    this.bullets.add(new Bullet(global, turretDir.copy(), turretPos.copy(), this.bulletDamage));
                }
            }
        }

        super.step(enemies);
    }

    private Character getClosestCharacter(List<Character> characters, PVector pos) {
        if (characters.isEmpty()) return null;

        Character closest = characters.getFirst();
        float minDist = PVector.dist(pos, closest.getPos());

        for (int i = 1; i < characters.size(); i++) {
            float d = PVector.dist(pos, characters.get(i).getPos());
            if (d < minDist) {
                minDist = d;
                closest = characters.get(i);
            }
        }

        return closest;
    }

    @Override
    public void display() {
        if (this.turret) {
            global.pushMatrix();
            global.translate(this.turretPos.x, this.turretPos.y);

            global.stroke(0);
            global.strokeWeight(8);
            global.line(0, 0, 10*PApplet.sqrt(3), 10);
            global.line(0, 0, -10*PApplet.sqrt(3), 10);
            global.line(0, 0, 0, -20);

            global.fill(120);
            global.noStroke();
            global.circle(0, 0, 15);

            global.stroke(0,0,125);
            turretDir.setMag(TURRET_LENGTH);
            global.line(0,0,turretDir.x, turretDir.y);
            global.noStroke();
            global.popMatrix();
        }

        super.display();
    }
}

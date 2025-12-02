package PhageFighter.Characters;

import PhageFighter.PhageFighter;
import processing.core.PApplet;

public class AntibodyEngineer extends Character {
    private final String SKIN_DIR = "images/AntibodyEngineer.png";
    private final int SIZE = 40;
    private static final float HEALTH_MAX = 150.0f;
    private static final float DAMAGE = 10.0f;
    private static final String ABILITY_DESCRIPTION = "(Active) Deploy a turret to\nhelp you fight enemies!";
    private static final String DESCRIPTION = "The Antibody Engineer knows\nhis way around machines.";

    public AntibodyEngineer(PhageFighter global) {
        super(global, HEALTH_MAX, DAMAGE, ABILITY_DESCRIPTION, DESCRIPTION);
        this.skin = global.loadImage(SKIN_DIR);
        this.skin.resize(SIZE, SIZE);

        this.skin_display = global.loadImage(SKIN_DIR);
        this.skin_display.resize(DISPLAY_SIZE, DISPLAY_SIZE);

        this.width = SIZE;
        this.height = SIZE;

        this.name = "Antibody Engineer";
    }

}

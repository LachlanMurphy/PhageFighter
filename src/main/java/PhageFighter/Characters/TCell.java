package PhageFighter.Characters;

import processing.core.PApplet;

import java.util.Objects;

public class TCell extends Character {
    private final String SKIN_DIR = "images/TCell.png";
    private final int SIZE = 40;
    private static final float HEALTH_MAX = 100.0f;
    private static final float DAMAGE = 100.0f;
    private static final String ABILITY_DESCRIPTION = "(Active) Dashes forward\ndealing damage to enemies!";
    private static final String DESCRIPTION = "The T-Cell is a basic phage killer\nwith high damage\nand a low fire rate.";

    public TCell(PApplet global) {
        super(global, HEALTH_MAX, DAMAGE, ABILITY_DESCRIPTION, DESCRIPTION);
        this.skin = global.loadImage(SKIN_DIR);
        this.skin.resize(SIZE, SIZE);

        this.skin_display = global.loadImage(SKIN_DIR);
        this.skin_display.resize(DISPLAY_SIZE, DISPLAY_SIZE);

        this.width = SIZE;
        this.height = SIZE;

        this.name = "T-Cell";
    }
}

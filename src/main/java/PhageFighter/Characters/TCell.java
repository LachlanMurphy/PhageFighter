package PhageFighter.Characters;

import PhageFighter.Ability.AbilityFactory;
import PhageFighter.Ability.TCellDash;
import PhageFighter.PhageFighter;

public class TCell extends Character {
    private final String SKIN_DIR = "images/TCell.png";
    private final int SIZE = 40;
    private static final float HEALTH_MAX = 100.0f;
    private static final float DAMAGE = 0.0f;
    private static final String ABILITY_DESCRIPTION = "(Active) Dashes forward\nfor extra speed!";
    private static final String DESCRIPTION = "The T-Cell is a basic phage killer\nwith high damage\nand a high fire rate.";

    public TCell(PhageFighter global) {
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

        this.name = "T-Cell";
        this.speed = 3.0f;
    }

    protected void initAbility() {
        this.ability = AbilityFactory.createTCellDashAbility();
        this.cooldown = 3000;
        this.cooldownElapsed = System.currentTimeMillis();
    }
}

package PhageFighter.Characters;

import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Character {
    private final String SKIN_DIR = "../../../../resources/player.jpg";

    Player(PApplet global) {
        super(global);
        this.skin = global.loadImage(SKIN_DIR);
    }
}

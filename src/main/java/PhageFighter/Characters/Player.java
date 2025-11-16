package PhageFighter.Characters;

import processing.core.PApplet;

import java.util.Objects;

public class Player extends Character {
    private final String SKIN_DIR = "images/player.jpg";

    Player(PApplet global) {
        super(global);
        this.skin = global.loadImage(SKIN_DIR);
    }
}

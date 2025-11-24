package PhageFighter.Characters;

import processing.core.PApplet;

public class Virus extends Character {
    private final String SKIN_DIR = "images/enemy.jpg";

    Virus(PApplet global) {
        super(global, 0.0f, 0.0f, "", "");
        this.skin = global.loadImage(SKIN_DIR);
    }
}

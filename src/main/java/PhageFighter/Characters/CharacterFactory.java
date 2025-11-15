package PhageFighter.Characters;

import processing.core.PApplet;

public class CharacterFactory {

    public static Character createPlayer(PApplet global) {
        return new Player(global);
    }
}

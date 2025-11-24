package PhageFighter.Characters;

import processing.core.PApplet;

import java.util.Arrays;
import java.util.List;

public class CharacterFactory {

    public static Character createTCell(PApplet global) {
        return new TCell(global);
    }

    public static Character createAntibodyEngineer(PApplet global) {
        return new AntibodyEngineer(global);
    }

    public static List<Character> createCharacters(PApplet global) {
        return Arrays.asList(createTCell(global), createAntibodyEngineer(global));
    }
}

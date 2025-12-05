package PhageFighter.Characters;

import PhageFighter.PhageFighter;
import processing.core.PApplet;

import java.util.Arrays;
import java.util.List;

public class CharacterFactory {

    public static Character createTCell(PhageFighter global) {
        return new TCell(global);
    }

    public static Character createAntibodyEngineer(PhageFighter global) {
        return new AntibodyEngineer(global);
    }

    public static Character createVirus(PhageFighter global) { return new Virus(global); }
    public static Character createMutantPhage(PhageFighter global) { return new MutantPhage(global); }

    public static List<Character> createCharacters(PhageFighter global) {
        return Arrays.asList(createTCell(global), createAntibodyEngineer(global));
    }
}

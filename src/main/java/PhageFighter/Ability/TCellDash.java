package PhageFighter.Ability;

import PhageFighter.Characters.Character;
import processing.core.PApplet;

public class TCellDash implements Ability {
    @Override
    public void useAbility(Character player) {
        player.setAcc(player.getVel().setMag(3));
    }
}

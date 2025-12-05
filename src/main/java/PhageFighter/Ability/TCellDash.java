package PhageFighter.Ability;

import PhageFighter.Characters.Character;

public class TCellDash implements Ability {
    @Override
    public void useAbility(Character player) {
        player.setAcc(player.getVel().setMag(3));
    }
}

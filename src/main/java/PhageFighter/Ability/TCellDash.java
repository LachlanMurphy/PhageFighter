package PhageFighter.Ability;

import PhageFighter.Characters.Character;

public class TCellDash implements Ability {
    private final int DASH_STRENGTH = 3;
    @Override
    public void useAbility(Character player) {
        player.setAcc(player.getVel().setMag(DASH_STRENGTH));
    }
}

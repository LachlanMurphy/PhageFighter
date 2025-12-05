package PhageFighter.Ability;

import PhageFighter.Characters.Character;

public class DeployTurret implements Ability {
    @Override
    public void useAbility(Character player) {
        player.addTurret();
    }
}

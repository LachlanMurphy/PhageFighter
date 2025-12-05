package PhageFighter.Button;

import PhageFighter.PhageFighter;

public class HealEvent implements ButtonEvent {
    @Override
    public void onClick(PhageFighter global) {
        global.playerHeal();
    }
}

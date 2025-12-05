package PhageFighter.Button;

import PhageFighter.PhageFighter;

public class BulletDamageIncreaseEvent implements ButtonEvent {
    @Override
    public void onClick(PhageFighter global) {
        global.playerBulletIncrease();
    }
}

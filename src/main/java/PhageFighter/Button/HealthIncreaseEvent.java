package PhageFighter.Button;

import PhageFighter.PhageFighter;

public class HealthIncreaseEvent implements ButtonEvent {

    @Override
    public void onClick(PhageFighter global) {
        global.playerHealthIncrease();
    }
}

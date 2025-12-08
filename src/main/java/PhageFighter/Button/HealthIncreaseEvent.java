package PhageFighter.Button;

import PhageFighter.Events.EventBus;
import PhageFighter.Events.EventType;
import PhageFighter.PhageFighter;

public class HealthIncreaseEvent implements ButtonEvent {

    @Override
    public void onClick(PhageFighter global) {
        global.playerHealthIncrease();
        EventBus.getInstance().postMessage(EventType.MaxHealthPlus);
    }
}

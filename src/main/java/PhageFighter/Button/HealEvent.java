package PhageFighter.Button;

import PhageFighter.Events.EventBus;
import PhageFighter.Events.EventType;
import PhageFighter.PhageFighter;

public class HealEvent implements ButtonEvent {
    @Override
    public void onClick(PhageFighter global) {
        global.playerHeal();
        EventBus.getInstance().postMessage(EventType.HealthPlus);
    }
}

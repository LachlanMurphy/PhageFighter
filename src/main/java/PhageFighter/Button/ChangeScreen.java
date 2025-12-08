package PhageFighter.Button;

import PhageFighter.Events.EventBus;
import PhageFighter.Events.EventType;
import PhageFighter.PhageFighter;

public class ChangeScreen implements ButtonEvent {
    private final PhageFighter.Screen screen;

    public ChangeScreen(PhageFighter.Screen screen) {
        this.screen = screen;
    }

    @Override
    public void onClick(PhageFighter global) {
        global.setScreen(this.screen);
        if (screen == PhageFighter.Screen.Game) {
            EventBus.getInstance().postMessage(EventType.GameStart);
        }
    }
}

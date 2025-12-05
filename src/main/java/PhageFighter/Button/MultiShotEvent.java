package PhageFighter.Button;

import PhageFighter.PhageFighter;

public class MultiShotEvent implements ButtonEvent {
    @Override
    public void onClick(PhageFighter global) {
        global.playerMultiShotPlus();
    }
}

package PhageFighter.Button;

import PhageFighter.PhageFighter;

public class ChangeCharacter implements ButtonEvent {
    private boolean direction;

    public ChangeCharacter(boolean direction) {
        this.direction = direction;
    }

    public void onClick(PhageFighter global) {
        global.changeCharacter(direction ? 1 : -1);
    }
}

package PhageFighter.Button;

import PhageFighter.PhageFighter;

import static java.lang.System.exit;

public class ExitEvent implements ButtonEvent {

    @Override
    public void onClick(PhageFighter global) {
        // maybe save game here
        exit(0);
    }
}

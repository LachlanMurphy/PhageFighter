package PhageFighter.Button;

import PhageFighter.PhageFighter;
import com.jogamp.newt.Screen;
import processing.core.PApplet;

public class ChangeScreen implements ButtonEvent {
    private final PhageFighter.Screen screen;

    public ChangeScreen(PhageFighter.Screen screen) {
        this.screen = screen;
    }

    @Override
    public void onClick(PhageFighter global) {
        global.setScreen(this.screen);
    }
}

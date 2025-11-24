package PhageFighter.Button;

import PhageFighter.PhageFighter;
import processing.core.*;

import java.awt.*;

public class Button implements ButtonEvent {
    protected final PVector pos = new PVector(0,0);
    protected final String text;
    protected final float width;
    protected final float height;
    String align;
    protected Color color;
    protected final PApplet global;
    protected final ButtonEvent buttonEvent;

    public Button(PApplet global, float x, float y, String text, float w, float h, ButtonEvent buttonEvent) {
        this.pos.x = x;
        this.pos.y = y;

        this.color = new Color(0, 0, 0);

        this.width = w;
        this.height = h;

        this.text = text;

        this.global = global;

        this.buttonEvent = buttonEvent;
    }

    public void display() {
        global.fill(this.color.getRGB());
        global.rect(this.pos.x, this.pos.y, this.width,this.height,9);
        global.textSize(20);
        global.textAlign(PConstants.CENTER, PConstants.CENTER);
        global.fill(255);
        global.text(text, pos.x + this.width/2,pos.y + this.height/2);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean contains(float x,float y) {
        if (x <= this.pos.x + this.width &&
                x >= this.pos.x &&
                y <= this.pos.y + this.height &&
                y >= this.pos.y) {
            return true;
        } else {
            return false;
        }
    }

    public void onClick(PhageFighter global) {
        this.buttonEvent.onClick(global);
    }
}

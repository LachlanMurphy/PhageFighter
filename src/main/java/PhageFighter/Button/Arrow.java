package PhageFighter.Button;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Arrow extends Button{
    private final int SIZE = 40;
    private PVector point1;
    private PVector point2;
    private PVector point3;

    public Arrow(PApplet global, float x, float y, boolean direction, ButtonEvent buttonEvent) {
        super(global, x, y, "", 0.0f, 0.0f, buttonEvent);

        int size = direction ? SIZE/2 : -SIZE/2;

        point1 = new PVector(x + size, y);
        point2 = new PVector(x - size, y + size);
        point3 = new PVector(x - size, y - size);
    }

    @Override
    public void display() {
        global.fill(this.color.getRGB());
        global.triangle(point1.x, point1.y,
                point2.x, point2.y,
                point3.x, point3.y);
    }

    @Override
    public boolean contains(float x, float y) {
        PVector p = new PVector(global.mouseX, global.mouseY);

        PVector v0 = PVector.sub(point3, point1);
        PVector v1 = PVector.sub(point2, point1);
        PVector v2 = PVector.sub(p, point1);

        float dot00 = v0.dot(v0);
        float dot01 = v0.dot(v1);
        float dot02 = v2.dot(v0);
        float dot11 = v1.dot(v1);
        float dot12 = v2.dot(v1);

        float invDenom = 1.0f / (dot00 * dot11 - dot01 * dot01);
        float u = (dot11 * dot02 - dot01 * dot12) * invDenom;
        float v = (dot00 * dot12 - dot01 * dot02) * invDenom;

        return (u >= 0) && (v >= 0) && (u + v <= 1);
    }
}

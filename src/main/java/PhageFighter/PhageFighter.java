package PhageFighter;

import PhageFighter.Characters.Character;
import PhageFighter.Characters.CharacterFactory;
import processing.core.PApplet;
import processing.core.PImage;

public class PhageFighter extends PApplet{
    // globals

    // enums
    private enum Screen {
        Intro,
        Menu,
        Game,
        Pause
    }

    // members
    private Screen screen;
    private Character player;

    // Image Assets
    private PImage introImage;
    private PImage gameImage;

    public void settings(){
        size(700, 500);

        introImage = loadImage("images/background.jpg");
        gameImage = loadImage("images/gameBackground.jpg");

        player = CharacterFactory.createPlayer(this);

        screen = Screen.Intro;
    }

    public void draw(){
        switch (screen){
            case Intro: drawIntro(); break;
            case Menu: drawMenu(); break;
            case Game: drawGame(); break;
            case Pause: drawPause(); break;
        }
    }

    public void drawIntro(){
        image(introImage, 0, 0);
    }

    public void drawGame() {
        image(gameImage, 0, 0);

        // step then draw
        player.step();
        player.display();
    }

    public void drawMenu() {

    }

    public void drawPause() {

    }

    public void keyPressed(){
        if (screen == Screen.Intro) {
            screen = Screen.Game;
        }
    }

    public static void main(String[] args){
        String[] processingArgs = {"MySketch"};
        PhageFighter mySketch = new PhageFighter();
        PApplet.runSketch(processingArgs, mySketch);
    }
}
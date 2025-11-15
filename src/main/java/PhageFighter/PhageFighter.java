package PhageFighter;

import PhageFighter.Characters.Character;
import PhageFighter.Characters.CharacterFactory;
import PhageFighter.Characters.Player;
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

    public void settings(){
        size(700, 500);

        introImage = loadImage("background.jpg");

        player = CharacterFactory.createPlayer(this);
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

    }

    public void drawMenu() {

    }

    public void drawPause() {

    }

    public void buttonPressed(int buttonId){
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
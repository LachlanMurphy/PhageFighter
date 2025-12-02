package PhageFighter;

import PhageFighter.Button.*;
import PhageFighter.Characters.*;
import PhageFighter.Characters.Character;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhageFighter extends PApplet{
    // globals
    // enums
    public enum Screen {
        Intro,
        Menu,
        Game,
        Pause,
        ChangeCharacter
    }

    // members
    private Screen screen;
    private Character player;
    private int playerType;
    private final int MAX_PLAYER_TYPE = 2;

    private List<Character> players;
    private List<Integer> keys;

    private List<Bullet> bullets;

    // Image Assets
    private PImage introImage;
    private PImage gameImage;
    private PImage menuImage;

    // menu buttons
    private List<Button> buttonsMenu;
    private List<Button> buttonsCharacter;


    public void settings() {
        size(700, 500);

        introImage = loadImage("images/background.jpg");
        gameImage = loadImage("images/gameBackground.jpg");
        menuImage = loadImage("images/menu.jpg");

        players = CharacterFactory.createCharacters(this);

        playerType = 0;
        player = players.get(playerType);

        screen = Screen.Intro;

        keys = new ArrayList<>();

        bullets = new ArrayList<>();

        // add menu buttons
        buttonsMenu = new ArrayList<>();
        buttonsMenu.add(new Button(this, 455, 200, "Play!", 200, 50, new ChangeScreen(Screen.Game)));
        buttonsMenu.add(new Button(this, 455, 300, "Change Character", 200, 50, new ChangeScreen(Screen.ChangeCharacter)));
        buttonsMenu.add(new Button(this, 455, 400, "Exit", 200, 50, new ExitEvent()));

        // character select buttons
        buttonsCharacter = new ArrayList<>();
        buttonsCharacter.add(new Button(this, 155, 450, "Select", 150, 30, new ChangeScreen(Screen.Menu)));
        buttonsCharacter.add(new Arrow(this, 80, 300, false, new ChangeCharacter(false)));
        buttonsCharacter.add(new Arrow(this, 380, 300, true, new ChangeCharacter(true)));
    }

    public void draw(){
        switch (screen){
            case Intro: drawIntro(); break;
            case Menu: drawMenu(); break;
            case Game: drawGame(); break;
            case Pause: drawPause(); break;
            case ChangeCharacter: drawChangeCharacter(); break;
        }
    }

    public void drawIntro(){
        image(introImage, 0, 0);
    }

    public void drawGame() {
        image(gameImage, 0, 0);

        // step then draw
        player.step(keys);
        player.display();

        // draw bullets
        for (Bullet bullet : bullets) {
            bullet.step();
            bullet.display();
        }
    }

    public void drawMenu() {
        image(menuImage, 0, 0);

        for (Button button : buttonsMenu){
            button.display();
        }

        player.displayCharacter();
    }

    public void drawPause() {

    }

    public void drawChangeCharacter() {
        image(menuImage, 0, 0);

        for (Button button : buttonsCharacter) {
            button.display();
        }

        // draw character enlarged
        player.displayCharacter();

        // draw character name
        fill(0);
        textSize(32);
        textAlign(CENTER, CENTER);
        text(player.getName(), 555, 200);

        // draw stats
        textSize(20);
        text(player.getDescription(), 555, 250);
        text("Health: " + player.getHealthMax(), 555, 320);
        text("Damage: " + player.getDamage(), 555, 370);
        text("Ability: " + player.getAbility(), 555, 420);
    }

    public void keyPressed(){
        switch (screen) {
            case Intro: screen = Screen.Menu; break;
            case Game: if (!keys.contains(keyCode)) keys.add(keyCode); break;
        }
    }

    public void keyReleased(){
        switch (screen) {
            case Game: if (keys.contains(keyCode)) keys.remove((Integer) keyCode); break;
        }
    }

    public void mouseClicked() {
        switch (screen){
            case Menu: {
                for(Button button : buttonsMenu){
                    if (button.contains(mouseX, mouseY)){
                        button.onClick(this);
                        return;
                    }
                }
            } break;
            case ChangeCharacter: {
                for (Button button : buttonsCharacter) {
                    if (button.contains(mouseX, mouseY)) {
                        button.onClick(this);
                        return;
                    }
                }
            } break;
            case Intro: {
                screen = Screen.Menu;
            } break;
            case Game: {
                player.shoot(mouseX, mouseY);
            } break;
        }
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public void changeCharacter(int direction) {
        playerType = playerType == MAX_PLAYER_TYPE - 1 ? 0 : playerType + direction;
        if (playerType < 0) playerType = MAX_PLAYER_TYPE - 1;
        player = players.get(playerType);
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public static void main(String[] args){
        String[] processingArgs = {"MySketch"};
        PhageFighter mySketch = new PhageFighter();
        PApplet.runSketch(processingArgs, mySketch);
    }
}
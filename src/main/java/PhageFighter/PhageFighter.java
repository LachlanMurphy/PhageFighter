package PhageFighter;

import PhageFighter.Button.*;
import PhageFighter.Characters.*;
import PhageFighter.Characters.Character;
import PhageFighter.Events.EventBus;
import PhageFighter.Events.EventType;
import PhageFighter.Observers.Observer;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import PhageFighter.Observers.*;

public class PhageFighter extends PApplet{

    // globals
    // enums
    public enum Screen {
        Intro,
        Menu,
        Game,
        Pause,
        ChangeCharacter,
        LevelUp,
        GameEnd
    }

    // members
    private Screen screen;
    private Character player;
    private int playerType;
    private final int MAX_PLAYER_TYPE = 2;
    private Observer gameObserver;

    private List<Character> players;
    private List<Character> enemies;
    private List<Integer> keys;

    // Image Assets
    private PImage introImage;
    private PImage gameImage;
    private PImage menuImage;

    // menu buttons
    private List<Button> buttonsMenu;
    private List<Button> buttonsCharacter;
    private List<Button> buttonsLevelUp;
    private List<Button> buttonsPause;
    private List<Button> buttonsEndGame;

    // constants for drawing stats
    private final float uiX = 20;           // left margin
    private final float uiY = 20;           // bottom margin
    private final float barWidth = 200;
    private final float barHeight = 14;
    private final float barSpacing = 28;    // spacing between each bar

    // array of potential level up abilities to chose from
    private static final List<ButtonEvent> abilityChoice = Arrays.asList(
            new HealthIncreaseEvent(),
            new HealEvent(),
            new BulletDamageIncreaseEvent(),
            new MultiShotEvent()
    );

    private static final List<String> abilityDescription = Arrays.asList(
            "Max Health++",
            "Full Heal",
            "Bullet Damage++",
            "Bullets++"
    );

    // probability weights of above abilities being available when level up
    private final double[] weights = {0.30, 0.30, 0.30, 0.10};


    public void settings() {
        size(700, 500, P2D);

        introImage = loadImage("images/background.jpg");
        gameImage = loadImage("images/gameBackground.jpg");
        menuImage = loadImage("images/menu.jpg");

        players = CharacterFactory.createCharacters(this);
        enemies = new ArrayList<>();

        playerType = 0;
        player = players.get(playerType);

        screen = Screen.Intro;

        keys = new ArrayList<>();

        // initialize observer
        gameObserver = new Observer();
        EventBus.getInstance().attach(gameObserver, EventType.All);

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

        // level up buttons
        buttonsLevelUp = new ArrayList<>();
        // buttons created upon call of levelUp()

        buttonsPause = new ArrayList<>();
        buttonsPause.add(new Button(this, width/2.0f-100, 150, "Resume", 200, 50, new ChangeScreen(Screen.Game)));
        buttonsPause.add(new Button(this, width/2.0f-100, 250, "Main Menu", 200, 50, new ChangeScreen(Screen.Menu)));
        buttonsPause.add(new Button(this, width/2.0f-100, 350, "Exit Game", 200, 50, new ExitEvent()));

        buttonsEndGame = new ArrayList<>();
        buttonsEndGame.add(new Button(this, width/2.0f - 100, height/2.0f+25, "Main Menu", 200, 50, new ChangeScreen(Screen.Menu)));
    }

    public void draw() {
        switch (screen){
            case Intro: drawIntro(); break;
            case Menu: drawMenu(); break;
            case Game: drawGame(); break;
            case Pause: drawPause(); break;
            case ChangeCharacter: drawChangeCharacter(); break;
            case LevelUp: drawLevelUp(); break;
            case GameEnd: drawGameEnd();
        }
    }

    private void drawGameEnd() {
        fill(255);
        textAlign(CENTER, CENTER);
        text("Game Over!", width/2.0f, height/2.0f - 50);

        for (Button button : buttonsEndGame) {
            button.display();
        }
    }

    private void drawLevelUp() {
        fill(255);
        textAlign(CENTER, CENTER);
        text("Level Up!", width/2.0f, 100);

        for (Button  button : buttonsLevelUp) {
            button.display();
        }
    }

    public void drawIntro(){
        image(introImage, 0, 0);
    }

    public void drawGame() {
        if (player.getHealth() <= 0) {
            screen = Screen.GameEnd;
            return;
        }
        image(gameImage, 0, 0);

        if (keys.contains(Keys.esc)) this.screen = Screen.Pause;

        // step then draw
        player.step(keys, enemies);
        player.display();

        // spawn enemies
        spawnEnemies();

        // display and step enemies
        int enemySize = enemies.size();
        for (int enemy = enemySize - 1; enemy >= 0; enemy--) {
            if (enemies.get(enemy).getHealth() <= 0) {
                // if kill enemy then get experience
                if (player.gainExp(enemies.get(enemy).getExp())) levelUp();
                EventBus.getInstance().postMessage(EventType.EnemyDefeated);
                enemies.remove(enemy);
                continue;
            }
            enemies.get(enemy).step(Collections.singletonList(player));
            enemies.get(enemy).display();
        }

        // draw stats
        drawHealthBar(player.getHealth(), player.getHealthMax());
        drawExperienceBar(player.getExp(), player.getExpMax());
        drawLevel(player.getLevel());
        drawCooldown(player.getCooldown(), player.getCooldownElapsed());
    }

    private void levelUp() {
        // create three random buttons that will be used
        // for the abilities the player can choose from
        Random random = new Random();
        buttonsLevelUp.clear();

        // chose 3 random abilities using the weights array as odds
        List<Integer> abilityIndex = IntStream.range(0, weights.length)
                .boxed()
                .collect(Collectors.toMap(i -> i, i -> weights[i])) // index → weight
                .entrySet().stream()
                .sorted(Comparator.comparingDouble(a -> Math.random() / a.getValue()))
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();

        for (int i = 0; i < 3; i++) {
            ButtonEvent event = abilityChoice.get(abilityIndex.get(i));
            String buttonDescription = abilityDescription.get(abilityIndex.get(i));

            buttonsLevelUp.add(
                    new Button(
                            this,
                            (i+1) * width / 4.0f - 50.0f + 10*i, height / 2.0f,
                            buttonDescription, 150.0f, 50.0f,
                            event
                    )
            );
        }

        this.screen = Screen.LevelUp;
    }

    private void drawHealthBar(float health, float maxHealth) {
        // draw the health bar
        float pct = constrain(health / maxHealth, 0, 1);

        float x = uiX;
        float y = height - uiY - barSpacing * 0;

        // Label
        fill(255);
        textAlign(LEFT, CENTER);
        text("HP:", x, y);

        // Background
        float barX = x + 40;
        float barY = y - barHeight / 2;

        fill(60);
        rect(barX, barY, barWidth, barHeight);

        // Filled part
        fill(lerpColor(color(255,0,0), color(0,255,0), pct));
        rect(barX, barY, barWidth * pct, barHeight);
    }

    private void drawExperienceBar(float exp, float maxExp) {
        float pct = constrain(exp / maxExp, 0, 1);

        float x = uiX;
        float y = height - uiY - barSpacing * 1;

        // Label
        fill(255);
        textAlign(LEFT, CENTER);
        text("XP:", x, y);

        // Background
        float barX = x + 40;
        float barY = y - barHeight / 2;

        fill(60);
        rect(barX, barY, barWidth, barHeight);

        // Filled part
        fill(0, 120, 255);
        rect(barX, barY, barWidth * pct, barHeight);
    }

    private void drawLevel(int level) {
        float y = height - uiY - barSpacing * 2;

        fill(255);
        textAlign(LEFT, CENTER);
        text("Level: " + level, uiX, y);
    }

    private void drawCooldown(long cooldownDuration, long nextUseTime) {
        float percentFill = map(nextUseTime - System.currentTimeMillis(), 0, cooldownDuration, 1, 0);
        percentFill = constrain(percentFill, 0, 1);

        // draw backplate
        fill(0);
        circle(width-50, height-50, 50);

        fill(0, 255, 0);
        noStroke();
        arc(width-50, height-50, 45, 45, -PI/2, 2*PI*percentFill-PI/2, PIE);
    }

    private void spawnEnemies() {
        Random random = new Random();

        // for each enemy type, each one has a different spawn rate per frame

        // virus
        float spawnChance = map(player.getLevel(), 1, 50, 0.01f, 0.10f);  // 2% → 30%
        spawnChance = constrain(spawnChance, 0.002f, 0.030f);   // safety cap
        if (random.nextFloat() < spawnChance) {
            enemies.add(CharacterFactory.createVirus(this));
        }

        // don't let enemies past this spawn until level 3
        if (player.getLevel() < 5) return;

        // mutant phage
        spawnChance = map(player.getLevel(), 3, 50, 0.01f, 0.10f);  // 2% → 30%
        spawnChance = constrain(spawnChance, 0.01f, 0.10f);   // safety cap
        if (random.nextFloat() < spawnChance) {
            enemies.add(CharacterFactory.createMutantPhage(this));
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
        fill(255);
        textAlign(CENTER, CENTER);
        text("Game Paused", width/2.0f, 100);

        for (Button  button : buttonsPause) {
            button.display();
        }
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
        if (Objects.requireNonNull(screen) == Screen.Intro) {
            screen = Screen.Menu;
        }

        // ignore esc key default behavior
        if (key == ESC) {
            key = 0;
        }

        if (!keys.contains(keyCode)) keys.add(keyCode);
    }

    public void keyReleased(){
        if (keys.contains(keyCode)) keys.remove((Integer) keyCode);
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
            case LevelUp: {
                for (Button button : buttonsLevelUp) {
                    if (button.contains(mouseX, mouseY)) {
                        button.onClick(this);
                        return;
                    }
                }
            } break;
            case Pause: {
                for (Button button : buttonsPause) {
                    if (button.contains(mouseX, mouseY)) {
                        button.onClick(this);
                        return;
                    }
                }
            } break;
            case GameEnd: {
                for (Button button : buttonsEndGame) {
                    if (button.contains(mouseX, mouseY)) {
                        button.onClick(this);
                        return;
                    }
                }
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

    public PVector getPlayerPos() { return player.getPos(); }

    public void playerHealthIncrease() {
        player.healthIncrease();
        this.screen = Screen.Game;
    }

    public void playerHeal() {
        player.heal();
        this.screen = Screen.Game;
    }

    public void playerBulletIncrease() {
        player.bulletIncrease();
        this.screen = Screen.Game;
    }

    public void playerMultiShotPlus() {
        player.addMultiShot();
        this.screen = Screen.Game;
    }

    public static void main(String[] args){
        String[] processingArgs = {"MySketch"};
        PhageFighter mySketch = new PhageFighter();
        PApplet.runSketch(processingArgs, mySketch);
    }
}
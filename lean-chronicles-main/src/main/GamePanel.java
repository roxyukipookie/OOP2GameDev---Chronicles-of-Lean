package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import java.awt.image.BufferedImage;
import javax.swing.*;

import javax.swing.JPanel;

import entity.Dragon;
import entity.Entity;
import entity.Knight;
import entity.Orc;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;//48 x 48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //48*26 pixels
    public final int screenHeight = tileSize  * maxScreenRow;//48*12 pixels

    //FOR FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;
    private Dimension originalScreenSize;
    private GraphicsDevice graphicsDevice;

    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //FPS
    int FPS = 60;

    //instantiations
    TileManager objTileManager = new TileManager(this);
    KeyHandler objKeyHandler = new KeyHandler(this);
    Thread gameThread;
    public UI ui = new UI(this);
    //Sound music = new Sound();
    // public Sound se = new Sound();
    public CollisionChecker objChecker = new CollisionChecker(this);

    // ENTITIES
    public Knight objPlayer = new Knight(this, objKeyHandler);
    public ArrayList<Entity> enemies;

    //Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;

    // public boolean isPaused = false;
    public final int dialogueState = 3;
    public final int optionsState = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        enemies = new ArrayList<>();
        this.originalScreenSize = new Dimension(screenWidth, screenHeight);
        this.graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.addKeyListener(objKeyHandler);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void setupGame() {
        gameState = playState;
        // playMusic(0);
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();

        if (fullScreenOn) {
            setFullScreen(true); // Set the game panel to fullscreen mode
        }
        System.out.println("Game setup complete. GameState: " + gameState);

    }

    public void setFullScreen(boolean enable) {
        if (enable && graphicsDevice.isFullScreenSupported()) {
            JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
            window.dispose(); // Dispose current window

            window.setUndecorated(true); // Remove window decorations
            window.setResizable(false); // Disable resizing in fullscreen mode

            graphicsDevice.setFullScreenWindow(window); // Enter fullscreen mode

            if (graphicsDevice.isDisplayChangeSupported()) {
                DisplayMode displayMode = new DisplayMode(originalScreenSize.width, originalScreenSize.height, 32, DisplayMode.REFRESH_RATE_UNKNOWN);
                graphicsDevice.setDisplayMode(displayMode); // Set display mode
            }
        } else {
            graphicsDevice.setFullScreenWindow(null); // Exit fullscreen mode
        }

        // Repaint to reflect changes
        this.repaint();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if(gameState == playState) {
            objPlayer.update();

            Iterator<Entity> iterator = enemies.iterator();
            while (iterator.hasNext()) {
                Entity enemy = iterator.next();
                enemy.update();
                if (!enemy.isAlive) {
                    iterator.remove();
                }
            }

            int gameOverState = 0;
            if (!objPlayer.isAlive && gameState != gameOverState) {
                gameState = gameOverState;
                objPlayer.isAlive = true;
                ui.currentDialogue = "Game Over! Would you like to try again?";
                ui.subState = 4; // Assuming 3 is the substate for end game confirmation
                ui.commandNum = 0; // Set to the default command
            }
        }
    }

    public void spawn(String type) {
        Random rand = new Random();
        int spawnX, spawnY;
        int side = rand.nextInt(4);

        switch(side) {
            case 0:
                spawnX = rand.nextInt(worldWidth - 50);
                spawnY = 0;
                break;
            case 1:
                spawnX = rand.nextInt(worldWidth - 50);
                spawnY = worldHeight - 50;
                break;
            case 2:
                spawnX = 0;
                spawnY = rand.nextInt(worldHeight - 50);
                break;
            case 3:
                spawnX = worldWidth - 50;
                spawnY = rand.nextInt(worldHeight - 50);
                break;
            default:
                spawnX = 0;
                spawnY = 0;
                break;
        }

        if(type == "dragon") {
            enemies.add(new Dragon(this, spawnX, spawnY));
        } else if(type == "orc") {
            enemies.add(new Orc(this, spawnX, spawnY));
        }

        System.out.println("spawned at " + spawnX + ", " + spawnY);
    }

    public void clear() {
        enemies.clear();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        //TITLE SCREEN
        if(gameState == titleState ) { //ONLY SPECIFIC ITEMS TO BE DISPLAYED
            ui.animationCounter++;
            ui.draw(g2);
        } else {
            //TILE
            objTileManager.draw(g2);

            // enemies
            for(Entity enemy : enemies) {
                enemy.draw(g2);
            }

            //PLAYER
            objPlayer.draw(g2);

            //UI
            ui.draw(g2);
            g2.dispose();


            //MUSIC
           /*public void playMusic(int i) {
                music.setFile(i);
                music.play();
                music.loop();
            }
            public void stopMusic() {
                music.stop();
            }
            public void playSE(int i) {
                se.setFile(i);
                se.play();
            }*/
        }
    }
}
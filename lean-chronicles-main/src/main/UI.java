package main;

import entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B, customFont;
    public String message = "";
    int messageCounter = 0;
    int animationCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    int subState = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        try {
            //File fontFile = new File("/Resource/TheWildBreathOfZelda-15Lv.ttf");
            InputStream inputStream = getClass().getResourceAsStream("/Resource/TheWildBreathOfZelda-15Lv.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(Font.BOLD, 60F); // Adjust style and size as needed
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        //TITLE STATE
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        //PAUSE STATE
        if(gp.gameState == gp.pauseState) {
            drawPauseSceen();
        }

        //OPTION STATE
        if(gp.gameState == gp.optionsState) {
            drawOptionsScreen();
        }
    }

    public void drawOptionsScreen() {
        System.out.println("Drawing options screen");
        g2.setColor(Color.red);
        g2.setFont(g2.getFont().deriveFont(24F));

        //SUBWINDOW
        int frameX = gp.tileSize * 5;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 7;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch(subState) {
            case 0: options_top(frameX, frameY); break;
            case 1: break;
            case 2: options_control(frameX, frameY); break;
            case 3: options_endGameConfirmation(frameX, frameY); break;
            case 4: gameEnd(frameX, frameY); break;
        }
        gp.objKeyHandler.enterPressed = false;
    }

    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        //TITLE
        String text = "Options";
        textX = getXForCenterText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);


        //FULL SCREEN ON/OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*2;
        g2.drawString("Full Screen", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX-25, textY);
        }

        //MUSIC
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX-25, textY);
        }

        //SE
        textY += gp.tileSize;
        g2.drawString("Sound Effects", textX, textY);
        if(commandNum == 2) {
            g2.drawString(">", textX-25, textY);
        }
        //CONTROL
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if(commandNum == 3) {
            g2.drawString(">", textX-25, textY);
            if(gp.objKeyHandler.enterPressed){
                subState = 2;
                commandNum = 0;
            }
        }

        //ENDGAME
        textY += gp.tileSize;
        g2.drawString("Exit Game", textX, textY);
        if(commandNum == 4) {
            g2.drawString(">", textX-25, textY);
            if(gp.objKeyHandler.enterPressed){
                subState = 3;
                commandNum = 0;
            }
        }

        //BACK
        textY += gp.tileSize*2;
        g2.drawString("Back", textX, textY);
        if(commandNum == 5) {
            g2.drawString(">", textX-25, textY);
        }

        //FULLSCREEN CHECKBOX
        textX = frameX + (int)(gp.tileSize*4.5);
        textY = frameY + gp.tileSize*2 + 23;
        g2.setStroke(new BasicStroke(4));
        g2.drawRect(textX, textY, 24, 24);
        if(gp.fullScreenOn) {
            g2.setColor(Color.white);
            g2.fillRect(textX, textY, 24, 24);
        }


        //MUSIC VOLUME
        textY+= gp.tileSize;
        g2.drawRect(textX, textY, 90, 24);

        //SOUND EFFECT VOLUME
        textY+= gp.tileSize;
        g2.drawRect(textX, textY, 90, 24);

    }

    public void  drawTitleScreen() {
        g2.setColor(new Color(252,61,3)); //SETTING BACKGROUND COLOR
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

        g2.setFont(customFont);

        //TITLE NAME
        //g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
        String text = "Mockingjay: Chronicles of Lean";
        int x = getXForCenterText(text);
        int y = gp.tileSize*3;
        //TITLE COLOR
        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        //DISPLAY MAIN CHARACTER
        x = (int) (gp.screenWidth/2.3);
        y += gp.tileSize*2;
        BufferedImage[] idleFrames = {gp.objPlayer.idle1, gp.objPlayer.right_attack1, gp.objPlayer.right_attack2, gp.objPlayer.right_attack3, gp.objPlayer.right_attack4};
        int frameIndex = (animationCounter / 10) % idleFrames.length; // Adjust the speed of animation by changing the divisor

        g2.drawImage(idleFrames[frameIndex], x, y, gp.tileSize*2, gp.tileSize*2, null);

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,30F));
        text = "NEW GAME";
        x = getXForCenterText(text);
        y += gp.tileSize*4;
        g2.drawString(text,x,y);
        if(commandNum == 0) {
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "QUIT";
        x = getXForCenterText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if(commandNum == 2) {
            g2.drawString(">", x-gp.tileSize, y);
        }
    }

    public void drawPauseSceen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 70));
        String text = "PAUSED";
        int x = getXForCenterText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text,x,y);
    }

    public void drawDialogueScreen(Player player) {
        //WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize * 3;

        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25));
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString(currentDialogue, x, y);
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0,0,0,200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    public int getXForCenterText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }

    public int getXforAlignToRightText(String text, int tailX) {
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        return tailX - textWidth;
    }


    //Control Option
    public void options_control(int frameX, int frameY) {

        int textX;
        int textY;

        //TITLE
        String text = "Controls";
        textX = frameX + (int)(gp.tileSize*2.6);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY); textY+=gp.tileSize;
        g2.drawString("Attack", textX, textY); textY+=gp.tileSize;
        g2.drawString("Sprint", textX, textY); textY+=gp.tileSize;
        g2.drawString("Options", textX, textY); textY+=gp.tileSize;
        g2.drawString("Options Panel", textX, textY); textY+=gp.tileSize;
        g2.drawString("", textX, textY); textY+=gp.tileSize;

        textX = frameX + (int)(gp.tileSize*5.1);
        textY = frameY + gp.tileSize*2;
        g2.drawString("WASD", textX, textY); textY+=gp.tileSize;
        g2.drawString("P", textX, textY); textY+=gp.tileSize;
        g2.drawString("O", textX, textY); textY+=gp.tileSize;
        g2.drawString("ESC", textX, textY); textY+=gp.tileSize;
        g2.drawString("Y/G", textX, textY); textY+=gp.tileSize;

        //BACK BUTTON
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if(gp.objKeyHandler.enterPressed){
                subState = 0;
                commandNum = 3;
            }
        }
    }

    public void options_endGameConfirmation(int frameX, int frameY) {

        int textX = frameX + gp.tileSize * 1;
        int textY = frameY + gp.tileSize * 2;

        currentDialogue = "Quit the game and \nreturn to the title screen?";

        for(String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }


        //YES
        String text = "Yes";
        textX = frameX + (int)(gp.tileSize*3.2);
        textY += gp.tileSize*3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0){
            g2.drawString (">", textX-25, textY);
            if(gp.objKeyHandler.enterPressed){
                subState = 0;
                gp.gameState = gp.titleState;
            }
        }

        //NO
        text = "No";
        textX = frameX + (int)(gp.tileSize*3.2);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString (">", textX-25, textY);
            if(gp.objKeyHandler.enterPressed){
                subState = 0;
                commandNum = 4;
            }
        }
    }

    public void gameEnd(int frameX, int frameY) {

        int textX = frameX + gp.tileSize * 1;
        int textY = frameY + gp.tileSize * 2;

        currentDialogue = "Quit the game and \nreturn to the title screen?";

        for(String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }


        //YES
        String text = "Yes";
        textX = frameX + (int)(gp.tileSize*3.2);
        textY += gp.tileSize*3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0){
            g2.drawString (">", textX-25, textY);
            if(gp.objKeyHandler.enterPressed){
                subState = 0;
                gp.gameState = gp.titleState;
            }
        }

        //NO
        text = "No";
        textX = frameX + (int)(gp.tileSize*3.2);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString (">", textX-25, textY);
            if(gp.objKeyHandler.enterPressed){
                subState = 0;
                commandNum = 4;
            }
        }
    }
}
package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Orc extends Enemy{
    public Orc(GamePanel gp, int x, int y) {
        super(gp);

        loadImage("/assets/enemy/Orc_Warrior/idle1.png");
        setDefaultValues();
        this.x = x;
        this.y = y;
        getEntityImage();
    }

    @Override
    public void setDefaultValues() {
        x = 100;
        y = 100;
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 3;
        maxHealth = 250;
        health = maxHealth;
        direction = "right";
    }

    @Override
    public void getEntityImage() {
        try {
            left1 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/left3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/left4.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/right3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/right4.png"));
            idle1 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/idle1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/idle2.png"));
            idle3 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/idle3.png"));
            idle4 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/idle4.png"));
            leftidle1 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/leftidle1.png"));
            leftidle2 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/leftidle2.png"));
            leftidle3 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/leftidle3.png"));
            leftidle4 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/leftidle4.png"));
            right_attack1 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/right_attack1.png"));
            right_attack2 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/right_attack2.png"));
            right_attack3 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/right_attack3.png"));
            right_attack4 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/right_attack4.png"));
            left_attack1 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/left_attack1.png"));
            left_attack2 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/left_attack2.png"));
            left_attack3 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/left_attack3.png"));
            left_attack4 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/Orc_Warrior/left_attack4.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        BufferedImage attack = null;
        BufferedImage attack_left = null;
        BufferedImage attack_right = null;

        if(isAttacking) {
            switch(attackFrame) {
                case 1:
                    attack_right = right_attack1;
                    attack_left = left_attack1;
                    break;
                case 2:
                    attack_right = right_attack2;
                    attack_left = left_attack2;
                    break;
                case 3:
                    attack_right = right_attack3;
                    attack_left = left_attack3;
                    break;
                case 4:
                    attack_right = right_attack4;
                    attack_left = left_attack4;
                    break;
            }
            if(direction == "right") {
                attack = attack_right;
                g2.drawImage(attack, x, y, gp.tileSize, gp.tileSize, null);
            } else if(direction == "left"){
                attack = attack_left;
                g2.drawImage(attack, x, y, gp.tileSize, gp.tileSize, null);
            } else {
                if(previousDirection == "left") {
                    attack = attack_left;
                    g2.drawImage(attack, x, y, gp.tileSize, gp.tileSize, null);
                } else {
                    attack = attack_right;
                    g2.drawImage(attack, x, y, gp.tileSize, gp.tileSize, null);
                }
            }
        } else {
            switch(direction) {
                case "left":
                    if(spriteNum == 1) {
                        image = left1;
                    }
                    if(spriteNum == 2) {
                        image = left2;
                    }
                    if(spriteNum == 3) {
                        image = left3;
                    }
                    if(spriteNum == 4) {
                        image = left4;
                    }
                    break;
                case "right":
                    if(spriteNum == 1) {
                        image = right1;
                    }
                    if(spriteNum == 2) {
                        image = right2;
                    }
                    if(spriteNum == 3) {
                        image = right3;
                    }
                    if(spriteNum == 4) {
                        image = right4;
                    }
                    break;
                case "idle":
                    if(spriteNum == 1) {
                        if(previousDirection == "right")
                            image = idle1;
                        else
                            image = leftidle1;
                    }
                    if(spriteNum == 2) {
                        if(previousDirection == "right")
                            image = idle2;
                        else
                            image = leftidle2;
                    }
                    if(spriteNum == 3) {
                        if(previousDirection == "right")
                            image = idle3;
                        else
                            image = leftidle3;
                    }
                    if(spriteNum == 4) {
                        if(previousDirection == "right")
                            image = idle4;
                        else
                            image = leftidle4;
                    }
                    break;
            }
            g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
        }
        drawHealthBar(g2, 40, gp.tileSize);
        drawDamageDisplays(g2);
    }

    public int getHealth() {
        return this.health;
    }
}

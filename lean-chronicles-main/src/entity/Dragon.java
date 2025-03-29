package entity;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Dragon extends Enemy {
    private int dragonHeight = 382;
    private int dragonWidth = 322;
    private int attackDimension = 1000;

    public Dragon(GamePanel gp, int x, int y) {
        super(gp);

        loadImage("/assets/enemy/dragon/tile003.png");
        setDefaultValues();
        this.x = x;
        this.y = y;
        getEntityImage();
        getAttackFXImage();
    }

    @Override
    public void setDefaultValues() {
        x = 100;
        y = 100;
        maxHealth = 1000;
        health = maxHealth;
        damage = 9999;
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 1;
        direction = "right";
    }

    @Override
    public void getEntityImage() {
        try {
            right1 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/dragon/tile003.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/dragon/tile004.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/dragon/tile005.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/dragon/tile0006.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/dragon/tile009.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/dragon/tile010.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/dragon/tile011.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/dragon/tile012.png"));
            right_attack1 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/dragon/tile003.png"));
            right_attack2 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/dragon/tile004.png"));
            right_attack3 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/dragon/tile005.png"));
            right_attack4 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/dragon/tile0006.png"));
            left_attack1 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/dragon/tile009.png"));
            left_attack2 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/dragon/tile010.png"));
            left_attack3 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/dragon/tile011.png"));
            left_attack4 = ImageIO.read(getClass().getResourceAsStream("/assets/enemy/dragon/tile012.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAttackFXImage() {
        try {
            attackFX1_right = ImageIO.read(getClass().getResourceAsStream("/assets/fx/red_slash/1.png"));
            attackFX2_right = ImageIO.read(getClass().getResourceAsStream("/assets/fx/red_slash/2.png"));
            attackFX3_right = ImageIO.read(getClass().getResourceAsStream("/assets/fx/red_slash/3.png"));
            attackFX4_right = ImageIO.read(getClass().getResourceAsStream("/assets/fx/red_slash/4.png"));
            attackFX1_left = ImageIO.read(getClass().getResourceAsStream("/assets/fx/red_slash/left1.png"));
            attackFX2_left = ImageIO.read(getClass().getResourceAsStream("/assets/fx/red_slash/left2.png"));
            attackFX3_left = ImageIO.read(getClass().getResourceAsStream("/assets/fx/red_slash/left3.png"));
            attackFX4_left = ImageIO.read(getClass().getResourceAsStream("/assets/fx/red_slash/left4.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        BufferedImage basicSlash = null;
        BufferedImage basicSlash_left = null;
        BufferedImage basicSlash_right = null;
        BufferedImage attack = null;
        BufferedImage attack_left = null;
        BufferedImage attack_right = null;
        int basicSlash_rightX = x + 300;
        int basicSlashY = y - 450;
        int basicSlash_leftX = x - 300;

        if(isAttacking) {
            switch(attackFrame) {
                case 1:
                    attack_right = right_attack1;
                    basicSlash_right = attackFX1_right;
                    attack_left = left_attack1;
                    basicSlash_left = attackFX1_left;
                    break;
                case 2:
                    attack_right = right_attack2;
                    basicSlash_right = attackFX2_right;
                    attack_left = left_attack2;
                    basicSlash_left = attackFX2_left;
                    break;
                case 3:
                    attack_right = right_attack3;
                    basicSlash_right = attackFX3_right;
                    attack_left = left_attack3;
                    basicSlash_left = attackFX3_left;
                    break;
                case 4:
                    attack_right = right_attack4;
                    basicSlash_right = attackFX4_right;
                    attack_left = left_attack4;
                    basicSlash_left = attackFX4_left;
                    break;
            }
            if(direction == "right") {
                attack = attack_right;
                basicSlash = basicSlash_right;
                g2.drawImage(attack, x, y, dragonHeight, dragonWidth, null);
                g2.drawImage(basicSlash, basicSlash_rightX, basicSlashY, attackDimension, attackDimension, null);
            } else if(direction == "left"){
                attack = attack_left;
                basicSlash = basicSlash_left;
                g2.drawImage(attack, x, y, dragonHeight, dragonWidth, null);
                g2.drawImage(basicSlash, basicSlash_leftX, basicSlashY, attackDimension, attackDimension, null);
            } else {
                if(previousDirection == "left") {
                    attack = attack_left;
                    basicSlash = basicSlash_left;
                    g2.drawImage(attack, x, y, dragonHeight, dragonWidth, null);
                    g2.drawImage(basicSlash, basicSlash_leftX, basicSlashY, attackDimension, attackDimension, null);
                } else {
                    attack = attack_right;
                    basicSlash = basicSlash_right;
                    g2.drawImage(attack, x, y, dragonHeight, dragonWidth, null);
                    g2.drawImage(basicSlash, basicSlash_rightX, basicSlashY, attackDimension, attackDimension, null);
                }
            }
        } else {
            switch(direction) {
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    if (spriteNum == 3) {
                        image = left3;
                    }
                    if (spriteNum == 4) {
                        image = left4;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    if (spriteNum == 3) {
                        image = right3;
                    }
                    if (spriteNum == 4) {
                        image = right4;
                    }
                    break;
            }
        }
        g2.drawImage(image, x, y, 382, 322, null);
        drawHealthBar(g2, 250, 382);
        drawDamageDisplays(g2);
    }
}

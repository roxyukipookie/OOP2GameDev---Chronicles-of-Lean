package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Knight extends Player{
    public Knight(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH);

        loadImage("/assets/princess_lean/knight/idle1.png");
        setDefaultValues();
        getEntityImage();
        getMoveFXImage();
        getAttackFXImage();
    }

    @Override
    public void getEntityImage() {
        try {
            left1 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/left3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/left4.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/right3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/right4.png"));
            idle1 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/idle1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/idle2.png"));
            idle3 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/idle3.png"));
            idle4 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/idle4.png"));
            leftidle1 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/leftidle1.png"));
            leftidle2 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/leftidle2.png"));
            leftidle3 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/leftidle3.png"));
            leftidle4 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/leftidle4.png"));
            right_attack1 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/right_attack1.png"));
            right_attack2 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/right_attack2.png"));
            right_attack3 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/right_attack3.png"));
            right_attack4 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/right_attack4.png"));
            left_attack1 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/left_attack1.png"));
            left_attack2 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/left_attack2.png"));
            left_attack3 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/left_attack3.png"));
            left_attack4 = ImageIO.read(getClass().getResourceAsStream("/assets/princess_lean/knight/left_attack4.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAttackFXImage() {
        try {
            attackFX1_right = null;
            attackFX2_right = null;
            attackFX3_right = ImageIO.read(getClass().getResourceAsStream("/assets/fx/slash/image/right3.png"));
            attackFX4_right = ImageIO.read(getClass().getResourceAsStream("/assets/fx/slash/image/right4.png"));
            attackFX1_left = null;
            attackFX2_left = null;
            attackFX3_left = ImageIO.read(getClass().getResourceAsStream("/assets/fx/slash/image/left3.png"));
            attackFX4_left = ImageIO.read(getClass().getResourceAsStream("/assets/fx/slash/image/left4.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        BufferedImage right_trail_ahead = null;
        BufferedImage right_trail_behind = null;
        BufferedImage left_trail_ahead = null;
        BufferedImage left_trail_behind = null;
        BufferedImage attack = null;
        BufferedImage attack_left = null;
        BufferedImage attack_right = null;
        BufferedImage basicSlash = null;
        BufferedImage basicSlash_left = null;
        BufferedImage basicSlash_right = null;
        int basicSlash_rightX = x + 15;
        int basicSlashY = y - 12;
        int basicSlash_leftX = x - 15;

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
            switch(direction) {
                case "left":
                    if(spriteNum == 1) {
                        left_trail_behind = moveFX1_left;
                    }
                    if(spriteNum == 2) {
                        left_trail_behind = moveFX2_left;
                    }
                    if(spriteNum == 3) {
                        left_trail_ahead = moveFX1_left;
                        left_trail_behind = moveFX3_left;
                    }
                    if(spriteNum == 4) {
                        left_trail_ahead = moveFX2_left;
                        left_trail_behind = moveFX4_left;
                    }
                    break;
                case "right":
                    if(spriteNum == 1) {
                        right_trail_behind = moveFX1_right;
                    }
                    if(spriteNum == 2) {
                        right_trail_behind = moveFX2_right;
                    }
                    if(spriteNum == 3) {
                        right_trail_ahead = moveFX1_right;
                        right_trail_behind = moveFX3_right;
                    }
                    if(spriteNum == 4) {
                        right_trail_ahead = moveFX2_right;
                        right_trail_behind = moveFX4_right;
                    }
                    break;
            }
            if(direction == "right") {
                attack = attack_right;
                basicSlash = basicSlash_right;
                g2.drawImage(attack, x, y, gp.tileSize, gp.tileSize, null);
                g2.drawImage(basicSlash, basicSlash_rightX, basicSlashY, gp.tileSize, gp.tileSize, null);
            } else if(direction == "left"){
                attack = attack_left;
                basicSlash = basicSlash_left;
                g2.drawImage(attack, x, y, gp.tileSize, gp.tileSize, null);
                g2.drawImage(basicSlash, basicSlash_leftX, basicSlashY, gp.tileSize, gp.tileSize, null);
            } else {
                if(previousDirection == "left") {
                    attack = attack_left;
                    basicSlash = basicSlash_left;
                    g2.drawImage(attack, x, y, gp.tileSize, gp.tileSize, null);
                    g2.drawImage(basicSlash, basicSlash_leftX, basicSlashY, gp.tileSize, gp.tileSize, null);
                } else {
                    attack = attack_right;
                    basicSlash = basicSlash_right;
                    g2.drawImage(attack, x, y, gp.tileSize, gp.tileSize, null);
                    g2.drawImage(basicSlash, basicSlash_rightX, basicSlashY, gp.tileSize, gp.tileSize, null);
                }
            }
        } else {
            switch(direction) {
                case "left":
                    if(spriteNum == 1) {
                        image = left1;
                        left_trail_behind = moveFX1_left;
                    }
                    if(spriteNum == 2) {
                        image = left2;
                        left_trail_behind = moveFX2_left;
                    }
                    if(spriteNum == 3) {
                        image = left3;
                        left_trail_ahead = moveFX1_left;
                        left_trail_behind = moveFX3_left;
                    }
                    if(spriteNum == 4) {
                        image = left4;
                        left_trail_ahead = moveFX2_left;
                        left_trail_behind = moveFX4_left;
                    }
                    break;
                case "right":
                    if(spriteNum == 1) {
                        image = right1;
                        right_trail_behind = moveFX1_right;
                    }
                    if(spriteNum == 2) {
                        image = right2;
                        right_trail_behind = moveFX2_right;
                    }
                    if(spriteNum == 3) {
                        image = right3;
                        right_trail_ahead = moveFX1_right;
                        right_trail_behind = moveFX3_right;
                    }
                    if(spriteNum == 4) {
                        image = right4;
                        right_trail_ahead = moveFX2_right;
                        right_trail_behind = moveFX4_right;
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
            // main character sprite
            g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
        }

        // movement dust trail
        if(this.speed >= 5) {
            int trailY = y + 12;
            if(direction == "right") {
                int right_trailX_behind = x - 26;
                int right_trailX_ahead = right_trailX_behind + 11;

                if(spriteNum == 2 || spriteNum == 3 || spriteNum == 4) {
                    g2.drawImage(right_trail_behind, right_trailX_behind, trailY, gp.tileSize, gp.tileSize, null);
                    g2.drawImage(right_trail_ahead, right_trailX_ahead, trailY, gp.tileSize, gp.tileSize, null);
                } else
                    g2.drawImage(right_trail_behind, right_trailX_behind, trailY, gp.tileSize, gp.tileSize, null);
            }

            if(direction == "left") {
                int left_trailX_behind = x + 26;
                int left_trailX_ahead = left_trailX_behind - 11;

                if(spriteNum == 2 || spriteNum == 3 || spriteNum == 4) {
                    g2.drawImage(left_trail_behind, left_trailX_behind, trailY, gp.tileSize, gp.tileSize, null);
                    g2.drawImage(left_trail_ahead, left_trailX_ahead, trailY, gp.tileSize, gp.tileSize, null);
                } else
                    g2.drawImage(left_trail_behind, left_trailX_behind, trailY, gp.tileSize, gp.tileSize, null);
            }
        }

        drawHealthBar(g2);
    }
}

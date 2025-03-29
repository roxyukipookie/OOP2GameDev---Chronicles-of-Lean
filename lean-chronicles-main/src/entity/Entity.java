package entity;

import main.DamageDisplay;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Entity {
    Random r = new Random();
    ArrayList<DamageDisplay> damageDisplays = new ArrayList<>();
    public int hitboxWidth;
    public int hitboxHeight;
    public int x, y, worldX, worldY;
    public int speed;
    public BufferedImage left1, left2, left3, left4, right1, right2, right3, right4, idle1, idle2, idle3, idle4,
            leftidle1, leftidle2, leftidle3, leftidle4,
            right_attack1, right_attack2, right_attack3, right_attack4, left_attack1, left_attack2, left_attack3, left_attack4,
            moveFX1_right, moveFX2_right, moveFX3_right, moveFX4_right, moveFX1_left, moveFX2_left, moveFX3_left, moveFX4_left
            , attackFX1_right, attackFX2_right, attackFX3_right, attackFX4_right, attackFX1_left, attackFX2_left, attackFX3_left, attackFX4_left, image;
    public String direction, previousDirection;
    public Rectangle solidArea;
    public boolean isAttacking = false;
    public boolean isAlive = true;
    public boolean isCrit = false;
    public boolean collisionOn = false;
    public int attackDuration = 4;
    public int attackFrame = 1;
    public int attackTime = 10;
    public int attackCounter = 0;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int maxHealth = 100;
    public int health = 100;
    public double damage = 50;
    public int damageRoll;
    public double damageModifier = 0.25;
    public int critRoll;
    public double crit = 2.25;

    String dialogues[] = new String[20];
    public void speak() {}
    int dialogueIndex = 0;

    abstract void setDefaultValues();
    abstract void getEntityImage();
    abstract void getAttackFXImage();
    public abstract void update();
    public abstract void draw(Graphics2D g2);

    public void loadImage(String imagePath) {
        try {
            InputStream is = getClass().getResourceAsStream(imagePath);
            if(is == null) {
                throw new IOException("res not found " + imagePath);
            }
            image = ImageIO.read(is);
            hitboxWidth = image.getWidth();
            hitboxHeight = image.getHeight();
            updateHitbox();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void updateHitbox() {
        hitboxHeight = (int) (hitboxHeight * 0.6);
        hitboxWidth = (int) (hitboxWidth * 0.6);
    }

    public void getMoveFXImage() {
        try {
            moveFX1_right = ImageIO.read(getClass().getResourceAsStream("/assets/fx/basic_smoke/left1.png"));
            moveFX2_right = ImageIO.read(getClass().getResourceAsStream("/assets/fx/basic_smoke/left3.png"));
            moveFX3_right = ImageIO.read(getClass().getResourceAsStream("/assets/fx/basic_smoke/left4.png"));
            moveFX4_right = ImageIO.read(getClass().getResourceAsStream("/assets/fx/basic_smoke/left5.png"));
            moveFX1_left = ImageIO.read(getClass().getResourceAsStream("/assets/fx/basic_smoke/1.png"));
            moveFX2_left = ImageIO.read(getClass().getResourceAsStream("/assets/fx/basic_smoke/3.png"));
            moveFX3_left = ImageIO.read(getClass().getResourceAsStream("/assets/fx/basic_smoke/4.png"));
            moveFX4_left = ImageIO.read(getClass().getResourceAsStream("/assets/fx/basic_smoke/5.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void addDamageDisplay(int damageAmount, boolean isCrit) {
        DamageDisplay display = new DamageDisplay(x, y, isCrit, String.valueOf(damageAmount));
        damageDisplays.add(display);
    }

    public void updateDamageDisplays() {
        Iterator<DamageDisplay> it = damageDisplays.iterator();
        while (it.hasNext()) {
            if (it.next().isExpired()) {
                it.remove();
            }
        }
    }
    public void drawDamageDisplays(Graphics2D g2) {
        for (DamageDisplay dd : damageDisplays) {
            dd.draw(g2);
        }
    }

}

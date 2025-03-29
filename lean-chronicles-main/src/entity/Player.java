package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.util.ArrayList;

public abstract class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        solidArea = new Rectangle(7, 15, 25, 25);
    }

    @Override
    public void setDefaultValues() {
        x = 400;
        y = 250;
        health = 100;
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 3;
        direction = "right";
    }

    @Override
    public void update() {
        gp.objPlayer.speak();

        // attack
        if(keyH.attackPressed && !isAttacking) {
            isAttacking = true;
            if(attackCounter != 0)
                attackCounter = 0;
        }

        if(isAttacking) {
            attackCounter++;
            if(attackCounter >= attackTime) {
                attackFrame++;
                attackCounter = 0;
            }

            if(attackFrame > attackDuration) {
                dealDamage(gp.enemies);
                isAttacking = false;
                attackFrame = 1;
            }
        }

        // sprite direction facing
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if(keyH.upPressed) {
                direction = "up";
                previousDirection = direction;
                //y -= speed;
            } else if(keyH.downPressed) {
                direction = "down";
                //y += speed;
            }
            if(keyH.leftPressed) {
                direction = "left";
                previousDirection = direction;
                x -= speed;
            } else if(keyH.rightPressed) {
                direction = "right";
                previousDirection = direction;
                x += speed;
            }
        }

        // sprite idle
        if(!keyH.upPressed && !keyH.downPressed && !keyH.leftPressed && !keyH.rightPressed) {
            direction = "idle";
        }

        // sprite animation
        spriteCounter++;
        if(spriteCounter > 12) {
            switch(spriteNum) {
                case 1:
                    spriteNum = 2;
                    break;
                case 2:
                    spriteNum = 3;
                    break;
                case 3:
                    spriteNum = 4;
                    break;
                case 4:
                    spriteNum = 1;
                    break;
            }

            spriteCounter = 0;
        }

        // sprinting
        if(keyH.sprintPressed) {
            setSpeed(5);
        } else {
            setSpeed(3);
        }

        //CHECK TILE COLLISION
        collisionOn = false;
        gp.objChecker.checkTile(this);

        //IF COLLISION IS FALSE, PLAYER CAN MOVE
        if(collisionOn == false) {
            if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                if(keyH.upPressed) { //working but character is glitching
                    previousDirection = direction;
                    worldY -= speed;
                }
                else if(keyH.downPressed) { //working but character is glitching
                    previousDirection = direction;
                    worldY += speed;
                }
                else if(keyH.leftPressed) {
                    direction = "left";
                    previousDirection = direction;
                    x -= speed;
                }
                else if(keyH.rightPressed) {
                    direction = "right";
                    previousDirection = direction;
                    x += speed;
                }
            }
        }
        checkHealth();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDialogue() {
        dialogues[0] = "Defeat the bastard to escape.";
    }

    public void speak() {
        gp.ui.currentDialogue = dialogues[0];
        //dialogueIndex++;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return this.health;
    }

    public void checkHealth() {
        if(health <= 0) {
            isAlive = false;
            System.out.println("player dead");
        }
    }

    private void dealDamage(ArrayList<Entity> enemies) {
        int enemyX, enemyY, playerX, playerY;

        for(Entity enemy : enemies) {
            enemyX = enemy.x + enemy.hitboxWidth / 2;
            enemyY = enemy.y + enemy.hitboxHeight / 2;
            playerX = x + hitboxWidth / 2;
            playerY = y + hitboxHeight / 2;

            int dx = Math.abs(playerX - enemyX);
            int dy = Math.abs(playerY - enemyY);
            double distance = Math.sqrt(dx * dx + dy * dy);
            double attackRange = 85;

            if(distance <= attackRange) {
                double totalDamage = 0;
                damageRoll = r.nextInt(2);
                double min = 0.01;
                double max = 0.25;
                damageModifier = min + (r.nextDouble() * (max - min));
                critRoll = r.nextInt(10);
                if(damageRoll == 0) {
                    totalDamage += damage - (damage * damageModifier);
                } else {
                    totalDamage += damage + (damage * damageModifier);
                }

                if(critRoll == 0) {
                    totalDamage *= crit;
                    isCrit = true;
                    System.out.print("critical ");
                }

                enemy.health -= Math.ceil(totalDamage);
                enemy.addDamageDisplay((int) Math.ceil(totalDamage), isCrit);
                System.out.println("enemy hit for " + Math.ceil(totalDamage));
                System.out.println("enemy current hp is " + enemy.health);
                totalDamage = 0;
                isCrit = false;
            }
        }
    }

    public void drawHealthBar(Graphics2D g2) {
        int healthWidth = 40; // Width of the health bar
        int healthHeight = 6; // Height of the health bar
        int healthX = x + (gp.tileSize / 2) - (healthWidth / 2); // Center the health bar above the player
        int healthY = y - healthHeight - 5; // Position the health bar 5 pixels above the player

        // Background of the health bar (empty part)
        g2.setColor(Color.gray);
        g2.fillRect(healthX, healthY, healthWidth, healthHeight);

        // Foreground of the health bar (filled part based on current health)
        g2.setColor(Color.green);
        int healthFill = (int) (healthWidth * (health / 100.0)); // Calculate fill width based on health percentage
        g2.fillRect(healthX, healthY, healthFill, healthHeight);

        // Optionally, draw a border around the health bar
        g2.setColor(Color.black);
        g2.drawRect(healthX, healthY, healthWidth, healthHeight);
    }
}
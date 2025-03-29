package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.util.ArrayList;

public abstract class Enemy extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    int prevX, prevY;
    long lastAttackTime;
    long attackCD = 5000; // 5 seconds

    public Enemy(GamePanel gp) {
        this.gp = gp;
        keyH = new KeyHandler(gp);
        lastAttackTime = System.currentTimeMillis() - attackCD;
    }

    @Override
    public void getAttackFXImage() {

    }

    @Override
    public void update() {
        // attack
        /*
        if(!isAttacking && ) {

            if(attackCounter != 0)
                attackCounter = 0;
        }
        */

        checkHealth();
        attackPlayer(this, gp.objPlayer);

        if(isAttacking) {
            attackCounter++;
            if(attackCounter >= attackTime) {
                attackFrame++;
                attackCounter = 0;
            }

            if(attackFrame > attackDuration) {
                dealDamage(gp.objPlayer);
                isAttacking = false;
                attackFrame = 1;
            }
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

        // moving
        moveTowards(this, gp.objPlayer, 2);
        int changeDirectionThreshold = 1;
        if(Math.abs(x - prevX) > changeDirectionThreshold) {
            if(x - prevX > 0) {
                direction = "right";
                previousDirection = direction;
            } else {
                direction = "left";
                previousDirection = direction;
            }
        }

        if(x - prevX == 0 && y - prevY == 0) {
            direction = "idle";
        }

        prevX = x;
        prevY = y;

        updateDamageDisplays();
    }

    public void attackPlayer(Entity enemy, Player player) {
        int enemyX = enemy.x + enemy.hitboxWidth / 2;
        int enemyY = enemy.y + enemy.hitboxHeight / 2;
        int playerX = player.x + player.hitboxWidth / 2;
        int playerY = player.y + player.hitboxHeight / 2;

        int dx = Math.abs(enemyX - playerX);
        int dy = Math.abs(enemyY - playerY);
        double distance = Math.sqrt(dx * dx + dy * dy);
        double attackRange = 75;

        long currentTime = System.currentTimeMillis();
        if(currentTime - lastAttackTime < attackCD) {
            return;
        }

        if(distance <= attackRange) {
            isAttacking = true;
            System.out.println("player hit!");
            lastAttackTime = currentTime;
        }
    }

    private void moveTowards(Entity enemy, Player player, int speed) {
        double dx = player.x - enemy.x;
        double dy = player.y - enemy.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double repelFactor = 0.1;
        double repelDamping = 0.95;
        int repelHeight = enemy.hitboxWidth / 2 + player.hitboxWidth / 2;
        int repelWidth = enemy.hitboxHeight / 2 + player.hitboxHeight / 2;
        int effectiveSpeed;
        boolean tooClose = false;

        for(Entity otherEnemy : gp.enemies) {
            if(otherEnemy != enemy) {
                double otherDx = otherEnemy.x - enemy.x;
                double otherDy = otherEnemy.y - enemy.y;
                double distanceMaintained = Math.sqrt(otherDx * otherDx + otherDy * otherDy);
                int otherRepelWidth = enemy.hitboxWidth / 2 + otherEnemy.hitboxWidth / 2;
                int otherRepelHeight = enemy.hitboxHeight / 2 + otherEnemy.hitboxWidth / 2;

                if(Math.abs(otherDx) < otherRepelWidth && Math.abs(otherDy) < otherRepelHeight) {
                    tooClose = true;
                    double repelDx = (enemy.x - otherEnemy.x) / distanceMaintained;
                    double repelDy = (enemy.y - otherEnemy.y) / distanceMaintained;
                    enemy.x += repelDx * repelFactor;
                    enemy.y += repelDy * repelFactor;
                    break;
                }
            }
        }

        if(Math.abs(dx) < repelWidth && Math.abs(dy) < repelHeight) {
            tooClose = true;
            effectiveSpeed = 1;
            double repelDx = dx / distance;
            double repelDy = dy / distance;
            enemy.x -= repelDx * repelFactor;
            enemy.y -= repelDy * repelFactor;
        } else {
            effectiveSpeed = speed;
        }

        if(!tooClose && distance > 0 && distance > effectiveSpeed) {
            dx = (dx / distance) * effectiveSpeed;
            dy = (dy / distance) * effectiveSpeed;

            dx *= repelDamping;
            dy *= repelDamping;

            enemy.x += (int) Math.round(dx);
            enemy.y += (int) Math.round(dy);
        }
    }

    private void dealDamage(Player player) {
        int enemyX, enemyY, playerX, playerY;

        enemyX = x + hitboxWidth / 2;
        enemyY = y + hitboxHeight / 2;
        playerX = player.x + hitboxWidth / 2;
        playerY = player.y + hitboxHeight / 2;

        int dx = Math.abs(enemyX - playerX);
        int dy = Math.abs(enemyY - playerY);
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

            player.health -= Math.ceil(totalDamage);
            player.addDamageDisplay((int) Math.ceil(totalDamage), isCrit);
            System.out.println("player hit for " + Math.ceil(totalDamage));
            System.out.println("player current hp is " + player.health);
            totalDamage = 0;
            isCrit = false;
        }
    }

    public void checkHealth() {
        if(health <= 0) {
            isAlive = false;
            System.out.println("enemy dead");
        }
    }

    public void drawHealthBar(Graphics2D g2, int width, int posX) {
        int healthWidth = width; // Width of the health bar remains constant
        int healthHeight = 6; // Height of the health bar remains constant
        int healthX = x + (posX / 2) - (healthWidth / 2); // Center the health bar above the player
        int healthY = y - healthHeight - 5; // Position the health bar 5 pixels above the player

        // Background of the health bar (empty part)
        g2.setColor(Color.gray);
        g2.fillRect(healthX, healthY, healthWidth, healthHeight);

        // Foreground of the health bar (filled part based on current health)
        g2.setColor(Color.RED);
        int healthFill = (int) ((double) health / maxHealth * healthWidth); // Calculate fill width as a percentage of max health
        g2.fillRect(healthX, healthY, healthFill, healthHeight);

        // Optionally, draw a border around the health bar
        g2.setColor(Color.black);
        g2.drawRect(healthX, healthY, healthWidth, healthHeight);
    }
}

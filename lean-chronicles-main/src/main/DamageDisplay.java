package main;

import java.awt.*;

public class DamageDisplay {
    int x, y;
    String damage;
    boolean isCrit;
    long displayTime = 1000; // Time in milliseconds to display the damage
    long startTime;

    public DamageDisplay(int x, int y, boolean isCrit, String damage) {
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.isCrit = isCrit;
        this.startTime = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return (System.currentTimeMillis() - startTime) > displayTime;
    }

    public void draw(Graphics2D g2) {
        if(isCrit)
            g2.setFont(new Font("Arial", Font.BOLD, 20));
        else
            g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.setColor(isCrit ? Color.RED : Color.YELLOW);
        g2.drawString(damage, x, y);
    }
}

package tile;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Tile {
    public Image image;
    public boolean collision;
    public boolean isObstacle;
    public List<Rectangle> solidAreas; // List of solid areas on the tile

    public Tile() {
        solidAreas = new ArrayList<>();
    }

    // Method to add a solid area to the tile
    public void addSolidArea(Rectangle solidArea) {
        solidAreas.add(solidArea);
    }

    // Method to check collision with the tile
    public boolean collides(Rectangle rect) {
        if (!collision)
            return false;

        if (!isObstacle)
            return false;

        for (Rectangle solidArea : solidAreas) {
            if (solidArea.intersects(rect)) {
                return true;
            }
        }
        return false;
    }
}

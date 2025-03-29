package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[30];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        // Load the default map
        loadMap("/assets/maps/map.txt");
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/assets/world/lavaland.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/assets/world/obstacles/wall1.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/assets/world/obstacles/wall2.png"));
            tile[2].collision = true;
//
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/assets/world/obstacles/wall3.png"));
            tile[3].collision = true;

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/assets/world/obstacles/wall4.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/assets/world/obstacles/gravel.png"));
            tile[5].collision = true;

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/assets/world/obstacles/streaks.png"));


            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/assets/world/obstacles/pebbles.png"));


            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/assets/world/obstacles/rock2.png"));


            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/assets/world/obstacles/lava3.png"));
            tile[9].collision = true;

            tile[10] = new Tile();
            tile[10].image = ImageIO.read(getClass().getResourceAsStream("/assets/world/obstacles/lava10.png"));
            tile[10].collision = true;

            tile[11] = new Tile();
            tile[11].image = ImageIO.read(getClass().getResourceAsStream("/assets/world/obstacles/lava4.png"));
            tile[11].collision = true;

            tile[12] = new Tile();
            tile[12].image = ImageIO.read(getClass().getResourceAsStream("/assets/world/obstacles/lava5.png"));
            tile[12].collision = true;

            tile[13] = new Tile();
            tile[13].image = ImageIO.read(getClass().getResourceAsStream("/assets/world/obstacles/purelava.png"));
            tile[13].collision = true;

            tile[14] = new Tile();
            tile[14].image = ImageIO.read(getClass().getResourceAsStream("/assets/world/obstacles/lavaInt.png"));
            tile[14].collision = true;



//
//            tile[4] = new Tile();
//            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/assets/world/tree.png"));
//            tile[4].collision = true;
//
//            tile[5] = new Tile();
//            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/assets/world/sand.png"));
//
//            tile[6] = new Tile();
//            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/assets/world/wavyyyyy.gif"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void switchMap(String filePath) {
        // Clear the current map
        for (int col = 0; col < gp.maxWorldCol; col++) {
            for (int row = 0; row < gp.maxWorldRow; row++) {
                mapTileNum[col][row] = 0;
            }
        }
        // Load the new map
        loadMap(filePath);
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.objPlayer.worldX + gp.objPlayer.screenX;
            int screenY = worldY - gp.objPlayer.worldY + gp.objPlayer.screenY;

            if(worldX + gp.tileSize > gp.objPlayer.worldX - gp.objPlayer.screenX &&
                    worldX - gp.tileSize < gp.objPlayer.worldX + gp.objPlayer.screenX &&
                    worldY + gp.tileSize > gp.objPlayer.worldY - gp.objPlayer.screenY &&
                    worldY - gp.tileSize < gp.objPlayer.worldY + gp.objPlayer.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            }
            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
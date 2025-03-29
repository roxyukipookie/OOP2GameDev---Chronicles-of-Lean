package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    public boolean upPressed, downPressed, leftPressed, rightPressed, attackPressed, sprintPressed, spawnPressed, clearPressed, enterPressed;
    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }


    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //TITLE STATE
        if(gp.gameState == gp.titleState) {
            if(code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if(code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                }
                if(gp.ui.commandNum == 1) {
                    //LOAD GAME ADD LATER
                }
                if(gp.ui.commandNum == 2) {
                    System.exit(0); //EXIT THE PROGRAM
                }
            }
        }

        //PLAY STATE
        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if(code == KeyEvent.VK_P) {
            attackPressed = true;
        }
        if(code == KeyEvent.VK_O) {
            sprintPressed = true;
        }
        if(code == KeyEvent.VK_SPACE) {
            System.out.println("Space key pressed. Current game state: " + gp.gameState); //expecting 1 since playState = 1
            //  gp.gameState = gp.pauseState; //game is PAUSED
            if(gp.gameState == gp.playState) { //game is paused
                gp.gameState = gp.pauseState;
                System.out.println("Game paused. New game state: " + gp.gameState); //expecting 2
            } else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
                System.out.println("Game resumed. New game state: " + gp.gameState); //expecting 1
            }
        }
        if(code == KeyEvent.VK_Q) {
            System.out.println("spawn key pressed");
            gp.spawn("orc");
            spawnPressed = true;
        }
        if(code == KeyEvent.VK_E) {
            System.out.println("clear key pressed");
            gp.clear();
            clearPressed = true;
        }
        if(code == KeyEvent.VK_R) {
            System.out.println("dragon spawned");
            gp.spawn("dragon");
            spawnPressed = true;
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        //OPTIONS STATE
        if (code == KeyEvent.VK_ESCAPE) {
            System.out.println("escape key pressed");
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.optionsState;
            } else {
                gp.gameState = gp.playState;
            }
        }

        if (gp.gameState == gp.optionsState) {
            // Process key events specific to the options pane
            optionsState(code);
        }
    }


    //OPTIONS CURSOR CONTROL
    public void optionsState(int code) {
        int maxCommandNum = 0;
        switch (gp.ui.subState) {
            case 0: maxCommandNum = 5; break;
            case 3: maxCommandNum = 1; break;
            case 4: maxCommandNum = 1; break;
        }
        if(code == KeyEvent.VK_Y) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if(code == KeyEvent.VK_G) {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER) {
            if(gp.ui.commandNum == 0) { // Check if commandNum corresponds to the Full Screen option
                gp.fullScreenOn = !gp.fullScreenOn; // Toggle the fullScreenOn flag
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_P) {
            attackPressed = false;
        }
        if(code == KeyEvent.VK_O) {
            sprintPressed = false;
        }
        if(code == KeyEvent.VK_Q) {
            spawnPressed = false;
        }
        if(code == KeyEvent.VK_E) {
            clearPressed = false;
        }
        if(code == KeyEvent.VK_R) {
            spawnPressed = true;
        }
    }
}
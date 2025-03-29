package main;

import login.Login;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        Login loginPage = new Login();
       // loginPage.setVisible(true);

        loginPage.setOnLoginListener(new Login.OnLoginListener() {
            @Override
            public void onLoginSuccess() {
                // After successful login, create and display the game window
                JFrame gameWindow = new JFrame();
                gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameWindow.setResizable(false);
                gameWindow.setTitle("Mockingjay: Chronicles of Lean");

                GamePanel gamePanel = new GamePanel();
                gameWindow.add(gamePanel);

                gameWindow.pack();
                gameWindow.setLocationRelativeTo(null);
                gameWindow.setVisible(true);

                gamePanel.startGameThread();
            }
        });
    }
}
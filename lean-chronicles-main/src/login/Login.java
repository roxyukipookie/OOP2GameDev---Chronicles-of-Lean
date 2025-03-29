package login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame{
    Connection con; //variable for databse connection
    Statement st; //variable for the sql
    ArrayList<Users> users = new ArrayList<>(); //arraylist for the table

    public interface OnLoginListener {
        void onLoginSuccess();
    }

    private Login.OnLoginListener loginListener;

    // Method to set the login listener
    public void setOnLoginListener(Login.OnLoginListener listener) {
        this.loginListener = listener;
    }

    private JLabel lblForm;
    private JLabel lblUsername;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lblPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JPanel Login;
    private JFrame frame;

    public Login() {
        frame = new JFrame("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.add(Login);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = String.valueOf(txtPassword.getPassword());

                if (!username.isEmpty() && !password.isEmpty()) {
                    try { //try catch to check if database in connected
                        Class.forName("com.mysql.cj.jdbc.Driver");//jdbc connector
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chroniclesoflean", "root", ""); //localhost mysql port, database name, username and password
                        String sql = "select * from player where username='" + username + "'"; //query to check last name to the database records
                        st = con.createStatement(); //create connection
                        ResultSet rs = st.executeQuery(sql); //executes the sql

                        if(rs.next()==true){ //checks if the username exist in the database table
                            JOptionPane.showMessageDialog(null, "Successful Login", "WELCOME", JOptionPane.INFORMATION_MESSAGE);
                            frame.dispose();
                            if(loginListener != null) {
                                // Call onLoginSuccess method
                                loginListener.onLoginSuccess();
                            }
                        } else{
                            JOptionPane.showMessageDialog(null, "No user found", "WARNING", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Fields are required", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterPage registerPanel = new RegisterPage();
                frame.dispose();
            }
        });
    }

    public static void main(String[] args) {
        Login frame = new Login();
        frame.setContentPane(frame.Login);
        frame.setBounds(600, 200, 300, 300);
        frame.setVisible(true);
    }
}

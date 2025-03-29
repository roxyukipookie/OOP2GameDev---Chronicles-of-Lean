package login;

import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class RegisterPage extends JFrame{
    private JTextField txtUsername;
    private JPanel registerPanel;
    private JLabel lblRegister;
    private JPasswordField txtSetPassword;
    private JButton registerButton;
    private JPasswordField txtPassword;
    private JButton btnRegister;
    private JButton btnBack;
    private JFrame frame;

    Connection con; //variable for databse connection
    Statement st; //variable for the sql
    ArrayList<Users> users = new ArrayList<>(); //arraylist for the table

    public  RegisterPage() {
        frame = new JFrame("Register");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.add(registerPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText().trim(); //trim() method removes whitespace from both ends of a string.
                String setPassword = String.valueOf(txtSetPassword.getPassword());
                String password = String.valueOf(txtPassword.getPassword());

                if (!username.isEmpty() && !password.isEmpty() && !setPassword.isEmpty()) {
                    if (!setPassword.equals(password)) {
                        // Passwords do not match
                        alert("Passwords do not match.", "Error");
                        return; // Exit the method since passwords do not match
                    }

                    try { //try catch to check if database in connected
                        Class.forName("com.mysql.cj.jdbc.Driver");//jdbc connector
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chroniclesoflean", "root", ""); //localhost mysql port, database name, username and password
                        String sql = "select * from player where username='" + username + "'"; //query to check username to the database records
                        st = con.createStatement(); //create connection
                        ResultSet rs = st.executeQuery(sql); //executes the sql

                        if(rs.next()==true){ //checks if the username already exist in the database table
                            alert("This username already exist", "Username not available");
                        }
                        else{
                            addUser(username,password); //calls addUser method to save new record to the database
                            alert("Record has been successfully added.");
                            frame.dispose();
                            Main.main(new String[]{});
                        }
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    alert("Please enter the required fields.", "Error");
                }
            }
        });
    }

    public void addUser(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chroniclesoflean", "root", "");

            String sql = "INSERT INTO `player`(`username`, `password`) " + "VALUES ('" + username + "','" + password + "')";
            st = con.createStatement();
            st.execute(sql);
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RegisterPage.class.getName()).log(Level.SEVERE, null, ex);
            alert("Error occurred. Please try again.");
        }
    }

    //method to show an info alert
    public void alert(String msg) {
        JOptionPane.showMessageDialog(rootPane, msg);
    }
    //method to show an error alert
    public void alert(String msg, String title) {
        JOptionPane.showMessageDialog(rootPane, msg, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void main (String[]args){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RegisterPage frame = new RegisterPage();
                    frame.setContentPane(frame.registerPanel);
                    frame.setBounds(600, 200, 300, 300);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
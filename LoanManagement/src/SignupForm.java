/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SignupForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton signUpButton;
    private JButton loginButton;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/users_info";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public SignupForm() {
        setTitle("Sign Up");
        setSize(800, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(210, 180, 140)); 
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

  
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setOpaque(false); 
        formPanel.setBorder(BorderFactory.createEmptyBorder(100, 150, 100, 150)); 

        Font labelFont = new Font("Verdana", Font.PLAIN, 24);
        Color labelColor = Color.BLACK; 

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        usernameLabel.setForeground(labelColor);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(labelColor);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(labelFont);
        confirmPasswordLabel.setForeground(labelColor);

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(confirmPasswordLabel);
        formPanel.add(confirmPasswordField);

       
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        buttonPanel.setOpaque(false); 
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0)); 

        Font buttonFont = new Font("Verdana", Font.BOLD, 24); 
        Color buttonColor = new Color(30, 144, 255); 

        signUpButton = new JButton("Sign Up");
        signUpButton.setFont(buttonFont);
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setBackground(buttonColor);
        signUpButton.setFocusPainted(false); 

        signUpButton.addActionListener(e -> handleSignUp());

        loginButton = new JButton("Login");
        loginButton.setFont(buttonFont);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(buttonColor);
        loginButton.setFocusPainted(false); 

        loginButton.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });

        buttonPanel.add(signUpButton);
        buttonPanel.add(loginButton);

       
        backgroundPanel.add(formPanel, BorderLayout.CENTER);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

       
        add(backgroundPanel);
    }

    private void handleSignUp() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String hashedPassword = hashPassword(password);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO users (username, password_hashed) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, hashedPassword);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Sign up successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                new LoginForm().setVisible(true);
                dispose();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error in database operation", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private String hashPassword(String password) {
       
        return password;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SignupForm().setVisible(true));
    }
}
   

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/users_info";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public LoginForm() {
        setTitle("Login");
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

       
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setOpaque(false); 
        formPanel.setBorder(BorderFactory.createEmptyBorder(150, 150, 100, 150)); 

        Font labelFont = new Font("Verdana", Font.PLAIN, 24); 
        Color labelColor = Color.BLACK;

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        usernameLabel.setForeground(labelColor);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(labelColor);

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        buttonPanel.setOpaque(false); 
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0)); 

        Font buttonFont = new Font("Verdana", Font.BOLD, 24); 
        Color buttonColor = new Color(30, 144, 255); 

        loginButton = new JButton("Login");
        loginButton.setFont(buttonFont);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(buttonColor);
        loginButton.setFocusPainted(false); 

        loginButton.addActionListener(e -> handleLogin());

        signUpButton = new JButton("Sign Up");
        signUpButton.setFont(buttonFont);
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setBackground(buttonColor);
        signUpButton.setFocusPainted(false); 

        signUpButton.addActionListener(e -> {
            new SignupForm().setVisible(true);
            dispose();
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(signUpButton);

        
        backgroundPanel.add(formPanel, BorderLayout.CENTER);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        
        add(backgroundPanel);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "SELECT password_hashed FROM users WHERE username = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, username);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String storedHashedPassword = resultSet.getString("password_hashed");

                    if (storedHashedPassword.equals(password)) { 
                        new LoanSystemAppForm(username).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Incorrect password", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Username not found", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error in database operation", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
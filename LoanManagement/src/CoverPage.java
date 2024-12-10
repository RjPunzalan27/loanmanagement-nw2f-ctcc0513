/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.*;
import java.awt.*;

public class CoverPage extends JFrame {

    public CoverPage() {
        setTitle("UmiBataan");
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

        
        JLabel titleLabel = new JLabel("UmiBataan", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 60)); 
        titleLabel.setForeground(Color.BLACK); 
        titleLabel.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0)); 

        
        JLabel taglineLabel = new JLabel("Securing Your Tomorrow, Today", SwingConstants.CENTER);
        taglineLabel.setFont(new Font("Verdana", Font.ITALIC, 24)); 
        taglineLabel.setForeground(new Color(70, 70, 70)); 
        taglineLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 150, 0)); 

        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        buttonPanel.setOpaque(false); 
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Verdana", Font.BOLD, 24)); 
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(30, 144, 255)); 
        loginButton.setFocusPainted(false);

        loginButton.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });

        
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Verdana", Font.BOLD, 24)); 
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setBackground(new Color(30, 144, 255)); 
        signUpButton.setFocusPainted(false); 

        signUpButton.addActionListener(e -> {
            new SignupForm().setVisible(true);
            dispose();
        });

   
        buttonPanel.add(loginButton);
        buttonPanel.add(signUpButton);

       
        backgroundPanel.add(titleLabel, BorderLayout.NORTH); 
        backgroundPanel.add(taglineLabel, BorderLayout.CENTER); 
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH); 

        
        add(backgroundPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CoverPage().setVisible(true));
    }
}
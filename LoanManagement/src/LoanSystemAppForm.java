/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class LoanSystemAppForm extends JFrame {
    private JLabel welcomeLabel;
    private JTextField loanAmountField;
    private JTextField interestRateField;
    private JTextField loanTermField;
    private JButton applyLoanButton;
    private String username;

    public LoanSystemAppForm(String username) {
        this.username = username;
        setTitle("Loan System");
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

        // Welcome panel
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setOpaque(false);
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        Font welcomeFont = new Font("Verdana", Font.BOLD, 36);
        Color welcomeColor = Color.BLACK;

        welcomeLabel = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        welcomeLabel.setFont(welcomeFont);
        welcomeLabel.setForeground(welcomeColor);

        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

        // Loan details panel
        JPanel loanPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        loanPanel.setOpaque(false);
        loanPanel.setBorder(BorderFactory.createEmptyBorder(100, 150, 100, 150));

        Font labelFont = new Font("Verdana", Font.PLAIN, 24);
        Color labelColor = Color.BLACK;

        JLabel loanAmountLabel = new JLabel("Loan Amount:");
        loanAmountLabel.setFont(labelFont);
        loanAmountLabel.setForeground(labelColor);

        JLabel interestRateLabel = new JLabel("Interest Rate (%):");
        interestRateLabel.setFont(labelFont);
        interestRateLabel.setForeground(labelColor);

        JLabel loanTermLabel = new JLabel("Loan Term (months):");
        loanTermLabel.setFont(labelFont);
        loanTermLabel.setForeground(labelColor);

        loanAmountField = new JTextField();
        interestRateField = new JTextField();
        loanTermField = new JTextField();
        applyLoanButton = new JButton("Apply for Loan");

        applyLoanButton.setFont(labelFont);
        applyLoanButton.setBackground(new Color(30, 144, 255));
        applyLoanButton.setForeground(Color.WHITE);

        applyLoanButton.addActionListener(e -> applyForLoan());

        loanPanel.add(loanAmountLabel);
        loanPanel.add(loanAmountField);
        loanPanel.add(interestRateLabel);
        loanPanel.add(interestRateField);
        loanPanel.add(loanTermLabel);
        loanPanel.add(loanTermField);

      
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));

        buttonPanel.add(applyLoanButton);


        backgroundPanel.add(welcomePanel, BorderLayout.NORTH);
        backgroundPanel.add(loanPanel, BorderLayout.CENTER);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        
        add(backgroundPanel);
    }

    private void applyForLoan() {
        String loanAmountStr = loanAmountField.getText().trim();
        String interestRateStr = interestRateField.getText().trim();
        String loanTermStr = loanTermField.getText().trim();

        if (loanAmountStr.isEmpty() || interestRateStr.isEmpty() || loanTermStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double loanAmount = Double.parseDouble(loanAmountStr);
            double interestRate = Double.parseDouble(interestRateStr);
            int loanTerm = Integer.parseInt(loanTermStr);

            double monthlyPayment = calculateMonthlyPayment(loanAmount, interestRate, loanTerm);
            DecimalFormat df = new DecimalFormat("#.##");

            
            JOptionPane.showMessageDialog(this, "Your monthly payment: ₱" + df.format(monthlyPayment), "Loan Calculation", JOptionPane.INFORMATION_MESSAGE);

            saveLoanApplication(loanAmount, interestRate, loanTerm, monthlyPayment);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter numbers only.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double calculateMonthlyPayment(double loanAmount, double interestRate, int loanTerm) {
        double monthlyInterestRate = interestRate / 100 / 12;
        int numberOfPayments = loanTerm;

        double monthlyPayment = (loanAmount * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));

        return monthlyPayment;
    }

    private void saveLoanApplication(double loanAmount, double interestRate, int loanTerm, double monthlyPayment) {
        String url = "jdbc:mysql://localhost:3306/LoanSystemDB";
        String user = "root";
        String password = ""; 

        String query = "INSERT INTO LoanApplications (username, loanAmount, interestRate, loanTerm, monthlyPayment) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setDouble(2, loanAmount);
            stmt.setDouble(3, interestRate);
            stmt.setInt(4, loanTerm);
            stmt.setDouble(5, monthlyPayment);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Loan application saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to save loan application: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoanSystemAppForm("Username").setVisible(true));
    }
}
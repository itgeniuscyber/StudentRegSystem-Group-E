/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.genius.studentregsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.regex.Pattern;

public class StudentRegSystem extends JFrame {
    private JTextField txtRegId, txtName, txtFaculty, txtProjectTitle, txtContact, txtEmail, txtImagePath;
    private JButton btnRegister, btnSearch, btnUpdate, btnDelete, btnClear, btnExit, btnBrowse;
    private JLabel lblImage;
    private static final String DB_URL = "jdbc:ucanaccess://VUE_Exhibition.accdb";
    
    public StudentRegSystem() {
        setTitle("VU Exhibition Registration System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        formPanel.add(new JLabel("Registration ID:"));
        txtRegId = new JTextField();
        formPanel.add(txtRegId);
        
        formPanel.add(new JLabel("Student Name:"));
        txtName = new JTextField();
        formPanel.add(txtName);
        
        formPanel.add(new JLabel("Faculty:"));
        txtFaculty = new JTextField();
        formPanel.add(txtFaculty);
        
        formPanel.add(new JLabel("Project Title:"));
        txtProjectTitle = new JTextField();
        formPanel.add(txtProjectTitle);
        
        formPanel.add(new JLabel("Contact Number:"));
        txtContact = new JTextField();
        formPanel.add(txtContact);
        
        formPanel.add(new JLabel("Email Address:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);
        
        formPanel.add(new JLabel("Project Image:"));
        JPanel imagePanel = new JPanel(new BorderLayout(5, 0));
        txtImagePath = new JTextField();
        txtImagePath.setEditable(false);
        btnBrowse = new JButton("Browse");
        imagePanel.add(txtImagePath, BorderLayout.CENTER);
        imagePanel.add(btnBrowse, BorderLayout.EAST);
        formPanel.add(imagePanel);
        
        // Image display area
        lblImage = new JLabel();
        lblImage.setPreferredSize(new Dimension(200, 200));
        lblImage.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        btnRegister = new JButton("Register");
        btnSearch = new JButton("Search");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        btnExit = new JButton("Exit");
        
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExit);
        
        // Add components to frame
        add(formPanel, BorderLayout.CENTER);
        add(lblImage, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        addActionListeners();
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void addActionListeners() {
        btnRegister.addActionListener(e -> registerParticipant());
        btnSearch.addActionListener(e -> searchParticipant());
        btnUpdate.addActionListener(e -> updateParticipant());
        btnDelete.addActionListener(e -> deleteParticipant());
        btnClear.addActionListener(e -> clearForm());
        btnExit.addActionListener(e -> System.exit(0));
        btnBrowse.addActionListener(e -> browseImage());
    }
    
    private void browseImage() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
        fileChooser.setFileFilter(filter);
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            txtImagePath.setText(selectedFile.getAbsolutePath());
            displayImage(selectedFile.getAbsolutePath());
        }
    }
    
    private void displayImage(String path) {
        ImageIcon imageIcon = new ImageIcon(path);
        Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        lblImage.setIcon(new ImageIcon(image));
    }
    
    private boolean validateInput() {
        if (txtRegId.getText().trim().isEmpty() || txtName.getText().trim().isEmpty() ||
            txtFaculty.getText().trim().isEmpty() || txtProjectTitle.getText().trim().isEmpty() ||
            txtContact.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate email format
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!Pattern.matches(emailRegex, txtEmail.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Invalid email format!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate contact number (assuming 10 digits)
        String contactRegex = "\\d{10}";
        if (!Pattern.matches(contactRegex, txtContact.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Contact number must be 10 digits!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void registerParticipant() {
        if (!validateInput()) return;
        
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "INSERT INTO Participants (RegistrationID, StudentName, Faculty, ProjectTitle, ContactNumber, Email, ImagePath) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, txtRegId.getText().trim());
                pstmt.setString(2, txtName.getText().trim());
                pstmt.setString(3, txtFaculty.getText().trim());
                pstmt.setString(4, txtProjectTitle.getText().trim());
                pstmt.setString(5, txtContact.getText().trim());
                pstmt.setString(6, txtEmail.getText().trim());
                pstmt.setString(7, txtImagePath.getText());
                
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Participant registered successfully!");
                clearForm();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void searchParticipant() {
        String regId = txtRegId.getText().trim();
        if (regId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Registration ID to search!");
            return;
        }
        
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT * FROM Participants WHERE RegistrationID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, regId);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    txtName.setText(rs.getString("StudentName"));
                    txtFaculty.setText(rs.getString("Faculty"));
                    txtProjectTitle.setText(rs.getString("ProjectTitle"));
                    txtContact.setText(rs.getString("ContactNumber"));
                    txtEmail.setText(rs.getString("Email"));
                    txtImagePath.setText(rs.getString("ImagePath"));
                    
                    if (rs.getString("ImagePath") != null) {
                        displayImage(rs.getString("ImagePath"));
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No participant found with this Registration ID!");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateParticipant() {
        if (!validateInput()) return;
        
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "UPDATE Participants SET StudentName=?, Faculty=?, ProjectTitle=?, ContactNumber=?, Email=?, ImagePath=? WHERE RegistrationID=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, txtName.getText().trim());
                pstmt.setString(2, txtFaculty.getText().trim());
                pstmt.setString(3, txtProjectTitle.getText().trim());
                pstmt.setString(4, txtContact.getText().trim());
                pstmt.setString(5, txtEmail.getText().trim());
                pstmt.setString(6, txtImagePath.getText());
                pstmt.setString(7, txtRegId.getText().trim());
                
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Participant information updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "No participant found with this Registration ID!");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteParticipant() {
        String regId = txtRegId.getText().trim();
        if (regId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Registration ID to delete!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this participant?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                String sql = "DELETE FROM Participants WHERE RegistrationID = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, regId);
                    
                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Participant deleted successfully!");
                        clearForm();
                    } else {
                        JOptionPane.showMessageDialog(this, "No participant found with this Registration ID!");
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void clearForm() {
        txtRegId.setText("");
        txtName.setText("");
        txtFaculty.setText("");
        txtProjectTitle.setText("");
        txtContact.setText("");
        txtEmail.setText("");
        txtImagePath.setText("");
        lblImage.setIcon(null);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new StudentRegSystem().setVisible(true);
        });
    }
}

/**
 *
 * @author nelly
 */


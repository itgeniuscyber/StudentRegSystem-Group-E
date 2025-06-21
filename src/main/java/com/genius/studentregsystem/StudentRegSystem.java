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
import javax.swing.border.Border;

public class StudentRegSystem extends JFrame {
    private JTextField txtRegId, txtName, txtFaculty, txtProjectTitle, txtContact, txtEmail, txtImagePath;
    private JButton btnRegister, btnSearch, btnUpdate, btnDelete, btnClear, btnExit, btnBrowse, btnViewAll;
    private JLabel lblImage;
    private static final String DB_URL = "jdbc:ucanaccess://VUE_Exhibition.accdb";
    
    public StudentRegSystem() {
        setTitle("VU Exhibition Registration System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Set modern look and feel.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Welcome Panel with Inspirational Message
        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(new Color(51, 153, 255));
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel welcomeLabel = new JLabel("Welcome to VU Innovation Exhibition!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel inspireLabel = new JLabel("Showcase Your Innovation, Shape the Future!");
        inspireLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        inspireLabel.setForeground(Color.WHITE);
        inspireLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        welcomePanel.add(inspireLabel);
        
        // Form Panel with modern styling
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 15, 15));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(25, 25, 25, 25),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(51, 153, 255), 2, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
            )
        ));
        formPanel.setBackground(Color.WHITE);
        
        // Create and style form labels and fields
        JLabel[] labels = {
            createStyledLabel("Registration ID:"),
            createStyledLabel("Student Name:"),
            createStyledLabel("Faculty:"),
            createStyledLabel("Project Title:"),
            createStyledLabel("Contact Number:"),
            createStyledLabel("Email Address:")
        };
        
        txtRegId = createStyledTextField();
        txtName = createStyledTextField();
        txtFaculty = createStyledTextField();
        txtProjectTitle = createStyledTextField();
        txtContact = createStyledTextField();
        txtEmail = createStyledTextField();
        
        // Add labels and fields to form
        for (int i = 0; i < labels.length; i++) {
            formPanel.add(labels[i]);
            JTextField field = i == 0 ? txtRegId :
                             i == 1 ? txtName :
                             i == 2 ? txtFaculty :
                             i == 3 ? txtProjectTitle :
                             i == 4 ? txtContact :
                             txtEmail;
            formPanel.add(field);
        }
        
        // Style image selection components
        formPanel.add(createStyledLabel("Project Image:"));
        JPanel imagePanel = new JPanel(new BorderLayout(5, 0));
        imagePanel.setBackground(Color.WHITE);
        
        txtImagePath = createStyledTextField();
        txtImagePath.setEditable(false);
        btnBrowse = createStyledButton("Browse", new Color(52, 152, 219));
        
        imagePanel.add(txtImagePath, BorderLayout.CENTER);
        imagePanel.add(btnBrowse, BorderLayout.EAST);
        formPanel.add(imagePanel);
        
        // Image preview panel with title
        JPanel imagePreviewPanel = new JPanel(new BorderLayout(5, 10));
        imagePreviewPanel.setBackground(Color.WHITE);
        imagePreviewPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(0, 10, 0, 0),
            BorderFactory.createLineBorder(new Color(51, 153, 255), 1, true)
        ));

        JLabel previewLabel = new JLabel("Image Preview");
        previewLabel.setFont(new Font("Arial", Font.BOLD, 14));
        previewLabel.setForeground(new Color(51, 153, 255));
        previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        previewLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        lblImage = new JLabel("No Image Selected", SwingConstants.CENTER);
        lblImage.setPreferredSize(new Dimension(250, 250));
        lblImage.setFont(new Font("Arial", Font.ITALIC, 12));
        lblImage.setForeground(new Color(150, 150, 150));
        lblImage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        imagePreviewPanel.add(previewLabel, BorderLayout.NORTH);
        imagePreviewPanel.add(lblImage, BorderLayout.CENTER);
        
        // Button Panel with modern styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnRegister = createStyledButton("Register", new Color(46, 204, 113));
        btnSearch = createStyledButton("Search", new Color(52, 152, 219));
        btnUpdate = createStyledButton("Update", new Color(155, 89, 182));
        btnDelete = createStyledButton("Delete", new Color(231, 76, 60));
        btnViewAll = createStyledButton("View All", new Color(52, 152, 219));
        btnClear = createStyledButton("Clear", new Color(149, 165, 166));
        btnExit = createStyledButton("Exit", new Color(149, 165, 166));
        
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnViewAll);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExit);
        
        // Copyright Panel
        JPanel copyrightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        copyrightPanel.setBackground(new Color(240, 240, 240));
        JLabel copyrightLabel = new JLabel("© 2025 System Designed by Group E");
        copyrightLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        copyrightLabel.setForeground(new Color(100, 100, 100));
        copyrightPanel.add(copyrightLabel);

        // Add components to frame
        add(welcomePanel, BorderLayout.NORTH);
        
        // Center panel to hold form and image preview
        JPanel centerPanel = new JPanel(new BorderLayout(20, 0));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        centerPanel.add(formPanel, BorderLayout.CENTER);
        centerPanel.add(imagePreviewPanel, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);
        
        // South panel to hold buttons and copyright
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.CENTER);
        southPanel.add(copyrightPanel, BorderLayout.SOUTH);
        add(southPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        addActionListeners();
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setOpaque(true);
        button.setBorderPainted(false);
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(brightenColor(bgColor));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(new Color(70, 70, 70));
        return label;
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        
        Border defaultBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );
        
        Border focusBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(51, 153, 255)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );
        
        field.setBorder(defaultBorder);
        
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(focusBorder);
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(defaultBorder);
            }
        });
        
        return field;
    }
    
    private void addActionListeners() {
        btnRegister.addActionListener(e -> registerParticipant());
        btnSearch.addActionListener(e -> searchParticipant());
        btnUpdate.addActionListener(e -> updateParticipant());
        btnDelete.addActionListener(e -> deleteParticipant());
        btnViewAll.addActionListener(e -> openParticipantList());
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
    
    private Color brightenColor(Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        return Color.getHSBColor(hsb[0], Math.max(0f, hsb[1] - 0.1f), Math.min(1f, hsb[2] + 0.1f));
    }

    private void displayImage(String path) {
        if (path != null && !path.isEmpty()) {
            ImageIcon imageIcon = new ImageIcon(path);
            Image image = imageIcon.getImage();
            if (image != null) {
                // Calculate scaled dimensions while maintaining aspect ratio
                int originalWidth = image.getWidth(null);
                int originalHeight = image.getHeight(null);
                if (originalWidth > 0 && originalHeight > 0) {
                    int targetSize = 250;
                    double scale = Math.min((double) targetSize / originalWidth, (double) targetSize / originalHeight);
                    int scaledWidth = (int) (originalWidth * scale);
                    int scaledHeight = (int) (originalHeight * scale);
                    
                    image = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
                    lblImage.setIcon(new ImageIcon(image));
                    lblImage.setText(""); // Clear the "No Image Selected" text
                }
            }
        } else {
            lblImage.setIcon(null);
            lblImage.setText("No Image Selected");
        }
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
                showSuccessMessage("Registration Successful!", 
                    "Welcome aboard! Your innovative project has been successfully registered.\n" +
                    "Get ready to showcase your brilliance at the VU Exhibition!");
                clearForm();
            }
        } catch (SQLException ex) {
            showErrorDialog("Registration Error", "Unable to register participant: " + ex.getMessage());
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
    
    private void showSuccessMessage(String title, String message) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(46, 204, 113));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel iconLabel = new JLabel("✓");
        iconLabel.setFont(new Font("Arial", Font.BOLD, 48));
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JTextArea messageArea = new JTextArea(message);
        messageArea.setFont(new Font("Arial", Font.PLAIN, 14));
        messageArea.setForeground(Color.WHITE);
        messageArea.setBackground(new Color(46, 204, 113));
        messageArea.setEditable(false);
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);
        messageArea.setOpaque(false);
        
        panel.add(iconLabel, BorderLayout.NORTH);
        panel.add(messageArea, BorderLayout.CENTER);
        
        JButton okButton = createStyledButton("OK", new Color(255, 255, 255));
        okButton.setForeground(new Color(46, 204, 113));
        okButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(46, 204, 113));
        buttonPanel.add(okButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(this,
            message,
            title,
            JOptionPane.ERROR_MESSAGE);
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
    
    private void openParticipantList() {
        ParticipantListViewer viewer = new ParticipantListViewer();
        viewer.setVisible(true);
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


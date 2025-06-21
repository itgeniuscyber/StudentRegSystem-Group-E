package com.genius.studentregsystem;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.border.Border;

public class ParticipantListViewer extends JFrame {
    private JTable participantsTable;
    private DefaultTableModel tableModel;
    private static final String DB_URL = "jdbc:ucanaccess://VUE_Exhibition.accdb";
    private JButton btnRefresh, btnClose, btnSearch, btnClear;
    private JTextField searchField;
    private JComboBox<String> searchColumn;
    private TableRowSorter<DefaultTableModel> sorter;
    
    public ParticipantListViewer() {
        setTitle("Registered Participants - VU Exhibition");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(51, 153, 255));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Registered Participants");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Innovation Exhibition Participants List");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(subtitleLabel);
        
        // Table Panel
        String[] columnNames = {"Registration ID", "Student Name", "Faculty", "Project Title", "Contact", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        
        participantsTable = new JTable(tableModel);
        participantsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        participantsTable.setRowHeight(30);
        participantsTable.setSelectionBackground(new Color(51, 153, 255, 50));
        participantsTable.setSelectionForeground(Color.BLACK);
        participantsTable.setShowGrid(true);
        participantsTable.setGridColor(new Color(230, 230, 230));
        participantsTable.setIntercellSpacing(new Dimension(10, 10));
        
        // Adjust column widths
        int[] columnWidths = {100, 150, 100, 250, 100, 200};
        for (int i = 0; i < columnWidths.length; i++) {
            participantsTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        
        // Custom header renderer
        JTableHeader header = participantsTable.getTableHeader();
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(new Color(51, 153, 255));
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(51, 153, 255)));
        ((DefaultTableCellRenderer)header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        
        // Center-align all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < participantsTable.getColumnCount(); i++) {
            participantsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JScrollPane scrollPane = new JScrollPane(participantsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnRefresh = createStyledButton("Refresh", new Color(52, 152, 219));
        btnClose = createStyledButton("Close", new Color(149, 165, 166));
        
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnClose);
        
        // Copyright Panel
        JPanel copyrightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        copyrightPanel.setBackground(new Color(240, 240, 240));
        JLabel copyrightLabel = new JLabel("© 2025 System Designed by Group E");
        copyrightLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        copyrightLabel.setForeground(new Color(100, 100, 100));
        copyrightPanel.add(copyrightLabel);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        searchColumn = new JComboBox<>(new String[]{"All Columns", "Registration ID", "Student Name", "Faculty", "Project Title", "Contact", "Email"});
        searchColumn.setBackground(Color.WHITE);
        searchColumn.setFont(new Font("Arial", Font.PLAIN, 12));
        
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        btnSearch = createStyledButton("Search", new Color(52, 152, 219));
        btnClear = createStyledButton("Clear Search", new Color(149, 165, 166));
        
        searchPanel.add(new JLabel("Search in: "));
        searchPanel.add(searchColumn);
        searchPanel.add(searchField);
        searchPanel.add(btnSearch);
        searchPanel.add(btnClear);

        // Setup table sorter
        sorter = new TableRowSorter<>(tableModel);
        participantsTable.setRowSorter(sorter);

        // Add search functionality
        btnSearch.addActionListener(e -> performSearch());
        btnClear.addActionListener(e -> clearSearch());
        searchField.addActionListener(e -> performSearch()); // Allow search on Enter key

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(searchPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Create main panel to hold all components
        JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.setBackground(Color.WHITE);
        
        // Add components to main panel
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // South panel to hold buttons and copyright
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.CENTER);
        southPanel.add(copyrightPanel, BorderLayout.SOUTH);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        
        // Add header and main panel to frame
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        
        // Add action listeners
        btnRefresh.addActionListener(e -> loadParticipants());
        btnClose.addActionListener(e -> dispose());
        
        // Initial load
        loadParticipants();
        
        // Set size and location
        setSize(1000, 600);
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
    
    private Color brightenColor(Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        return Color.getHSBColor(hsb[0], Math.max(0f, hsb[1] - 0.1f), Math.min(1f, hsb[2] + 0.1f));
    }
    
    private void performSearch() {
        String searchText = searchField.getText().toLowerCase().trim();
        String selectedColumn = (String) searchColumn.getSelectedItem();
        
        if (searchText.isEmpty()) {
            sorter.setRowFilter(null);
            return;
        }
        
        RowFilter<DefaultTableModel, Object> filter = new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                if (selectedColumn.equals("All Columns")) {
                    for (int i = 0; i < entry.getValueCount(); i++) {
                        if (entry.getStringValue(i).toLowerCase().contains(searchText)) {
                            return true;
                        }
                    }
                    return false;
                } else {
                    int columnIndex = searchColumn.getSelectedIndex() - 1; // -1 because "All Columns" is at index 0
                    return entry.getStringValue(columnIndex).toLowerCase().contains(searchText);
                }
            }
        };
        
        sorter.setRowFilter(filter);
    }
    
    private void clearSearch() {
        searchField.setText("");
        searchColumn.setSelectedIndex(0);
        sorter.setRowFilter(null);
    }
    
    private void loadParticipants() {
        tableModel.setRowCount(0); // Clear existing data
        
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT RegistrationID, StudentName, Faculty, ProjectTitle, ContactNumber, Email FROM Participants";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                while (rs.next()) {
                    Object[] row = {
                        rs.getString("RegistrationID"),
                        rs.getString("StudentName"),
                        rs.getString("Faculty"),
                        rs.getString("ProjectTitle"),
                        rs.getString("ContactNumber"),
                        rs.getString("Email")
                    };
                    tableModel.addRow(row);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Error loading participants: " + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
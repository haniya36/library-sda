package com.library.lib.gui;

import com.library.lib.model.Member;
import com.library.lib.model.LibraryUser;
import com.library.lib.service.MemberService;
import com.library.lib.dao.MemberDao;
import com.library.lib.dao.InMemoryMemberDaoImpl; // Import your InMemory DAO implementation explicitly

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MemberManagementForm extends JFrame {
    private JTextField memberIdField, nameField, contactInfoField, memberTypeField;
    private JButton registerButton, viewAllButton, updateButton, deleteButton, backButton;
    private JTextArea displayArea;
    private MemberService memberService;
    private LibraryUser loggedInUser; // To pass back to the dashboard

    // Constructor to receive the logged-in user
    public MemberManagementForm(LibraryUser user) {
        this.loggedInUser = user; // Store the user
        // Instantiate MemberService with your in-memory DAO
        this.memberService = new MemberService(new InMemoryMemberDaoImpl()); // Correctly instantiate the DAO

        setTitle("Member Management - Library System");
        setSize(700, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Only dispose this window

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                Color color1 = new Color(0, 128, 128, 180); // Using a slightly more transparent teal for background
                Color color2 = new Color(0, 150, 150, 180);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout(0, 20));
        setContentPane(backgroundPanel);

        JLabel titleLabel = new JLabel("Member Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel memberIdLabel = createStyledLabel("Member ID:");
        memberIdField = new JTextField(20);
        styleTextField(memberIdField);
        JLabel nameLabel = createStyledLabel("Name:");
        nameField = new JTextField(20);
        styleTextField(nameField);
        JLabel contactInfoLabel = createStyledLabel("Contact Info:");
        contactInfoField = new JTextField(20);
        styleTextField(contactInfoField);
        JLabel memberTypeLabel = createStyledLabel("Member Type:");
        memberTypeField = new JTextField(20); // e.g., "Student", "Faculty", "General"
        styleTextField(memberTypeField);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(memberIdLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(memberIdField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(contactInfoLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(contactInfoField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(memberTypeLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(memberTypeField, gbc);

        backgroundPanel.add(formPanel, BorderLayout.CENTER);

        JPanel crudButtonsPanel = new JPanel();
        crudButtonsPanel.setOpaque(false);
        crudButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        registerButton = createStyledButton("Register Member");
        viewAllButton = createStyledButton("View All Members");
        updateButton = createStyledButton("Update Member");
        deleteButton = createStyledButton("Delete Member");

        crudButtonsPanel.add(registerButton);
        crudButtonsPanel.add(viewAllButton);
        crudButtonsPanel.add(updateButton);
        crudButtonsPanel.add(deleteButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        backButton = createStyledButton("Back to Dashboard");
        JPanel backButtonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButtonWrapper.setOpaque(false);
        backButtonWrapper.add(backButton);

        displayArea = new JTextArea(10, 50);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        displayArea.setBackground(new Color(240, 255, 255)); // Light cyan background for text area
        displayArea.setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE), "Member List",
                0, 0, new Font("Times New Roman", Font.BOLD, 14), Color.WHITE));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); // Make viewport transparent

        bottomPanel.add(crudButtonsPanel, BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        bottomPanel.add(backButtonWrapper, BorderLayout.SOUTH);

        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Action Listeners
        registerButton.addActionListener(e -> registerMember());
        viewAllButton.addActionListener(e -> viewAllMembers());
        updateButton.addActionListener(e -> updateMember());
        deleteButton.addActionListener(e -> deleteMember());
        backButton.addActionListener(e -> {
            // Re-open the dashboard, passing the current logged-in user
            new LibraryDashboard(this.loggedInUser).setVisible(true);
            this.dispose(); // Close the current MemberManagementForm
        });

        // Initialize with all members displayed
        viewAllMembers();
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Times New Roman", Font.BOLD, 16));
        return label;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(0, 128, 128));
        button.setFont(new Font("Times New Roman", Font.BOLD, 16));
        button.setFocusPainted(false);
        // Add hover effect
        Color originalBg = button.getBackground();
        Color hoverBg = new Color(220, 240, 240);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBg);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalBg);
            }
        });
        return button;
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createLineBorder(new Color(0, 170, 170)));
        field.setBackground(new Color(60, 60, 60)); // Dark background for input
        field.setForeground(Color.WHITE); // White text
        field.setCaretColor(Color.WHITE); // White caret
    }

    private void registerMember() {
        try {
            String memberId = memberIdField.getText().trim();
            String name = nameField.getText().trim();
            String contactInfo = contactInfoField.getText().trim();
            String memberType = memberTypeField.getText().trim();

            if (memberId.isEmpty() || name.isEmpty() || contactInfo.isEmpty() || memberType.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required to register a member.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Member newMember = new Member(memberId, name, contactInfo, memberType);
            boolean success = memberService.registerMember(newMember);

            if (success) {
                JOptionPane.showMessageDialog(this, "Member registered successfully!");
                clearFields();
                viewAllMembers(); // Refresh list
            } else {
                JOptionPane.showMessageDialog(this, "Failed to register member. Member ID might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void viewAllMembers() {
        displayArea.setText("");
        java.util.List<Member> members = memberService.getAllMembers();
        if (members.isEmpty()) {
            displayArea.setText("No members registered yet.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-15s %-30s %-25s %-15s%n", "MEMBER ID", "NAME", "CONTACT INFO", "MEMBER TYPE"));
        sb.append("------------------------------------------------------------------------------------------\n");
        for (Member member : members) {
            sb.append(String.format("%-15s %-30s %-25s %-15s%n",
                                     member.getMemberId(), member.getName(), member.getContactInfo(), member.getMemberType()));
        }
        displayArea.setText(sb.toString());
    }

    private void updateMember() {
        try {
            String memberId = memberIdField.getText().trim();
            String name = nameField.getText().trim();
            String contactInfo = contactInfoField.getText().trim();
            String memberType = memberTypeField.getText().trim();

            if (memberId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the Member ID of the member to update.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Member existingMember = memberService.getMemberById(memberId);
            if (existingMember == null) {
                JOptionPane.showMessageDialog(this, "Member with ID " + memberId + " not found.", "Not Found", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Update fields only if they are not empty in the form
            if (!name.isEmpty()) {
                existingMember.setName(name);
            }
            if (!contactInfo.isEmpty()) {
                existingMember.setContactInfo(contactInfo);
            }
            if (!memberType.isEmpty()) {
                existingMember.setMemberType(memberType);
            }

            boolean success = memberService.updateMember(existingMember);

            if (success) {
                JOptionPane.showMessageDialog(this, "Member updated successfully!");
                clearFields();
                viewAllMembers(); // Refresh list
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update member.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void deleteMember() {
        String memberId = memberIdField.getText().trim();
        if (memberId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Member ID of the member to delete.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete member with ID: " + memberId + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = memberService.removeMember(memberId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Member deleted successfully!");
                    clearFields();
                    viewAllMembers(); // Refresh list
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete member. Member not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void clearFields() {
        memberIdField.setText("");
        nameField.setText("");
        contactInfoField.setText("");
        memberTypeField.setText("");
    }

    public static void main(String[] args) {
        // For testing MemberManagementForm directly without going through login
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MemberManagementForm(new LibraryUser("temp", "pass", "Temp User", "admin")).setVisible(true);
        });
    }
}
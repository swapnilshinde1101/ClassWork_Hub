package com.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.DBConnection.DBCon;


public class Student {

    private static JPanel contentPanel; // Panel to switch content
    private static JPanel classListPanel; // Panel where new classes will be added dynamically

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Student();
            }
        });
    }

    public Student() {
        // Create the main frame
        JFrame frame = new JFrame("Classwork Hub");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // Center the window

        // Create the header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.GRAY);
        headerPanel.setPreferredSize(new Dimension(800, 100)); // Adjusted to match frame width
        JLabel headerLabel = new JLabel("Classwork Hub", SwingConstants.CENTER);
        headerLabel.setVerticalAlignment(SwingConstants.TOP);
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headerPanel.add(headerLabel);
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);

        // Create the content panel with BorderLayout
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BorderLayout());
        frame.getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Initialize the class list panel (where new classes will be added)
        classListPanel = new JPanel();
        classListPanel.setLayout(new BoxLayout(classListPanel, BoxLayout.Y_AXIS));  // Stack classes vertically
        JScrollPane classScrollPane = new JScrollPane(classListPanel); // Make it scrollable
        contentPanel.add(classScrollPane, BorderLayout.CENTER);  // Add to the center of the content panel

        // Create the side panel with "Join Class" and "View Class" buttons
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.LIGHT_GRAY);
        sidePanel.setPreferredSize(new Dimension(150, 600)); // Fixed width
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));  // Vertical stacking

        JButton btnJoinClass = new JButton("Join Class");
        btnJoinClass.setBackground(Color.GRAY);
        btnJoinClass.setForeground(Color.WHITE);
        sidePanel.add(btnJoinClass);
        
        JButton btnViewClass = new JButton("View Class");
        btnViewClass.setBackground(Color.GRAY);
        btnViewClass.setForeground(Color.WHITE);
        sidePanel.add(btnViewClass);

        // Add ActionListener for the "Join Class" button
        btnJoinClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinClassForm();
            }
        });

        // Add ActionListener for the "View Class" button
        btnViewClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showClasses();
            }
        });

        // Add side panel to the frame's content panel
        contentPanel.add(sidePanel, BorderLayout.WEST);  // Fixed side panel on the left

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.LIGHT_GRAY);
        frame.getContentPane().add(menuBar, BorderLayout.NORTH);

        // Create "Home" button in the menu bar
        JMenu homeMenu = new JMenu("Home");
        homeMenu.addMenuListener(new javax.swing.event.MenuListener() {
            @Override
            public void menuSelected(javax.swing.event.MenuEvent e) {
                showHomePage();  // Show the home page when Home is clicked
            }
            @Override public void menuDeselected(javax.swing.event.MenuEvent e) {}
            @Override public void menuCanceled(javax.swing.event.MenuEvent e) {}
        });
        menuBar.add(homeMenu);

        // Create "About" button
        JMenu aboutMenu = new JMenu("About");
        aboutMenu.addMenuListener(new javax.swing.event.MenuListener() {
            @Override
            public void menuSelected(javax.swing.event.MenuEvent e) {
                JOptionPane.showMessageDialog(frame, "About us information for students.");
            }
            @Override public void menuDeselected(javax.swing.event.MenuEvent e) {}
            @Override public void menuCanceled(javax.swing.event.MenuEvent e) {}
        });
        menuBar.add(aboutMenu);

        // Load and resize user icon
        ImageIcon userIcon = loadAndResizeIcon("/home/mima9/Desktop/Assignment Writer/Assingnment_writer/src/com/image/login.png", 30, 30);

        // Create user menu and add it to the menu bar
        JMenu userMenu = new JMenu();
        userMenu.setIcon(userIcon); // Set resized icon
        userMenu.setText("User");

        JMenuItem profileMenuItem = new JMenuItem("Profile");
        profileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Profile Page");
            }
        });

        JMenuItem logoutMenuItem = new JMenuItem("Logout");
        logoutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Optionally, you can show a confirmation dialog here
                int confirmed = JOptionPane.showConfirmDialog(frame, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    // Dispose of the current frame
                    frame.dispose();
                    // Create a new instance of HomeMain
                    new HomeMain(); // Assuming HomeMain is your main application class
                }
            }
        });

        userMenu.add(profileMenuItem);
        userMenu.add(logoutMenuItem);
        menuBar.add(Box.createHorizontalGlue()); // Push userMenu to the right
        menuBar.add(userMenu);

        // Add menu bar to the frame
        frame.getContentPane().add(menuBar, BorderLayout.NORTH);

        // Add content panel to the frame
        frame.getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Show the frame
        frame.setVisible(true);
    }

    // Method to show the Join Class form
    private static void joinClassForm() {
        JDialog classForm = new JDialog();
        classForm.setTitle("Join Class");
        classForm.setSize(400, 300);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2)); // Create a grid for labels and text fields

        JLabel lblClassName = new JLabel("Class Name:");
        JTextField txtClassName = new JTextField();
        JLabel lblBatch = new JLabel("Batch:");
        JTextField txtBatch = new JTextField();
        JLabel lblClassCode = new JLabel("Class Code:");
        JTextField txtClassCode = new JTextField();

        JButton doneButton = new JButton("Join");

        // Add components to the form panel
        formPanel.add(lblClassName);
        formPanel.add(txtClassName);
        formPanel.add(lblBatch);
        formPanel.add(txtBatch);
        formPanel.add(lblClassCode);
        formPanel.add(txtClassCode);
        formPanel.add(new JLabel()); // Empty cell for layout purposes
        formPanel.add(doneButton);

        classForm.add(formPanel);

        // ActionListener for Join button
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String className = txtClassName.getText();
                String batch = txtBatch.getText();
                String classCode = txtClassCode.getText();

                try {
                    Connection cn = DBCon.getConnection();
                    Statement st = cn.createStatement();

                    // Query to check if the class exists and the class code matches
                    String query = "SELECT * FROM createclass WHERE class_name = '" + className + "' AND batch = '" + batch + "' AND code = '" + classCode + "'";
                    ResultSet rs = st.executeQuery(query);

                    if (rs.next()) {
                        // Assuming student ID is obtained from a login mechanism or session
                        String studentId = "8412"; // Replace with actual student ID

                        // Insert record into joinclass table
                        String insertQuery = "INSERT INTO joinclass ( class_name, batch, code,student_id) VALUES ('" + className + "', '" + batch + "', '" + classCode + "',6073)";
                        st.executeUpdate(insertQuery);

                        JOptionPane.showMessageDialog(null, "Successfully joined the class!");
                        addClassToClassListPanel(className, batch, classCode); // Add new class to the list
                        classForm.dispose(); // Close the form
                    } else {
                        JOptionPane.showMessageDialog(null, "Class not found or class code does not match. Please check the details.");
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            }
        });
        classForm.setModal(true); // Blocks other windows until form is closed
        classForm.setVisible(true);
    }  
    


    // Method to add class details to class list panel dynamically
    private static void addClassToClassListPanel(String className, String batch, String code) {
        // Create a class panel with a more modern look
        JPanel classPanel = new JPanel();
        classPanel.setLayout(new BorderLayout(10, 10));  // Add spacing between elements
        classPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));  // Add padding inside the panel

        // Create labels for class name and batch with better styling
        JLabel lblClassName = new JLabel("Class: " + className);
        lblClassName.setFont(new Font("Arial", Font.BOLD, 16));  // Bold font for class name
        lblClassName.setForeground(Color.DARK_GRAY);  // Darker color for class name

        JLabel lblBatch = new JLabel("Batch: " + batch);
        lblBatch.setFont(new Font("Arial", Font.PLAIN, 14));  // Slightly smaller font for batch
        lblBatch.setForeground(Color.GRAY);  // Lighter color for batch label

        // Create the "View Assignments" button with better styling
        JButton viewAssignmentsButton = new JButton("View Assignments");
        viewAssignmentsButton.setBackground(Color.DARK_GRAY);
        viewAssignmentsButton.setForeground(Color.WHITE);
        viewAssignmentsButton.setFont(new Font("Arial", Font.BOLD, 12));
        viewAssignmentsButton.setFocusPainted(false);  // Remove focus border around the button

        // Add a hover effect to the button (optional)
        viewAssignmentsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                viewAssignmentsButton.setBackground(Color.GRAY);  // Change color on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                viewAssignmentsButton.setBackground(Color.DARK_GRAY);  // Revert color
            }
        });

        // Add action listener to view assignments
        viewAssignmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAssignments(className, batch, code);
            }
        });

        // Use a BoxLayout for the content to control vertical spacing
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);  // Set a clean background for content

        contentPanel.add(lblClassName);
        contentPanel.add(Box.createVerticalStrut(5));  // Add space between labels
        contentPanel.add(lblBatch);

        // Add components to the class panel
        classPanel.add(contentPanel, BorderLayout.CENTER);
        classPanel.add(viewAssignmentsButton, BorderLayout.SOUTH);

        // Add the class panel to the class list and refresh
        classListPanel.add(Box.createVerticalStrut(10));  // Add spacing between class panels
        classListPanel.add(classPanel);
        classListPanel.revalidate();
    }


    // Method to show the home page
    private static void showHomePage() {
        // This can be expanded to show specific content for the home page
        JOptionPane.showMessageDialog(null, "Welcome to the Home Page!");
    }

    // Method to show available classes
    private static void showClasses() {
        try {
            Connection cn = DBCon.getConnection();
            Statement st = cn.createStatement();
            // Assuming you have a way to get the current student ID
            String studentId = "actual_student_id";  // Replace with actual student ID

            // Query to get classes the student has joined
            String query = "SELECT class_name, batch, code FROM joinclass WHERE student_id = '" + studentId + "'";
            ResultSet rs = st.executeQuery(query);

            // Clear previous class list
            classListPanel.removeAll();

            while (rs.next()) {
                String className = rs.getString("class_name");
                String batch = rs.getString("batch");
                String code = rs.getString("code");
                // Add class to the list panel
                addClassToClassListPanel(className, batch, code);
            }
            classListPanel.revalidate(); // Refresh the panel to show new components

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    // Method to load and resize an icon
    private static ImageIcon loadAndResizeIcon(String path, int width, int height) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to view assignments (stub implementation)
 // Method to view assignments and allow submission
    private static void viewAssignments(String className, String batch, String code) {
        JDialog assignmentDialog = new JDialog();
        assignmentDialog.setTitle("View Assignments");
        assignmentDialog.setSize(600, 400);

        JPanel assignmentPanel = new JPanel();
        assignmentPanel.setLayout(new BoxLayout(assignmentPanel, BoxLayout.Y_AXIS));

        try {
            Connection cn = DBCon.getConnection();
            Statement st = cn.createStatement();

            // Query to get assignments for the class
            String query = "SELECT * FROM assignment";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String assignmentName = rs.getString("assignment_name");
                String date = rs.getString("date");
                String time = rs.getString("time");
                int assignmentId = rs.getInt("assignment_id");

                JPanel singleAssignmentPanel = new JPanel(new BorderLayout());

                JLabel lblAssignmentDetails = new JLabel("Assignment: " + assignmentName + " | Date: " + date + " | Time: " + time);
                JButton WriteAssignmentButton = new JButton("Write Assignment");

                // Add action listener to view assignment details
                WriteAssignmentButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	WriteAssignmentButton(assignmentId);
                    }
                });

                singleAssignmentPanel.add(lblAssignmentDetails, BorderLayout.CENTER);
                singleAssignmentPanel.add(WriteAssignmentButton, BorderLayout.EAST);
                assignmentPanel.add(singleAssignmentPanel);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }

        JScrollPane scrollPane = new JScrollPane(assignmentPanel);
        assignmentDialog.add(scrollPane);

        assignmentDialog.setModal(true);
        assignmentDialog.setVisible(true);
    }

    
    public class CurrentUser {
        private static String studentId;

        public static String getStudentId() {
            return studentId;
        }

        public static void setStudentId(String studentId) {
            CurrentUser.studentId = studentId;
        }
    }
    // Method to show assignment details
    private static void WriteAssignmentButton(int assignmentId) {
        // Create dialog for writing assignment
        JDialog detailDialog = new JDialog();
        detailDialog.setTitle("Write Assignment");
        detailDialog.setSize(700, 500);
        detailDialog.setLayout(new BorderLayout());

        // Panel for displaying assignment details
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new GridLayout(5, 3)); // To display the assignment details

        JLabel lblAssignmentName = new JLabel();
        lblAssignmentName.setHorizontalAlignment(SwingConstants.CENTER); // Center the assignment name

        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            cn = DBCon.getConnection(); // Assuming DBCon is your database connection class
            st = cn.createStatement();

            // Query to get assignment details based on assignment ID
            String query = "SELECT * FROM assignment WHERE assignment_id = " + assignmentId;
            rs = st.executeQuery(query);

            if (rs.next()) {
                String assignmentName = rs.getString("assignment_name");
                String date = rs.getString("date");
                String time = rs.getString("time");
                String description = rs.getString("description");

                lblAssignmentName.setText("Assignment: " + assignmentName);
                detailPanel.add(new JLabel("Date: " + date));
                detailPanel.add(new JLabel("Time: " + time));
                detailPanel.add(new JLabel("description: " + description));
            } else {
                JOptionPane.showMessageDialog(null, "Assignment not found.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        } finally {
            // Ensure resources are closed to prevent leaks
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (cn != null) cn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Adding assignment name to the top of the dialog
        detailDialog.add(lblAssignmentName, BorderLayout.NORTH);

        // Adding the details panel to the center of the dialog
        detailDialog.add(detailPanel, BorderLayout.CENTER);

        // Panel for writing and submitting the assignment
        JPanel submitPanel = new JPanel(new BorderLayout());
        JTextArea txtAssignmentText = new JTextArea(15, 20);
        txtAssignmentText.setBorder(BorderFactory.createTitledBorder("Write Assignment"));
        submitPanel.add(new JScrollPane(txtAssignmentText), BorderLayout.CENTER);

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitPanel.add(submitButton, BorderLayout.SOUTH);

        // Adding the submission panel to the bottom of the dialog
        detailDialog.add(submitPanel, BorderLayout.SOUTH);

        // ActionListener for the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String submittedText = txtAssignmentText.getText().trim();

                // Check if the submission text is empty
                if (submittedText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Assignment text cannot be empty!");
                    return;
                }

                Connection cn = null;
                PreparedStatement pst = null; // Use PreparedStatement for security

                try {
                    // Get connection from your DBCon class
                    cn = DBCon.getConnection();

                    // Assuming student_id is fetched from session or login system
                    String studentId ="8412" ;
                    // Replace with actual student ID logic
                    
                    // Prepare the SQL Query
                    String query = "INSERT INTO assignment_completion (assignment_id, student_id, submission_text, submission_date, submission_status) " +
                                   "VALUES (?, ?, ?, NOW(), 'Submitted')";
                    pst = cn.prepareStatement(query);
                    pst.setInt(1, assignmentId);
                    System.out.println(assignmentId);
                    pst.setString(2, studentId);
                    System.out.println(studentId);
                    pst.setString(3, submittedText);
                    System.out.println(submittedText);

                    // Execute the update
                    int rowsAffected = pst.executeUpdate();

                    if (rowsAffected > 0) {
                        // Show success message
                        JOptionPane.showMessageDialog(null, "Assignment submitted successfully!");
                        detailDialog.dispose(); // Close the dialog after successful submission
                    } else {
                        // In case no rows were affected, show error
                        JOptionPane.showMessageDialog(null, "Failed to submit assignment. Please try again.");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                } finally {
                    // Close database resources to prevent leaks
                    try {
                        if (pst != null) pst.close();
                        if (cn != null) cn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        detailDialog.setModal(true);
        detailDialog.setVisible(true);

    }


}

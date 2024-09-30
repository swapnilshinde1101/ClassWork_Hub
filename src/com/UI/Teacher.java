package com.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

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


public class Teacher {

    private static JPanel contentPanel; // Panel to switch content
    private static JPanel classListPanel; // Panel where new classes will be added dynamically
    private static HashMap<String, ArrayList<String>> classAssignments = new HashMap<>(); // Stores assignments for each class
    private static HashMap<String, ArrayList<String>> classStudents = new HashMap<>(); // Stores students for each class
    private static HashMap<String, HashMap<String, String>> assignmentGrades = new HashMap<>(); // Stores grades for each assignment

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Teacher();
            }
        });
    }


    public Teacher() {
        // Create the main frame
        JFrame frame = new JFrame("Classwork Hub ");
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

        // Create the side panel with a fixed "Create Class" button
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.LIGHT_GRAY);
        sidePanel.setPreferredSize(new Dimension(150, 600)); // Fixed width
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));  // Vertical stacking

        JButton btnClass = new JButton("Create Class");
        btnClass.setBackground(Color.GRAY);
        btnClass.setForeground(Color.WHITE);
        sidePanel.add(btnClass);

        JButton btnClassView = new JButton("View Class");
        btnClassView.setBackground(Color.GRAY);
        btnClassView.setForeground(Color.WHITE);
        sidePanel.add(btnClassView);

        // Add ActionListener for the "Create Class" button
        btnClass.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                showClassForm();
            }
        });

       
        // Add ActionListener for the "View Class" button
        btnClassView.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
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

            @Override
            public void menuDeselected(javax.swing.event.MenuEvent e) {}
            @Override
            public void menuCanceled(javax.swing.event.MenuEvent e) {}
        });
        menuBar.add(homeMenu);

        // Create "About" button
        JMenu aboutMenu = new JMenu("About");
        aboutMenu.addMenuListener(new javax.swing.event.MenuListener() {
            @Override
            public void menuSelected(javax.swing.event.MenuEvent e) {
                JOptionPane.showMessageDialog(frame, "Teacher Instructions:\n1. Create classes using 'Create Class' button.\n2. Assign and view assignments.\n3. Manage student enrollments.");
            }

            @Override
            public void menuDeselected(javax.swing.event.MenuEvent e) {}
            @Override
            public void menuCanceled(javax.swing.event.MenuEvent e) {}
        });
        menuBar.add(aboutMenu);

        // Load and resize user icon
        ImageIcon userIcon = loadAndResizeIcon("/home/mima9/Desktop/Assignment Writer/Assingnment_writer/src/com/image/login.png", 30, 30);

        // Create user menu and add it to the menu bar
        JMenu userMenu = new JMenu();
        userMenu.setIcon(userIcon); // Set resized icon
        userMenu.setText("User");

        JMenuItem profileMenuItem = new JMenuItem("Profile");
        profileMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
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


    // Method to show class form using CreateClassPanel
    private static void showClassForm() {
        JDialog classForm = new JDialog();
        classForm.setTitle("Create Class");
        classForm.setSize(400, 300);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2)); // Create a grid for labels and text fields

        JLabel lblClassName = new JLabel("Class Name:");
        JTextField txtClassName = new JTextField();
        JLabel lblBatch = new JLabel("Batch:");
        JTextField txtBatch = new JTextField();
        JLabel lblClassCode = new JLabel("Class Code:");
        JTextField txtClassCode = new JTextField();

        JButton doneButton = new JButton("Done");

        // Add components to the form panel
        formPanel.add(lblClassName);
        formPanel.add(txtClassName);
        formPanel.add(lblBatch);
        formPanel.add(txtBatch);
        formPanel.add(lblClassCode);
        formPanel.add(txtClassCode);
        formPanel.add(new JLabel()); 
        formPanel.add(doneButton);

        classForm.add(formPanel);

        // Add ActionListener for Done button
        doneButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String className = txtClassName.getText();
                String batch = txtBatch.getText();
                String classCode = txtClassCode.getText();

                try {
                    Connection cn = DBCon.getConnection(); 
                    Statement st = cn.createStatement();
                    
                    // Prepare the insert query
                    String query = "INSERT INTO createclass (class_id, class_name, batch, code) " +
                                   "VALUES (" + new Random().nextInt(10000) + ", '" + className + "', '" + batch + "', '" + classCode + "')";

                    // Execute the insert query
                    int result = st.executeUpdate(query);
                    
                    // Show success message and close the form
                    if(result > 0) {
                        JOptionPane.showMessageDialog(null, "Class created successfully!");
                        addClassToClassListPanel(className, batch, classCode); // Add new class to the list
                        classForm.dispose();  // Close the class form after successful creation
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to create class.");
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
        // Create a new panel for the class
        JPanel classPanel = new JPanel();
        classPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Align left with spacing

        classPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        classPanel.setBackground(new Color(230, 230, 230));

        // Add class name label
        JLabel classNameLabel = new JLabel("Class: " + className);
        classNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        classPanel.add(classNameLabel);

        // Add batch label
        JLabel batchLabel = new JLabel("Batch: " + batch);
        batchLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        classPanel.add(batchLabel);

        // Add class code label
        JLabel codeLabel = new JLabel("Code: " + code);
        codeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        classPanel.add(codeLabel);

        // Add "Open Class" button
        JButton openClassButton = new JButton("Open Class");
        openClassButton.setFont(new Font("Arial", Font.PLAIN, 12));
        openClassButton.setBackground(Color.DARK_GRAY);
        openClassButton.setForeground(Color.WHITE);
        openClassButton.setFocusPainted(false);
        openClassButton.addActionListener(new OpenClassButtonListener(className)); // Use custom ActionListener
        classPanel.add(openClassButton);

        // Add the class panel to the main class list panel
        classListPanel.add(classPanel);
        classListPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Adds spacing between panels
        classListPanel.revalidate(); // Refresh the panel to show newly added class
        classListPanel.repaint(); // Repaint to ensure the visual updates
    }

    // Create a custom ActionListener class
    private static class OpenClassButtonListener implements ActionListener {
        private String className;

        public OpenClassButtonListener(String className) {
            this.className = className;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            showClassOptions(className); // Call showClassOptions when button is clicked
        }
    }




 // Method to show class options (view assignments, view students)
    private static void showClassOptions(String className) {
        JDialog classOptionsDialog = new JDialog();
        classOptionsDialog.setTitle("Class Options - " + className);
        classOptionsDialog.setSize(400, 300);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(3, 1)); // Vertical stacking

        JButton assignAssignmentButton = new JButton("Assign Assignment");
        JButton viewAssignmentsButton = new JButton("View Assignments");
        JButton viewStudentsButton = new JButton("View Students");

        // Add ActionListeners to buttons
        assignAssignmentButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                assignAssignmentForm(className);
            }
        });

        viewAssignmentsButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	showStudentListForAssignments(className);
            }
        });

        viewStudentsButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                showStudentsForClass(className);
            }
        });

        // Add buttons to the panel
        optionsPanel.add(assignAssignmentButton);
        optionsPanel.add(viewAssignmentsButton);
        optionsPanel.add(viewStudentsButton);

        classOptionsDialog.add(optionsPanel);
        classOptionsDialog.setModal(true);
        classOptionsDialog.setVisible(true);
    }


    private static void showStudentListForAssignments(String className) {
        // Create a new dialog for showing the student list
        JDialog studentListDialog = new JDialog();
        studentListDialog.setTitle("Student List for Assignments - " + className);
        studentListDialog.setSize(400, 300);
        studentListDialog.setLocationRelativeTo(null); // Center the dialog

        // Create the main panel with vertical alignment for student buttons
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE); // Set background color for the panel

        // Fetch students from the database who have submitted assignments for the class
        ArrayList<StudentAssignment> studentAssignments = fetchStudentsWithAssignments(className);

        // Loop through the list of students
        for (StudentAssignment sa : studentAssignments) {
            // Create a panel for each student row with custom styling
            JPanel studentPanel = new JPanel(new BorderLayout());
            studentPanel.setBackground(new Color(240, 248, 255)); // Light blue background
            studentPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY), // Bottom gray line
                    BorderFactory.createEmptyBorder(10, 10, 10, 10))); // Padding

            // Create a label for the student's name
            JLabel studentNameLabel = new JLabel(sa.getStudentName());
            studentNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            studentNameLabel.setForeground(new Color(30, 144, 255)); // Dodger Blue font color

            // Create a "View Assignment" button
            JButton viewAssignmentButton = new JButton("View Assignment");
            viewAssignmentButton.setBackground(new Color(30, 144, 255)); // Dodger Blue background
            viewAssignmentButton.setForeground(Color.WHITE); // White text color
            viewAssignmentButton.setFocusPainted(false); // Remove button focus border
            viewAssignmentButton.setFont(new Font("Arial", Font.PLAIN, 12));

            // ActionListener to show assignment details when the button is clicked
            viewAssignmentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showAssignmentDetails(sa);
                }
            });

            // Add the student name and button to the student panel
            studentPanel.add(studentNameLabel, BorderLayout.WEST); // Name on the left
            studentPanel.add(viewAssignmentButton, BorderLayout.EAST); // Button on the right

            // Add the student panel to the main panel
            mainPanel.add(studentPanel);
        }

        // Create a scroll pane to allow scrolling through the list
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add the scroll pane to the dialog and make it modal
        studentListDialog.add(scrollPane);
        studentListDialog.setModal(true);
        studentListDialog.setVisible(true);
    }

    private static ArrayList<StudentAssignment> fetchStudentsWithAssignments(String className) {
        ArrayList<StudentAssignment> studentAssignments = new ArrayList<>();

        // Database query to fetch students with submitted assignments
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Assignment_writer", "root", "root");
             PreparedStatement stmt = conn.prepareStatement("SELECT s.student_id, s.name, ac.assignment_id " +
                     "FROM assignment_completion ac " +
                     "JOIN student s ON ac.student_id = s.student_id " +
                     "JOIN assignment a ON ac.assignment_id = a.assignment_id " +
                     "")) {
            //stmt.setString(1, className);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String studentName = rs.getString("name");
                int assignmentId = rs.getInt("assignment_id");
                studentAssignments.add(new StudentAssignment(studentId, studentName, assignmentId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentAssignments;
    }

    
    
    private static void showAssignmentDetails(StudentAssignment studentAssignment) {
        // Create a dialog to show assignment details
        JDialog assignmentDialog = new JDialog();
        assignmentDialog.setTitle("Assignment Details for " + studentAssignment.getStudentName());
        assignmentDialog.setSize(400, 300);

        // Fetch assignment details from the database
        String assignmentDetails = fetchAssignmentDetails(studentAssignment.getAssignmentId());

        JTextArea assignmentTextArea = new JTextArea(assignmentDetails);
        assignmentTextArea.setEditable(false); // Make it read-only

        assignmentDialog.add(new JScrollPane(assignmentTextArea));
        assignmentDialog.setModal(true);
        assignmentDialog.setVisible(true);
    }


    private static String fetchAssignmentDetails(int assignmentId) {
        StringBuilder details = new StringBuilder();

        // Database query to get assignment details
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Assignment_writer", "root", "root");
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT student_id,submission_text FROM assignment_completion WHERE assignment_id = ?")) {
            stmt.setInt(1, assignmentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                details.append("Student Id: ").append(rs.getString("student_id")).append("\n");
                details.append("Submission: ").append(rs.getString("submission_text")).append("\n");
            } else {
                details.append("No assignment found for this student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details.toString();
    }


    // Helper class to hold student assignment data
    static class StudentAssignment {
        private final int studentId;
        private final String studentName;
        private final int assignmentId;

        public StudentAssignment(int studentId, String studentName, int assignmentId) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.assignmentId = assignmentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public int getAssignmentId() {
            return assignmentId;
        }
    }



    // Method to show students for a class
    private static void showStudentsForClass(String className) {
        JDialog studentsDialog = new JDialog();
        studentsDialog.setTitle("Students - " + className);
        studentsDialog.setSize(400, 300);

        JPanel studentsPanel = new JPanel();
        studentsPanel.setLayout(new BoxLayout(studentsPanel, BoxLayout.Y_AXIS));

        // Retrieve students for the class
        ArrayList<String> students = classStudents.getOrDefault(className, new ArrayList<>());

        for (String student : students) {
            studentsPanel.add(new JLabel(student));
        }

        JScrollPane studentsScrollPane = new JScrollPane(studentsPanel);
        studentsDialog.add(studentsScrollPane);

        studentsDialog.setModal(true);
        studentsDialog.setVisible(true);
    }

 // Method to show the assignment form
    private static void assignAssignmentForm(String className) {
        JDialog assignmentForm = new JDialog();
        assignmentForm.setTitle("Assign Assignment");
        assignmentForm.setSize(400, 300);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2));

        JLabel lblAssignmentName = new JLabel("Assignment Name:");
        JTextField txtAssignmentName = new JTextField();
        JLabel lblDueDate = new JLabel("Due Date (YYYY-MM-DD):");
        JTextField txtDueDate = new JTextField();
        JLabel lblTime = new JLabel("Time (HH:MM):");
        JTextField txtTime = new JTextField();
        JLabel lblDescription = new JLabel("Description:");
        JTextArea txtDescription = new JTextArea();
        JButton assignButton = new JButton("Assign");

        // Add components to the form panel
        formPanel.add(lblAssignmentName);
        formPanel.add(txtAssignmentName);
        formPanel.add(lblDueDate);
        formPanel.add(txtDueDate);
        formPanel.add(lblTime); // Add the time field here
        formPanel.add(txtTime); 
        formPanel.add(lblDescription);
        formPanel.add(new JScrollPane(txtDescription));
        
        formPanel.add(new JLabel());
        formPanel.add(assignButton);

        assignmentForm.add(formPanel);

        // Add ActionListener for Assign button
        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String assignmentName = txtAssignmentName.getText();
                String dueDateStr = txtDueDate.getText();
                String time = txtTime.getText();
                String description = txtDescription.getText();

                // Validate and parse due date
                Date dueDate = null;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    dueDate = sdf.parse(dueDateStr);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid due date format.");
                    return;
                }

                try {
                    Connection cn = DBCon.getConnection();
                    Statement st = cn.createStatement();

                    // Prepare and execute the insert query
                    String query = "INSERT INTO assignment (assignment_name, date, time, description) " +
                                   "VALUES ('" + assignmentName + "', '" + new java.sql.Date(dueDate.getTime()) + "', '" + time + "', '" + description + "')";
                    int result = st.executeUpdate(query);

                    // Show success message and close the form
                    if (result > 0) {
                        JOptionPane.showMessageDialog(null, "Assignment assigned successfully!");
                        addAssignmentToClass(className, assignmentName);
                        assignmentForm.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to assign assignment.");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        assignmentForm.setModal(true);
        assignmentForm.setVisible(true);
    }

    // Method to add an assignment to a class
    private static void addAssignmentToClass(String className, String assignmentName) {
        ArrayList<String> assignments = classAssignments.getOrDefault(className, new ArrayList<>());
        assignments.add(assignmentName);
        classAssignments.put(className, assignments);
    }

    // Method to show class options from home page
 // Method to show home page content
    private static void showHomePage() {
        contentPanel.removeAll();  // Clear previous content
        contentPanel.setLayout(new BorderLayout());

        // Side panel setup (Create Class and View Class buttons)
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.LIGHT_GRAY);
        sidePanel.setPreferredSize(new Dimension(150, 600)); // Fixed width
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));  // Vertical stacking

        JButton btnClass = new JButton("Create Class");
        btnClass.setBackground(Color.GRAY);
        btnClass.setForeground(Color.WHITE);
        sidePanel.add(btnClass);

        JButton btnClassView = new JButton("View Class");
        btnClassView.setBackground(Color.GRAY);
        btnClassView.setForeground(Color.WHITE);
        sidePanel.add(btnClassView);

        // Add ActionListeners for the buttons
        btnClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showClassForm();
            }
        });

        btnClassView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showClasses();
            }
        });

        contentPanel.add(sidePanel, BorderLayout.WEST);  // Fixed side panel on the left

        // Optionally, you can add additional content or a welcome message on the home page.
        JPanel homeContentPanel = new JPanel();
        homeContentPanel.setBackground(Color.WHITE);
        homeContentPanel.setLayout(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome to the Classwork Hub!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        homeContentPanel.add(welcomeLabel, BorderLayout.CENTER);

        contentPanel.add(homeContentPanel, BorderLayout.CENTER);  // Add home content

        contentPanel.revalidate();
        contentPanel.repaint();
    }



    // Method to show all available classes
 // Method to show all available classes
    private static void showClasses() {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        JPanel classesPanel = new JPanel();
        classesPanel.setLayout(new BoxLayout(classesPanel, BoxLayout.Y_AXIS));

        // Load classes from the database and add to the panel
        try {
            Connection cn = DBCon.getConnection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM createclass");

            while (rs.next()) {
                String className = rs.getString("class_name");
                String batch = rs.getString("batch");
                String code = rs.getString("code");

                JPanel classPanel = new JPanel();
                classPanel.setLayout(new BoxLayout(classPanel, BoxLayout.Y_AXIS));
                classPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                JLabel classLabel = new JLabel("Class: " + className + " (Batch: " + batch + ", Code: " + code + ")");
                classLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                JButton openButton = new JButton("Open Class");
                openButton.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Add ActionListener for the "Open Class" button
                openButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showClassOptions(className);
                    }
                });

                classPanel.add(classLabel);
                classPanel.add(openButton);

                classesPanel.add(classPanel);
            }

            JScrollPane classesScrollPane = new JScrollPane(classesPanel);
            contentPanel.add(classesScrollPane, BorderLayout.CENTER);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading classes: " + ex.getMessage());
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Method to load and resize icons
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
}

package com.UI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.DBConnection.DBCon;

public class Enterpage extends JFrame {

    JRadioButton teacherButton = null;
    JRadioButton studentButton = null;
    JLabel nameLabel = null;
    JTextField nameField = null;
    JLabel professionLabel = null;
    ButtonGroup professionGroup = null;
    JLabel emailLabel = new JLabel("Email:");
    JTextField emailField = new JTextField(20);
    JLabel phoneLabel = new JLabel("Phone No:");
    JTextField phoneField = new JTextField(20);
    JLabel passwordLabel = new JLabel("Password:");
    JPasswordField passwordField = new JPasswordField(20);
    JPasswordField confirmPasswordField = null;
    JButton actionButton = new JButton("Submit");

    public Enterpage(String type) {
        setTitle(type + " - Classwork Hub");
        setSize(400, 500);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        if (type.equals("Signup")) {
            nameLabel = new JLabel("Name:");
            nameField = new JTextField(20);

            professionLabel = new JLabel("Profession:");
            teacherButton = new JRadioButton("Teacher");
            studentButton = new JRadioButton("Student");
            professionGroup = new ButtonGroup();
            professionGroup.add(teacherButton);
            professionGroup.add(studentButton);

            phoneLabel = new JLabel("Phone No:");
            phoneField = new JTextField(20);

            confirmPasswordField = new JPasswordField(20);

            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(nameLabel, gbc);

            gbc.gridx = 1;
            panel.add(nameField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(professionLabel, gbc);

            gbc.gridx = 1;
            panel.add(teacherButton, gbc);
            gbc.gridx = 2;
            panel.add(studentButton, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(emailLabel, gbc);

            gbc.gridx = 1;
            panel.add(emailField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            panel.add(phoneLabel, gbc);

            gbc.gridx = 1;
            panel.add(phoneField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            panel.add(passwordLabel, gbc);

            gbc.gridx = 1;
            panel.add(passwordField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            panel.add(new JLabel("Confirm Password:"), gbc);

            gbc.gridx = 1;
            panel.add(confirmPasswordField, gbc);
        }
        else if (type.equals("Login")) {
            professionLabel = new JLabel("Profession:");
            teacherButton = new JRadioButton("Teacher");
            studentButton = new JRadioButton("Student");
            professionGroup = new ButtonGroup();
            professionGroup.add(teacherButton);
            professionGroup.add(studentButton);

            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(emailLabel, gbc);

            gbc.gridx = 1;
            panel.add(emailField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(passwordLabel, gbc);

            gbc.gridx = 1;
            panel.add(passwordField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(professionLabel, gbc);

            gbc.gridx = 1;
            panel.add(teacherButton, gbc);
            gbc.gridx = 2;
            panel.add(studentButton, gbc);
        }

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 5;
        gbc.gridheight = 10;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(actionButton, gbc);

        getContentPane().add(panel);
        setVisible(true);

        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword());
                String profession = null;

                if (teacherButton.isSelected()) {
                    profession = "Teacher";
                } else if (studentButton.isSelected()) {
                    profession = "Student";
                }

                if (type.equals("Signup")) {
                    handleSignup(email, password, profession);
                } else if (type.equals("Login")) {
                    handleLogin(email, password, profession);
                }
            }
        });
    }

    private void handleSignup(String email, String password, String profession) {
        String confirmPassword = confirmPasswordField != null ? new String(confirmPasswordField.getPassword()) : "";
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();

        // Check if any field is empty
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || profession == null) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields!");
            return;
        }

        // Validate name (only alphabetic characters allowed)
        if (!name.matches("[a-zA-Z ]+")) {
            JOptionPane.showMessageDialog(null, "Name can only contain alphabetic characters!");
            return;
        }

        // Validate email (must end with @gmail.com)
        if (!email.endsWith("@gmail.com")) {
            JOptionPane.showMessageDialog(null, "Email must be a Gmail address (e.g., example@gmail.com)!");
            return;
        }

        // Validate phone number (must be exactly 10 digits)
        if (!phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(null, "Phone number must be 10 digits!");
            return;
        }

        // Validate password match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match!");
            return;
        }

        // Database insert logic
        try (Connection cn = DBCon.getConnection(); 
             Statement st = cn.createStatement()) {
            int id = new Random().nextInt(10000);

            String query;
            if (profession.equals("Teacher")) {
                query = "INSERT INTO teacher VALUES(" + id + ", '" + name + "', '" + email + "', '" + password + "', '" + phone + "')";
            } else {
                query = "INSERT INTO student VALUES(" + id + ", '" + name + "', '" + email + "', '" + password + "', '" + phone + "')";
            }

            int i = st.executeUpdate(query);
            if (i == 1) {
                JOptionPane.showMessageDialog(null, "Sign up successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Something went wrong!");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void handleLogin(String email, String password, String profession) {
        if (email.isEmpty() || password.isEmpty() || profession == null) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields!");
            return;
        }

        try (Connection cn = DBCon.getConnection(); 
             Statement st = cn.createStatement()) {
            String query;

            if (profession.equals("Teacher")) {
                query = "SELECT * FROM teacher WHERE email = '" + email + "' AND password = '" + password + "'";
            } else {
                query = "SELECT * FROM student WHERE email = '" + email + "' AND password = '" + password + "'";
            }

            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                if (profession.equals("Teacher")) {
                    new Teacher();
                } else {
                    new Student();
                }
                JOptionPane.showMessageDialog(null, "Logged in as: " + rs.getString("name") + " (" + profession + ")");
                dispose(); // Close the login/signup window on successful login
            } else {
                JOptionPane.showMessageDialog(null, "Invalid email, password, or profession!");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Enterpage("Signup");
        new Enterpage("Login");
    }
}

package com.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UserInfoForm {

    public UserInfoForm() {
        // Create the frame for the user information form
        JFrame frame = new JFrame("User Information");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center the window

        // Create and add components to the frame
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(0, 0, 190, 46);
        JTextField nameField = new JTextField();
        nameField.setBounds(200, 0, 190, 46);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(0, 56, 190, 46);
        JTextField emailField = new JTextField();
        emailField.setBounds(200, 56, 190, 46);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(0, 112, 190, 46);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(200, 112, 190, 46);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(0, 168, 190, 46);
        JTextField phoneField = new JTextField();
        phoneField.setBounds(200, 168, 190, 46);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(200, 224, 190, 46);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle form submission
                String name = nameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String phone = phoneField.getText();

                // For demonstration, show a message with the input
                JOptionPane.showMessageDialog(frame, 
                    "Submitted!\nName: " + name + "\nEmail: " + email + "\nPhone: " + phone);

                // Clear fields after submission
                nameField.setText("");
                emailField.setText("");
                passwordField.setText("");
                phoneField.setText("");
            }
        });
        frame.getContentPane().setLayout(null);

        // Add components to the frame
        frame.getContentPane().add(nameLabel);
        frame.getContentPane().add(nameField);
        frame.getContentPane().add(emailLabel);
        frame.getContentPane().add(emailField);
        frame.getContentPane().add(passwordLabel);
        frame.getContentPane().add(passwordField);
        frame.getContentPane().add(phoneLabel);
        frame.getContentPane().add(phoneField);
        JLabel label = new JLabel();
        label.setBounds(0, 224, 190, 46);
        frame.getContentPane().add(label); // Empty cell
        frame.getContentPane().add(submitButton);

        // Show the frame
        frame.setVisible(true);
    }
}

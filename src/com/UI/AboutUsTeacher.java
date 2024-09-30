package com.UI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AboutUsTeacher extends JFrame {

    public AboutUsTeacher()  {
        
        
        // Create a text area to show the "About" information
        JTextArea aboutText = new JTextArea();
        aboutText.setText("Welcome to Classwork Hub!\n\n"
                + "As a teacher, you can perform the following tasks:\n\n"
                + "1. Create and manage your classes.\n"
                + "2. Assign and manage assignments.\n"
                + "3. View assignments submitted by students.\n"
                + "4. Track the progress of students.\n"
                + "5. Provide feedback on assignments.\n\n"
                + "For detailed help, please refer to the user guide or contact support.");
        aboutText.setEditable(false);
        aboutText.setWrapStyleWord(true);
        aboutText.setLineWrap(true);
        
        // Add scroll pane in case the content overflows
        JScrollPane scrollPane = new JScrollPane(aboutText);
        
        // Set layout and add the text area
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

   
    public void showAboutPage() {
        // Set the window visible when this method is called
        this.setVisible(true);
    }

    // Main method to test the About Us page (optional for testing)
    public static void main(String[] args) {
        new AboutUsTeacher();
       
    }
}

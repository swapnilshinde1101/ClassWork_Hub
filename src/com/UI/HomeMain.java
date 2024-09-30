package com.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import org.eclipse.wb.swing.FocusTraversalOnArray;

public class HomeMain {
    
    private static final int SLIDE_DELAY = 3000; // Time in milliseconds for each slide
    private JFrame frame;

    public HomeMain() {
        // Create the main frame
        frame = new JFrame("Classwork Hub");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // Center the window

        // Create the header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.GRAY);
        headerPanel.setPreferredSize(new Dimension(600, 100));
        JLabel headerLabel = new JLabel("Classwork Hub", SwingConstants.CENTER);
        headerLabel.setVerticalAlignment(SwingConstants.TOP);
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);

        // Sign in button
        JButton btnLogin = new JButton("Sign in");
        btnLogin.setBackground(Color.GRAY);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Enterpage("Login"); // Open login form when Sign In is clicked
            }
        });

        // Sign Up button
        JButton btnSignup = new JButton("Sign Up");
        btnSignup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Enterpage("Signup"); // Open signup form when Sign Up is clicked
            }
        });
        btnSignup.setForeground(Color.WHITE);
        btnSignup.setBackground(Color.GRAY);

        GroupLayout gl_headerPanel = new GroupLayout(headerPanel);
        gl_headerPanel.setHorizontalGroup(
            gl_headerPanel.createParallelGroup(Alignment.TRAILING)
                .addGroup(gl_headerPanel.createSequentialGroup()
                    .addContainerGap(329, Short.MAX_VALUE)
                    .addComponent(btnSignup)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(btnLogin)
                    .addGap(24))
                .addGroup(Alignment.LEADING, gl_headerPanel.createSequentialGroup()
                    .addGap(286)
                    .addComponent(headerLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(306))
        );
        gl_headerPanel.setVerticalGroup(
            gl_headerPanel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_headerPanel.createSequentialGroup()
                    .addComponent(headerLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(41)
                    .addGroup(gl_headerPanel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnSignup)
                        .addComponent(btnLogin, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)))
        );
        headerPanel.setLayout(gl_headerPanel);
        headerPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{headerLabel}));

        // Create the image carousel panel
        JPanel carouselPanel = new JPanel();
        carouselPanel.setLayout(new CardLayout());

        String[] imagePaths = {
            "/home/mima9/Desktop/Assignment Writer/Assingnment_writer/src/com/image/7c625e6f378995e40bb30075f8c1f70b.png",
            "/home/mima9/Desktop/Assignment Writer/Assingnment_writer/src/com/image/240_F_837980477_9RaHkhWMLlKZONyr0MvS36mDoOEUNrcO.jpg",
            "/home/mima9/Desktop/Assignment Writer/Assingnment_writer/src/com/image/Assignment.jpg"
        };

        // Add images to the carousel panel
        for (String path : imagePaths) {
            ImageIcon icon = new ImageIcon(path);
            JLabel label = new JLabel(icon);
            carouselPanel.add(label);
        }

        JScrollPane scrollPane = new JScrollPane(carouselPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Setup image rotation
        Timer timer = new Timer(SLIDE_DELAY, new ActionListener() {
            private int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (carouselPanel.getLayout());
                cl.next(carouselPanel);
            }
        });
        timer.start();

        // Add component listener to handle window resizing
        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                // Adjust the size of images to fit the new size of the frame
                for (Component comp : carouselPanel.getComponents()) {
                    if (comp instanceof JLabel) {
                        JLabel label = (JLabel) comp;
                        ImageIcon icon = (ImageIcon) label.getIcon();
                        Image img = icon.getImage();
                        Image resizedImg = img.getScaledInstance(frame.getWidth(), frame.getHeight() - headerPanel.getHeight(), Image.SCALE_SMOOTH);
                        label.setIcon(new ImageIcon(resizedImg));
                    }
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomeMain();
            }
        });
    }
}

package com.UI;


import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
public class AdminHome extends JFrame implements ActionListener{

	JLabel bgimg;
	JTextArea header;
	JMenuItem abook,assignBooks,AddUser,retriveBook,view,edit,logOut,showBooks,deleteUser;
	JMenu menu, submenu;  
    
	public AdminHome() {
		
		setVisible(true);
		setSize(500,500);
		getContentPane().setLayout(null);
		
		
		
				
		JMenuBar mb=new JMenuBar();  
			
        menu=new JMenu("MENU"); 
        abook=new JMenuItem("Add Books");
        abook.setBackground(Color.white);
        abook.addActionListener(this);
        assignBooks=new JMenuItem("assignBook");
        assignBooks.setBackground(Color.white);
        assignBooks.addActionListener(this);
        AddUser=new JMenuItem("AddUser");
        AddUser.setBackground(Color.white);
        AddUser.addActionListener(this);
        retriveBook=new JMenuItem("retriveBook");
        retriveBook.setBackground(Color.white);
        retriveBook.addActionListener(this);
        showBooks=new JMenuItem("showBooks");
        showBooks.setBackground(Color.white);
        showBooks.addActionListener(this);
        deleteUser=new JMenuItem("Delete User");
        deleteUser.setBackground(Color.white);
        deleteUser.addActionListener(this);
        
        view=new JMenuItem("View    ");
        view.setBackground(Color.white);
        view.addActionListener(this);
        edit=new JMenuItem("Edit    ");
        edit.setBackground(Color.white);
        edit.addActionListener(this);
        logOut=new JMenuItem("LogOut");
        logOut.setBackground(Color.white);
        logOut.addActionListener(this);
        
        submenu=new JMenu("Profile");  
        submenu.add(view); 
        submenu.add(edit); 
        submenu.add(logOut);
          
        menu.add(submenu);
        mb.add(menu); 
        mb.add(abook);
        mb.add(assignBooks);
        mb.add(AddUser);
        mb.add(retriveBook);
        mb.add(showBooks); 
        mb.add(deleteUser);
        mb.setBounds(10,10,1260,40);
       
		
        
        header=new JTextArea();
		header.setBounds(0,0,1400,60);
		header.setBackground(Color.lightGray);
		header.setEditable(false);
		
		
		
		ImageIcon i11=new ImageIcon("library.jpg");
		Image img=i11.getImage().getScaledInstance(1400,800,Image.SCALE_SMOOTH);
		i11=new ImageIcon(img);
		bgimg=new JLabel("",i11,JLabel.CENTER);
		bgimg.setBounds(0,0,1400,800);
		getContentPane().add(bgimg);
		
		bgimg.add(mb);
		bgimg.add(header);
		
		
		         
	  
		
	}

	public void actionPerformed(ActionEvent ae)
	{
//		if(ae.getSource()==abook)
//		{
//			
//			AddBook a=new AddBook();
//			a.setLocation(100,150);
//		
//		}
//		if(ae.getSource()==assignBooks)
//		{
//			
//			AssignBookGUI a=new AssignBookGUI();
//			a.setLocation(100,150);
//		
//		}
//		if(ae.getSource()==showBooks)
//		{
//			
//			new ShowBooks();
//					
//		}
//		if(ae.getSource()==AddUser)
//		{
//			
//			new AddUser();
//			
//					
//		}
//		if(ae.getSource()==retriveBook)
//		{
//			
//			new RetriveBook();
//			
//					
//		}
//		if(ae.getSource()==deleteUser)
//		{
//			
//			new deleteUser();
//			
//					
//		}
//		if(ae.getSource()==logOut)
//		{
//			setVisible(false);
//			new Home();
//		}
	}
	
	public static void main(String[] args) {
		
		new AdminHome();
	}
}



//package com.UI;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import javax.imageio.ImageIO;
//
//public class Techar {
//
//    public static void main(String[] args) {
//        // Create the main frame
//        JFrame frame = new JFrame("Classwork Hub");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(800, 600);
//        frame.setLocationRelativeTo(null); // Center the window
//
//        // Create the header panel
//        JPanel headerPanel = new JPanel();
//        headerPanel.setBackground(Color.GRAY);
//        headerPanel.setPreferredSize(new Dimension(800, 100)); // Adjusted to match frame width
//        JLabel headerLabel = new JLabel("Classwork Hub", SwingConstants.CENTER);
//        headerLabel.setVerticalAlignment(SwingConstants.TOP);
//        headerLabel.setForeground(Color.WHITE);
//        headerLabel.setFont(new Font("Arial", Font.BOLD, 30));
//        headerPanel.add(headerLabel);
//        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);
//
//        // Create the placeholder panel
//        JPanel placeholderPanel = new JPanel();
//        placeholderPanel.setBackground(Color.WHITE);
//
//        JScrollPane scrollPane = new JScrollPane(placeholderPanel);
//        placeholderPanel.setLayout(null);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//
//        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
//        
//        // Create the menu bar
//        JMenuBar menuBar = new JMenuBar();
//        menuBar.setBackground(Color.LIGHT_GRAY);
//        scrollPane.setColumnHeaderView(menuBar);
//        
//        // Create "Home" and "About" buttons
//        JMenu homeMenu = new JMenu("Home");
//        homeMenu.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                // Handle home action
//                JOptionPane.showMessageDialog(frame, "Home clicked");
//            }
//        });
//        menuBar.add(homeMenu);
//
//        JMenu aboutMenu = new JMenu("About");
//        aboutMenu.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                // Handle about action
//                JOptionPane.showMessageDialog(frame, "About clicked");
//            }
//        });
//        menuBar.add(aboutMenu);
//
//        // Load and resize user icon
//        ImageIcon userIcon = loadAndResizeIcon("/home/mima9/Desktop/Assignment Writer/Assingnment_writer/src/com/image/login.png", 30, 30);
//
//        // Create user menu and add it to the menu bar
//        JMenu userMenu = new JMenu();
//        userMenu.setIcon(userIcon); // Set resized icon
//        userMenu.setText("User");
//
//        JMenuItem profileMenuItem = new JMenuItem("Profile");
//        profileMenuItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                // Handle profile action
//                JOptionPane.showMessageDialog(frame, "Profile clicked");
//            }
//        });
//
//        JMenuItem logoutMenuItem = new JMenuItem("Logout");
//        logoutMenuItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                // Handle logout action
//                JOptionPane.showMessageDialog(frame, "Logout clicked");
//            }
//        });
//
//        userMenu.add(profileMenuItem);
//        userMenu.add(logoutMenuItem);
//        menuBar.add(Box.createHorizontalGlue()); // Push userMenu to the right
//        menuBar.add(userMenu);
//
//        // Show the frame
//        frame.setVisible(true);
//    }
//
//    private static ImageIcon loadAndResizeIcon(String path, int width, int height) {
//        try {
//            BufferedImage img = ImageIO.read(new File(path));
//            Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
//            return new ImageIcon(resizedImg);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null; // Return null if there was an issue loading the image
//        }
//    }
//}

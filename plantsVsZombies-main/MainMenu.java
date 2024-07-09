/*
 * Authors: Zayaan and Brandon
 * Date: Jan 17 2023
 * Program Name: Main Menu Class
 * Program Description: Main menu with instructions and play button
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenu extends JPanel implements ActionListener{
	
		  // initalizing buttons and background img
		  private static JButton b1 = new JButton("Play");
		  private JButton b2;
		  private JButton b3;
	      private ImageIcon bgImg = new ImageIcon("images/titleScreen.png");
	  	  
		  // constructing main menu	
		  public MainMenu() {
		    b1.addActionListener(this);
		    b1.setContentAreaFilled(false);
			b1.setFont(new Font("Serif", Font.PLAIN, 18));
			b1.setForeground(Color.WHITE);

		    
		    b2 = new JButton("How to play?");
		    b2.addActionListener(this);
		    b2.setContentAreaFilled(false);
			b2.setFont(new Font("Serif", Font.PLAIN, 18));
			b2.setForeground(Color.WHITE);


		    
		    b3 = new JButton("Quit");
		    b3.addActionListener(this);
		    b3.setContentAreaFilled(false);
			b3.setFont(new Font("Serif", Font.PLAIN, 18));
			b3.setForeground(Color.WHITE);


		    
		    JPanel pSouth= new JPanel();
			setLayout(new BorderLayout());
			add(pSouth, BorderLayout.SOUTH);
			pSouth.setLayout(new GridLayout(3, 1));
			pSouth.setOpaque(false);
		    
		    pSouth.add(b1);
		    pSouth.add(b2);
		    pSouth.add(b3);
		    
		  }
		
		  // painting onto panel
		  public void paintComponent(Graphics g) {
			super.paintComponents(g);
			g.drawImage(bgImg.getImage(), 0, 0, getWidth(), getHeight(), null);
		  }
		
		  // checking if buttons are clicked
		  public void actionPerformed(ActionEvent e) {
			  if(e.getSource()==b1){
					GameScreen gameP = new GameScreen(this);
					MyJava.c.add(("Level" + GamePanel.getLevel()), gameP);
			      	MyJava.cardsL.show(MyJava.c, ("Level" + GamePanel.getLevel()));
			  }
			  else if (e.getSource()==b2) {					
					MyJava.cardsL.show(MyJava.c, "Instructions");
			  }
			  else if (e.getSource()==b3){
				  System.exit(0);
			  }	
		  }
}


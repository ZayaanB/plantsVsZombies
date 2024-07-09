/*
 * Authors: Zayaan and Brandon
 * Date: Jan 17 2023
 * Program Name: Instructions Pages
 * Program Description: Game Instructions Display and Clickable
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Instructions extends JPanel implements ActionListener{
	
		  // initalizing buttons and background img
		  private static JButton b1 = new JButton("Next");
		  private int slide = 0;
		  private JButton b2 = new JButton("Return to Main Menu");
	      private ImageIcon bgImg = new ImageIcon("images/instructions/instructionsPg0.png");
	      
	  	  	      
		  // constructing main menu	
		  public Instructions() {
		    b1.addActionListener(this);
		    b1.setContentAreaFilled(false);
			b1.setFont(new Font("Serif", Font.PLAIN, 24));
			b1.setForeground(Color.BLACK);

		    
		    b2 = new JButton("Return to main menu");
		    b2.addActionListener(this);
		    b2.setContentAreaFilled(false);
			b2.setFont(new Font("Serif", Font.PLAIN, 24));
			b2.setForeground(Color.WHITE);
		    
		    JPanel pSouth= new JPanel();
			setLayout(new BorderLayout());
			add(pSouth, BorderLayout.SOUTH);
			pSouth.setLayout(new GridLayout(2, 1));
			pSouth.setOpaque(false);
		    
		    pSouth.add(b1);
		    pSouth.add(b2);
		    
		  }
		
		  // painting onto panel
		  public void paintComponent(Graphics g) {
			super.paintComponents(g);
			g.drawImage(bgImg.getImage(), 0, 0, getWidth(), getHeight(), null);
			
			if (slide == 3) {
				slide = -1;
			}
		  }
		
		  // checking if buttons are clicked
		  public void actionPerformed(ActionEvent e) {
			 
			  if(e.getSource()==b1){
				  slide++;
				  bgImg = new ImageIcon("images/instructions/instructionsPg"+slide+".png");
				  repaint();
			  }
			  else if (e.getSource()==b2) {
				  slide = 0;
				  MyJava.cardsL.show(MyJava.c, "Main Menu");
			  }	
			  
			  
		  }
}

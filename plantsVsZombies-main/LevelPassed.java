/*
 *Name: Zayaan and Brandon
 *Date: Jan 17 2023
 *Program name: Level Passed Screen
 *Description: Display a victory screen when user completes level
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class LevelPassed extends JPanel implements MouseListener {
    // level passed components and constructor
      private ImageIcon bgImg = new ImageIcon("images/Victory.png");
      private JLabel text = new JLabel("Click Anywhere to Return to Main Menu");
	  
	  public LevelPassed() {	    
	    
	    addMouseListener(this);
	    setBackground(Color.BLACK);
		text.setForeground(Color.WHITE);
	    
		JPanel pSouth= new JPanel();
		setLayout(new BorderLayout());
		add(pSouth, BorderLayout.SOUTH);
		pSouth.setOpaque(false);
		text.setFont(new Font("Serif", Font.PLAIN, 36));
	    pSouth.add(text);
	    
	  }
	
	  public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(bgImg.getImage(), 0, 0, getWidth(), getHeight(), null);
	  }
	  
	  // going to main menu when user clicks
	  public void mouseClicked(MouseEvent e) {
	      	MyJava.cardsL.show(MyJava.c, "Main Menu");
	  }
	
	  public void mousePressed( MouseEvent e ){   }
	  public void mouseReleased( MouseEvent e ){   }
	  public void mouseEntered( MouseEvent e ) {   }
	  public void mouseExited( MouseEvent e )  {   }

}

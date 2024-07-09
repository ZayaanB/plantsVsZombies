/*
 *Name: Zayaan and Brandon
 *Date: Jan 17 2023
 *Program name: Game Over Screen
 *Description: Screen to display after user loses a level
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class GameOver extends JPanel implements MouseListener {
	  // game over screen components and constructor
      private ImageIcon bgImg = new ImageIcon("images/GameOver.png");
      private JLabel text = new JLabel("Click Anywhere to Return to Main Menu");
	  	
	  public GameOver() {	    
	    
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

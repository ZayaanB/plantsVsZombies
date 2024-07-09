/*
 * Authors: Zayaan and Brandon
 * Date: Jan 17 2023
 * Program Name: Minishroom Plant Class
 * Program Description: Create minishroom and control shooting
 */

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Minishroom extends Plant {

	// peashooter variables
    public Timer shootTimer;
    private boolean isShooting;

    // creating peashooters
    public Minishroom(GamePanel parent, int x, int y) {
        super(parent, x, y, 100);
        shoot();
    }

    // shoot method
    public void shoot() {
        shootTimer = new Timer(2000, (ActionEvent e) -> {
        	
        	// checking if zombie is in lane
            if(getGp().getActiveZombies().get(getY()).size() > 0 && getHealth() > 0){
            	
            	// checking if zombie is infront of peashooter
            	for (int i = 0; i < getGp().getActiveZombies().get(getY()).size(); i++) {

            		if(getGp().getActiveZombies().get(getY()).get(i).getXCord() >= getXCord() && getHealth() > 0) {
            			this.isShooting = true;
                        getGp().getActivePeas().get(getY()).add(new Goopball(getGp(), getY(), 103 + this.getX() * 100));
                        break;
            		}
            	}              
            }
            else{ 
                this.isShooting = false;
            }
        });
        shootTimer.start();
    }
    
    // getters and timer stop
    public boolean isShooting(){
        return this.isShooting;
    }
    public void stop() {
        shootTimer.stop();
    }

}

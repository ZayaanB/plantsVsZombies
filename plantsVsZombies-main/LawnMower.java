/*
 * Authors: Zayaan and Brandon
 * Date: Jan 17 2023
 * Program Name: Lawn mower class
 * Program Description: Create lawnmower and control movement
 */

import java.awt.Rectangle;

public class LawnMower {

	// lawnmower variables
    private int posX;
    protected GamePanel gp;
    private int myLane;
    private boolean zombieReachEnd;
    private boolean onScreen = true;

    // creating lawnmower
    public LawnMower(GamePanel parent, int lane, int startX) {
        this.gp = parent;
        this.myLane = lane;
        posX = 0;
        zombieReachEnd = false;
    }

    public void move() {
        Rectangle mowerRect = new Rectangle(posX, 130 + myLane * 120, 28, 28);
        
        // only move and check when on screen
        if (onScreen) {
        	
        	// going through every zombie in lane
	        for (int i = 0; i < gp.getActiveZombies().get(myLane).size(); i++) {
	            Zombie z = gp.getActiveZombies().get(myLane).get(i);
	            Rectangle zRect = new Rectangle(z.getXCord(), 109 + myLane * 120, 400, 120);
	            
	            // check if zombie has triggered lawnmower
	            if (mowerRect.intersects(zRect)) {
	                zombieReachEnd = true;
	                z.setHealth(z.getHealth() - 2000);
	                gp.zombieDied();
	                gp.getActiveZombies().get(myLane).remove(i);
	                
	            }
	            
	        }
	        
	        // checking if mower is on the screen
	        if (posX > 1000) {
	            onScreen = false;
	        }
	        
	        // moving mower when it has been triggered
	        if (zombieReachEnd) {
	        	posX += 15;
	        }
        }
        
    }

    // getters and setters
    public int getPosX() {
        return posX;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }
    public int getMyLane() {
        return myLane;
    }
    public void setMyLane(int myLane) {
        this.myLane = myLane;
    }
}

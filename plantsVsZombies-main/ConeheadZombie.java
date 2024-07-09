/*
 * Authors: Zayaan and Brandon
 * Date: Jan 17 2023
 * Program Name: Conehead Zombie Class
 * Program Description: Creatig zombie with different type and more health than normal
 */

public class ConeheadZombie extends Zombie{

	// changing health and zombie type
	public ConeheadZombie(GamePanel parent, int lane) {
		super(parent, lane);
		setHealth(1500);
		setZombieType("cone");
	}
	
}

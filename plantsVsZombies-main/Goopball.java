/*
 * Authors: Zayaan and Brandon
 * Date: Jan 17 2023
 * Program Name: Goopball Projectile for Puffshroom
 * Program Description: Chnaging pea damage and pea type
 */

public class Goopball extends Pea{

	public Goopball(GamePanel parent, int lane, int startX) {
		super(parent, lane, startX);
		setDamage(50);
		setPeaType("shroom");
	}

}

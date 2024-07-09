/*
 * Authors: Zayaan and Brandon
 * Date: Jan 17 2023
 * Program Name: Game Screen Class
 * Program Description: Create labels and background and initalize PlantType
 */

import javax.swing.*;
import java.awt.*;


public class GameScreen extends JLayeredPane {

    enum PlantType {
        None,
        Shovel,
        Minishroom,
        Sunflower,
        Peashooter,
        Wallnut,
        Repeater,
    }

    // gamescreen constructor
    public GameScreen(MainMenu menu) {
        setSize(1012, 785);

        JLabel sun = new JLabel();
        sun.setForeground(Color.white);
        sun.setFont(new Font("Futura", Font.BOLD, 13));
        sun.setLocation(50, 80);
        sun.setSize(60, 20);


        GamePanel gp = new GamePanel(sun, menu);
        gp.setLocation(0, 0);
        this.add(gp, 0);

        add(sun, 0);
    }

}
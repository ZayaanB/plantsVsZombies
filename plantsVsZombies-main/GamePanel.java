/*
 * Authors: Zayaan and Brandon
 * Date: Jan 17 2023
 * Program Name: Main Game Panel
 * Program Description: Display images and game classes and attributes 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JLayeredPane implements MouseMotionListener {

    private Collider[] colliders;
    private final Image bgImage = new ImageIcon("images/mainBG.png").getImage();
    private Timer generateTimer;
    private Timer redrawTimer;
    private static int level = 1;
    private int zombieType;

    // plant animations
    private final Image peashooterIdleAnim = new ImageIcon("images/animations/peashooterIdle.gif").getImage();
    private final Image peashooterShootingAnim = new ImageIcon("images/animations/peashooterShooting.gif").getImage();
    private final Image wallnutImage = new ImageIcon("images/animations/wallnutIdle.gif").getImage();
    private final Image sunflowerIdleAnim = new ImageIcon("images/animations/sunflowerIdle.gif").getImage();
    private final Image puffShroomIdleAnim = new ImageIcon("images/animations/puffshroomIdle.png").getImage();
    private final Image repeaterIdleAnim = new ImageIcon("images/animations/repeaterIdle.gif").getImage();
    
    // plant card images
    private final Image wallnutCardImg = new ImageIcon("images/cards/wallnutCard.png").getImage();
    private final Image disabledWallnutCardImg = new ImageIcon("images/cards/disabledWallnutCard.png").getImage();
    private final Image sunflowerCardImg = new ImageIcon("images/cards/sunflowerCard.png").getImage();
    private final Image disabledSunflowerCardImg = new ImageIcon("images/cards/disabledSunflowerCard.png").getImage();
    private final Image peashooterCardImg = new ImageIcon("images/cards/peashooterCard.png").getImage();
    private final Image disabledPeashooterCardImg = new ImageIcon("images/cards/disabledPeashooterCard.png").getImage();
    private final Image puffshroomCardImg = new ImageIcon("images/cards/puffshroomCard.png").getImage();
    private final Image disabledPuffshroomCardImg = new ImageIcon("images/cards/disabledPuffshroomCard.png").getImage();
    private final Image repeaterCardImg = new ImageIcon("images/cards/repeaterCard.png").getImage();
    private final Image disabledRepeaterCardImg = new ImageIcon("images/cards/disabledRepeaterCard.png").getImage();
    private final Image shovelImg = new ImageIcon("images/cards/shovel.png").getImage();

    // lawnmower image and arraylist
    private final Image mowerImage = new ImageIcon("images/Lawnmower.png").getImage();
    private ArrayList<LawnMower> activeMowers = new ArrayList<LawnMower>();

    // sun image and arraylist
    private JLabel sunScoreBoard;
    private ArrayList<Sun> activeSuns;
    private int sunScore;
    private Timer sunProducer;

    // zombie image and arraylist
    private int zombiesDead;
    private ArrayList<ArrayList<Zombie>> activeZombies;
    private final Image zombieWalkingAnim = new ImageIcon("images/coneZombie.png").getImage();
    private final Image coneheadWalkingAnim = new ImageIcon("images/zombieWalking.gif").getImage();
    private int maxZombies = 0;
    private int totalZombies = 0;
    private int spawnSpeed;
    private Timer zombieSpawner;
    
    // pea image and arraylist
    private final ArrayList<ArrayList<Pea>> peaLanes;
    private final Image peaImage = new ImageIcon("images/pea.png").getImage();
    private final Image goopImage = new ImageIcon("images/shroomball.png").getImage();

    private GameScreen.PlantType currentPlantingBrush = GameScreen.PlantType.None;

    public GamePanel(JLabel sunScoreboard, MainMenu menu) {
        setSize(1000, 752);
        setLayout(null);
        addMouseMotionListener(this);
        this.sunScoreBoard = sunScoreboard;
        setSunScore(700);
        spawnSpeed = 18000 - (level * 1500);
        
        peaLanes = new ArrayList<>();
        peaLanes.add(new ArrayList<>()); //lane 1
        peaLanes.add(new ArrayList<>()); //lane 2
        peaLanes.add(new ArrayList<>()); //lane 3
        peaLanes.add(new ArrayList<>()); //lane 4
        peaLanes.add(new ArrayList<>()); //lane 5

        activeZombies = new ArrayList<>();
        activeZombies.add(new ArrayList<>()); //lane 1
        activeZombies.add(new ArrayList<>()); //lane 2
        activeZombies.add(new ArrayList<>()); //lane 3
        activeZombies.add(new ArrayList<>()); //lane 4
        activeZombies.add(new ArrayList<>()); //lane 5

        // changing max zombie spawns based on level
        if (level <= 6) {
	        maxZombies = level * 4;
        }
        else {
        	maxZombies = 9999;
        }
        
        // redraw timer
        redrawTimer = new Timer(25, (ActionEvent e) -> {
            repaint();            
        });
        redrawTimer.start();

        // generate timer and check for win
        generateTimer = new Timer(50, (ActionEvent e) -> {      	
        	try {
				generate();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}       	
		});
        generateTimer.start();

        // timer that produces sun randomly
        activeSuns = new ArrayList<>();
        sunProducer = new Timer(((int)(Math.random() * 3) + 1) * 10000, (ActionEvent e) -> {
            Random rand = new Random();
            Sun sun = new Sun(this, rand.nextInt(800) + 100, 0, rand.nextInt(300) + 200);
            activeSuns.add(sun);
            add(sun, 1);
        });
        sunProducer.start();

        // zombie spawner that spawns zombies based on timer
        zombieSpawner = new Timer(spawnSpeed, (ActionEvent e) -> {
            Zombie zombie;
            int lane = (int)(Math.random() * 5);          
            
            // types of zombies
            zombieType = (int)(Math.random()*10) + 1;
            if (zombieType < 8) {
            	zombie = new Zombie(this, lane);
            }
            else {
            	zombie = new ConeheadZombie(this, lane);
            }
            
            // making spawns faster overtime
            if (spawnSpeed >= 5000) {
            	spawnSpeed -= 250;
            }
            zombieSpawner.setDelay(spawnSpeed--);

            
            // adding zombie to screen
			activeZombies.get(lane).add(zombie);
            add(zombie, 1);
            totalZombies++;
        });
        zombieSpawner.start();

        // adding a plant collider to each grid
        colliders = new Collider[45];
        for (int i = 0; i < 45; i++) {
            Collider a = new Collider();
            a.setLocation(44 + (i % 9) * 100, 109 + (i / 9) * 120);
            a.setAction(new PlantActionListener((i % 9), (i / 9)));
            colliders[i] = a;
            add(a, 0);
        }
        
        // creating lawnmowers for each lane
        for (int i = 0; i < 5; i++) {
	        LawnMower lawnmower = new LawnMower(this, i, 0);
	        activeMowers.add(lawnmower);
        }
    }

    // generating suns and zombies method
    private void generate() throws InterruptedException {
        //produce sun
	    for (int i = 0; i < activeSuns.size(); i++) {
	        activeSuns.get(i).create();
	    }


        //produce zombies
	    outerloop:
        for (int i = 0; i < activeZombies.size(); i++) {
        	if (activeZombies.get(i).size() > 0) {
	            for (Zombie z: activeZombies.get(i)) {
	            	if (totalZombies == maxZombies) {
	            		zombieSpawner.stop();
	            	}
	            	
	            	// checking if a zombie has reached the end
	            	if (z.getXCord() < 2) {
	            		MyJava.cardsL.show(MyJava.c, "Game Over");
	            		for(int j = 0; j < 5; j++) {
	            			activeZombies.get(j).clear();
	            			peaLanes.get(j).clear();
	            		}
	        	      	activeSuns.clear();
	        	      	activeMowers.clear();
	        	      	zombieSpawner.stop();
	        	      	sunProducer.stop();
	        	      	redrawTimer.stop();
	        	      	generateTimer.stop();
	        	      	colliders = new Collider[0];
	        	      	break outerloop;
	            	}
	            	z.create();
	            }
        	}
	            
        }
        
        // checking if all zombies have been killed
        if (totalZombies >= maxZombies && zombiesDead == maxZombies) {
        	redrawTimer.stop();
        	generateTimer.stop();
        	sunProducer.stop();
        	zombieSpawner.stop();
        	
	      	MyJava.cardsL.show(MyJava.c, "Level Completed");
	      	
        	for(int i = 0; i < 5; i++) {
	      		activeZombies.get(i).clear();
	      		peaLanes.get(i).clear();
	      	}
	      	activeSuns.clear();
	      	activeMowers.clear();
	      	colliders = new Collider[0];
	      	
	      	
	      	sunScore = 0;
	      	zombiesDead = 0;
	      	totalZombies = 0;
	      	level++;
	      	
    	}
    }
    
    // painting onto panel
    public void paintComponent(Graphics g) {
    	// drawing background and components
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.PLAIN, 24));
        g.drawImage(bgImage, 0, 0, null);
        g.drawString("Level " + level, 888, 25);

        // checking when sunflower can be placed and adding to grid
        PlantCard sunflowerCard;
        if (this.getSunScore() < 50){
            sunflowerCard = new PlantCard(disabledSunflowerCardImg);
        }else{
            sunflowerCard = new PlantCard(sunflowerCardImg);
        }
        sunflowerCard.setAction((ActionEvent e) -> this.setCurrentPlantingBrush(GameScreen.PlantType.Sunflower));
        sunflowerCard.setLocation(115, 8);
        add(sunflowerCard, 0);

        // checking when peashooter can be placed and adding to grid
        PlantCard peashooterCard;
        if (this.getSunScore() < 100){
            peashooterCard = new PlantCard(disabledPeashooterCardImg);
        }else{
            peashooterCard = new PlantCard(peashooterCardImg);
        }
        peashooterCard.setAction((ActionEvent e) -> this.setCurrentPlantingBrush(GameScreen.PlantType.Peashooter));
        peashooterCard.setLocation(185, 8);
        add(peashooterCard, 0);
        
        // checking when wallnut can be placed and adding to grid
        PlantCard wallnutCard;
        if (this.getSunScore() < 50){
            wallnutCard = new PlantCard(disabledWallnutCardImg);
        }else{
            wallnutCard = new PlantCard(wallnutCardImg);
        }
        wallnutCard.setAction((ActionEvent e) -> this.setCurrentPlantingBrush(GameScreen.PlantType.Wallnut));
        wallnutCard.setLocation(255, 8);
        add(wallnutCard, 0);
        
        // checking when puffshroom can be placed and adding to the grid
        PlantCard puffShroomCard = null;
        if (level >= 5) {
        	if (this.getSunScore() < 20){
                puffShroomCard = new PlantCard(disabledPuffshroomCardImg);
            }else{
                puffShroomCard = new PlantCard(puffshroomCardImg);
            }
	        puffShroomCard.setAction((ActionEvent e) -> this.setCurrentPlantingBrush(GameScreen.PlantType.Minishroom));
	        puffShroomCard.setLocation(325, 8);
	        add(puffShroomCard, 0);
        }
        
        // checking when wallnut can be placed and adding to grid
        PlantCard repeaterCard = null;
        if (level >= 6) {
	        if (this.getSunScore() < 200){
	            repeaterCard = new PlantCard(disabledRepeaterCardImg);
	        }else{
	            repeaterCard = new PlantCard(repeaterCardImg);
	        }
        
        	repeaterCard.setAction((ActionEvent e) -> this.setCurrentPlantingBrush(GameScreen.PlantType.Repeater));
        	repeaterCard.setLocation(395, 8);
        	add(repeaterCard, 0);
    	}     
        
        // making shovel card
        PlantCard shovel;
        shovel = new PlantCard(shovelImg);
        shovel.setAction((ActionEvent e) -> this.setCurrentPlantingBrush(GameScreen.PlantType.Shovel));
        shovel.setLocation(470, 23);
        add(shovel, 0);
        
        //draw peas and zombies based on lane
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < peaLanes.get(i).size(); j++) {
                Pea p = peaLanes.get(i).get(j);
                p.move();
            }

            for(Zombie z : activeZombies.get(i)){
            	if (z.getZombieType().equals("cone"))
            		g.drawImage(coneheadWalkingAnim, z.getXCord(), 115 + (i * 120), null);
            	else
            		g.drawImage(zombieWalkingAnim, z.getXCord(), 115 + (i * 120), null);
            }
        }
        
        
        // lawnmower moving based on lane
        for (int i = 0; i < activeMowers.size(); i++) {
        		LawnMower m = activeMowers.get(i);
        		m.move();
            	g.drawImage(mowerImage, activeMowers.get(i).getPosX(), 130 + (i * 120), null);
        }
        

        //draw plants
        for (int i = 0; i < 45; i++) {
            Collider c = colliders[i];
            if (c.assignedPlant != null) {
                Plant plant = c.assignedPlant;
                g.drawString(Integer.toString(plant.getHealth()), 69 + (i % 9) * 104, 129 + (i / 9) * 115);
                if (plant instanceof Peashooter) {
                    if(((Peashooter) plant).isShooting()){
                        g.drawImage(peashooterShootingAnim, 60 + (i % 9) * 100, 129 + (i / 9) * 120, null);
                    }else{
                        g.drawImage(peashooterIdleAnim, 60 + (i % 9) * 100, 129 + (i / 9) * 120, null);
                    }

                }
                if (plant instanceof Sunflower) {
                    g.drawImage(sunflowerIdleAnim, 60 + (i % 9) * 100, 129 + (i / 9) * 120, null);
                }
                if(plant instanceof Wallnut) {
                    g.drawImage(wallnutImage, 60 + (i % 9) * 100, 129 + (i / 9) * 120, null);
                }
                if (plant instanceof Minishroom) {
                	g.drawImage(puffShroomIdleAnim, 60 + (i % 9) * 100, 129 + (i / 9) * 120, null);
                }
                if (plant instanceof Repeater) {
                	g.drawImage(repeaterIdleAnim, 60 + (i % 9) * 100, 129 + (i / 9) * 120, null);
                }
            }
        }

        // draw peas based on lane
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < peaLanes.get(i).size(); j++) {
                Pea pea = peaLanes.get(i).get(j);
                if (pea.getPeaType().equals("peashooter"))
                	g.drawImage(peaImage, pea.getPosX(), 130 + (i * 120), null); 
                else
                	g.drawImage(goopImage, pea.getPosX(), 150 + (i * 120), null); 

            }

        }      
        
    }

    
    public void setCurrentPlantingBrush(GameScreen.PlantType currentPlantingBrush) {
        this.currentPlantingBrush = currentPlantingBrush;
    }
    
    public void zombieDied() {
		zombiesDead ++;
	}
    
    // sunscore methods
    public int getSunScore() {
        return sunScore;
    }
    public void setSunScore(int sunScore) {
        this.sunScore = sunScore;
        sunScoreBoard.setText(String.valueOf(sunScore));
    }

    // getting arrayLists
    public ArrayList<Sun> getActiveSuns() {
        return activeSuns;
    }
    public ArrayList<ArrayList<Zombie>> getActiveZombies() {
        return activeZombies;
    }
    public ArrayList<ArrayList<Pea>> getActivePeas() {
    	return peaLanes;
    }
    public ArrayList<LawnMower> getActiveMowers() {
    	return activeMowers;
    }

    public Collider[] getColliders() {
        return colliders;
    }
    public static int getLevel() {
    	return level;
    }

    public void mouseDragged(MouseEvent e) {   }
    public void mouseMoved(MouseEvent e) {   }
    
    // plant collider class
    private class PlantActionListener implements ActionListener {

        int x, y;

        public PlantActionListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // making sure plants cannot be placed ontop of each other
        public void actionPerformed(ActionEvent e) {
        	if (currentPlantingBrush == GameScreen.PlantType.Minishroom && colliders[x + y * 9].getPlant() == null) {
                if (getSunScore() >= 20) {
                    colliders[x + y * 9].setPlant(new Minishroom(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 20);
                }
            }
            if (currentPlantingBrush == GameScreen.PlantType.Sunflower) {
                if (getSunScore() >= 50  && colliders[x + y * 9].getPlant() == null) {
                    colliders[x + y * 9].setPlant(new Sunflower(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 50);
                }
            }
            if (currentPlantingBrush == GameScreen.PlantType.Peashooter) {
                if (getSunScore() >= 100  && colliders[x + y * 9].getPlant() == null) {
                    colliders[x + y * 9].setPlant(new Peashooter(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 100);
                }
            }

            if (currentPlantingBrush == GameScreen.PlantType.Wallnut && colliders[x + y * 9].getPlant() == null) {
                if (getSunScore() >= 50) {
                    colliders[x + y * 9].setPlant(new Wallnut(GamePanel.this, x, y, 600));
                    setSunScore(getSunScore() - 50);
                }
            }
            
            if (currentPlantingBrush == GameScreen.PlantType.Repeater) {
                if (getSunScore() >= 200  && colliders[x + y * 9].getPlant() == null) {
                    colliders[x + y * 9].setPlant(new Repeater(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 200);
                }
            }
            
            // removing the plant on selected grid
            if(currentPlantingBrush == GameScreen.PlantType.Shovel) {
            	colliders[x + y * 9].getPlant().setHealth(0);
                colliders[x + y * 9].setPlant(null);
            }

            currentPlantingBrush = GameScreen.PlantType.None;
        }
    }

}

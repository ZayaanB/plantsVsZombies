/*
 * Authors: Zayaan and Brandon
 * Date: Jan 17 2023
 * Program Name: Collider Class
 * Program Description: Colliders for plant grids
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Collider extends JPanel implements MouseListener {

    private ActionListener listener;
    public Plant assignedPlant;

    // collider constructors
    public Collider() {
        setOpaque(false);
        addMouseListener(this);
        setSize(100, 120);
    }

    public Collider(int h, int w) {
        setOpaque(false);
        setSize(h, w);
    }

    // setters and getters
    public void setPlant(Plant p) {
        assignedPlant = p;
    }
    public Plant getPlant() {
        return this.assignedPlant;
    }

    public void removePlant() {
        assignedPlant = null;
    }

    public boolean isInsideCollider(int jx) {
        return (jx > getLocation().x) && (jx < getLocation().x + 75);
    }

    public void setAction(ActionListener listener) {
        this.listener = listener;
    }

    public void mouseClicked(MouseEvent e) {   }
    public void mousePressed(MouseEvent e) {   }
    public void mouseEntered(MouseEvent e) {   }
    public void mouseExited(MouseEvent e) {  }

    public void mouseReleased(MouseEvent e) {
        if (listener != null) {
            listener.actionPerformed(new ActionEvent(this, ActionEvent.RESERVED_ID_MAX + 1, ""));
        }
    }

}

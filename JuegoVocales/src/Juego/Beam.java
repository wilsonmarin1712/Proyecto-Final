
package Juego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javafx.scene.shape.Ellipse;
import javax.swing.ImageIcon;

/**
 *
 * @author wmarin, almartinez, wflorez
 */
public class Beam extends ObjetoJuegoMovimiento {

    // Enemigos disparan rayos
   // Constructor para haz
    public Beam(int xPosition, int yPosition, int diameter, Color color) {
        super(xPosition, yPosition, 0, 0, color);
    }
    
// usado para crear haz
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(this.getXPosition(), this.getYPosition(), 8, 15);
    }
    
    // Used to get the hit box of a beam
    @Override
    public Rectangle getBounds() {
        Rectangle beamHitbox = new Rectangle(xPos, yPos, 7, 15);
        return beamHitbox;
    }
}


package Juego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author wmarin, almartinez, wflorez
 */
public class Bala extends ObjetoJuegoMovimiento {

    // Mi barco jugador dispara balas
    int diametro;
    int yVelocidad;

    // Constructor para bala
    public Bala(int xPosition, int yPosition, int diameter, Color color) {
        super(xPosition, yPosition, 0, 0, color);
        this.diametro = diametro;
    }

    // Obtiene el diámetro de la bala
    public int getDiameter() {
        return diametro;
    }

    // Utilizado para dibujar la bala
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(this.getXPosition(), this.getYPosition(), 7, 15);

    }

    @Override
    public Rectangle getBounds() {
        Rectangle bulletHitbox = new Rectangle(xPos, yPos, 7, 15);
        return bulletHitbox;
    }
}

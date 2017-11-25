
package Juego;

import Juego.ObjetoJuego;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author wmarin, almartinez, wflorez
 */
public class Proteccion extends ObjetoJuego {

    int ancho;
    int altura;

    // Constructor for Shield objects
    public Proteccion(int xPosition, int yPosition, int width, int height, Color color) {
        super(xPosition, yPosition, color);
        this.ancho = width;
        this.altura = height;

    }

    // Accessors and mutators for every part of the shield constructor
    public int getWidth() {
        return ancho;
    }

    public int getHeight() {
        return altura;
    }

    public void setWidth(int width) {
        this.ancho = width;
    }

    public void setHeight(int height) {
        this.altura = height;
    }

    // Used to draw shield objects
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(this.getXPosition(), this.getYPosition(), 90, 10);
    }

    // Used to get the hit box of a shield object
    @Override
    public Rectangle getBounds() {
        Rectangle shieldHitbox = new Rectangle(this.getXPosition(), this.getYPosition(), 90, 10);
        return shieldHitbox;
    }
}


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

   // Constructor para objetos Shield
    public Proteccion(int xPosition, int yPosition, int width, int height, Color color) {
        super(xPosition, yPosition, color);
        this.ancho = width;
        this.altura = height;

    }

    // Accesores y mutadores para cada parte del constructor de escudo
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

    // Se usa para dibujar objetos de escudo
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(this.getXPosition(), this.getYPosition(), 90, 10);
    }

    // Se usa para obtener el cuadro de golpe de un objeto de escudo
    @Override
    public Rectangle getBounds() {
        Rectangle shieldHitbox = new Rectangle(this.getXPosition(), this.getYPosition(), 90, 10);
        return shieldHitbox;
    }
}

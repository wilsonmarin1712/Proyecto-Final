
package Juego;

import java.awt.Color;
import java.awt.Rectangle;

/**
 *
 * @author wmarin, almartinez, wflorez
 */
public abstract class ObjetoJuego implements Dibujar {

    int xPos;
    int yPos;
    Color color;
    boolean isColliding;
    
    public ObjetoJuego(){};
    
    // Constuctor para cualquier objeto de juego
    public ObjetoJuego(int xPosition, int yPosition, Color color) {
        this.xPos = xPosition;
        this.yPos = yPosition;
        this.color = color;
    }

    public abstract Rectangle getBounds();

    // Obtiene la posición X de cualquier objeto

    public int getXPosition() {
        return xPos;
    }

    // Obtiene la posición Y de cualquier objeto

    public int getYPosition() {
        return yPos;
    }

    // Obtiene el color de cualquier objeto

    public Color getColor() {
        return color;
    }

    // Establece la posición X de cualquier objeto
    public void setXPosition(int xPosition) {
        this.xPos = xPosition;
    }

    // Establece la posición Y de cualquier objeto
    public void setYPosition(int yPosition) {
        this.yPos = yPosition;
    }

    // Establece el color de cualquier objeto
    public void setColor(Color color) {
        this.color = color;
    }

    // Comprueba si las casillas de hits de dos objetos se intersectan
    public boolean isColliding(ObjetoJuego other) {
        isColliding = other.getBounds().intersects(this.getBounds());
        return isColliding;
    }
}

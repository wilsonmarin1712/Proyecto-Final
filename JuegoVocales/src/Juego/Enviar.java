
package Juego;

import Controlador.ControladorTeclado;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;


public class Enviar extends ObjetoJuegoControlado {

    ImageIcon nave = new ImageIcon("images/shipSkin.gif");
    ImageIcon bonusEnemy = new ImageIcon("images/bonusEnemySkin.gif");
    ImageIcon lifeCounterShip = new ImageIcon("images/shipSkinSmall.gif");

    // Constructor para todos los objetos de barco
    public Enviar(int xPosition, int yPosition, Color color, ControladorTeclado control) {
        super(xPosition, yPosition, color, control);
    }

    // Draw bonus enemy ship
    public void bonusDraw(Graphics g) {

        bonusEnemy.paintIcon(null, g, this.getXPosition(), this.getYPosition());
    }

    // Dibujar naves para el contador de la vida
    public void lifeDraw(Graphics g) {

        lifeCounterShip.paintIcon(null, g, this.getXPosition(), this.getYPosition());
    }

    // Dibujar barco controlado por jugador
    @Override
    public void draw(Graphics g) {
        nave.paintIcon(null, g, this.getXPosition(), this.getYPosition());

    }

    // Obtiene el cuadro de hit para todos los objetos de barco
    @Override
    public Rectangle getBounds() {
        Rectangle shipHitbox = new Rectangle(this.getXPosition(), this.getYPosition(), 50, 50);
        return shipHitbox;
    }

    // Used to move all ship objects
    @Override
    public void move() {
        // Tecla de flecha izquierda presionada
        if (control.getKeyStatus(37)) {
            xPos -= 10;
        }
        // Tecla de flecha derecha presionada
        if (control.getKeyStatus(39)) {
            xPos += 10;
        }
        
        // Mover de borde a borde sin detenerse
        if (xPos > 800) {
            xPos = -50;
        }
        if (xPos < -50) {
            xPos = 800;
        }
    }
}
